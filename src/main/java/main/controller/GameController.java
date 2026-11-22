package main.controller;

import main.dto.*;
import main.exception.ChapterNotFoundException;
import main.mapper.ChapterMapper;
import main.mapper.PlayerMapper;
import main.mapper.SceneMapper;
import main.model.player.Item;
import main.model.profile.Preference;
import main.model.profile.UserProfile;
import main.observer.*;
import main.persistence.dao.*;
import main.persistence.dto.SavePersistenceDto;
import main.service.achievement.AchievementService;
import main.service.game.GameSession;
import main.service.save.SaveService;
import main.service.profile.UserProfileService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GameController {

    private record ItemSnapshot(
            String id,
            String name,
            String description
    ) {}

    private record GameStateSnapshot(
            int sanity,
            int knowledge,
            Map<String, Integer> relationshipTrust,
            Map<String, ItemSnapshot> inventoryItems,
            Set<String> activeFlags
    ) {}

    private GameSession gameSession;
    private SaveService saveService;
    private final ChapterDao chapterDao;
    private AchievementService achievementService;
    private UserProfileService userProfileService;
    private final GameEventManager gameEventManager = new GameEventManager();

    public GameController(
            ChapterDao chapterDao,
            SaveDao saveDao,
            ItemDao itemDao,
            AchievementDao achievementDao,
            UserProfileDao profileDao
    ) {
        this.chapterDao = chapterDao;
        this.saveService = new SaveService(saveDao, itemDao);
        this.achievementService = new AchievementService(achievementDao);
        this.userProfileService = new UserProfileService(profileDao);
    }

    private GameStateSnapshot captureGameState() {

        var player = gameSession.getPlayer();

        Map<String, Integer> relationships = new HashMap<>();

        player.getRelationships().forEach((name, npc) -> {
            relationships.put(name, npc.getTrustLevel());
        });

        Map<String, ItemSnapshot> items = new HashMap<>();

        for (Item item : player.getInventory().getItems().values()) {

            items.put(
                    item.getId(),
                    new ItemSnapshot(
                            item.getId(),
                            item.getName(),
                            item.getDescription()
                    )
            );
        }

        Set<String> flags = Set.copyOf(
                gameSession.getGameFlags().getActiveFlags()
        );

        return new GameStateSnapshot(
                player.getSanity(),
                player.getKnowledge(),
                Map.copyOf(relationships),
                Map.copyOf(items),
                flags
        );

    }

    private void publishStateChanges(GameStateSnapshot previous, GameStateSnapshot current
    ) {

        // SANIDADE
        if (previous.sanity() != current.sanity()) {

            gameEventManager.notifyObservers(
                    new GameEvent(
                            GameEventType.PLAYER_ATTRIBUTE_CHANGED,
                            new PlayerAttributeChange(
                                    PlayerAttribute.SANITY,
                                    previous.sanity(),
                                    current.sanity()
                            )
                    )
            );
        }

        if (previous.knowledge() != current.knowledge()) {

            gameEventManager.notifyObservers(
                    new GameEvent(
                            GameEventType.PLAYER_ATTRIBUTE_CHANGED,
                            new PlayerAttributeChange(
                                    PlayerAttribute.KNOWLEDGE,
                                    previous.knowledge(),
                                    current.knowledge()
                            )
                    )
            );
        }

        for (Map.Entry<String, ItemSnapshot> entry :
                current.inventoryItems().entrySet()) {

            String itemId = entry.getKey();

            if (!previous.inventoryItems().containsKey(itemId)) {

                ItemSnapshot item = entry.getValue();

                gameEventManager.notifyObservers(
                        new GameEvent(
                                GameEventType.ITEM_ACQUIRED,
                                new ItemAcquired(
                                        item.id(),
                                        item.name(),
                                        item.description()
                                )
                        )
                );
            }
        }

        for (String flag : current.activeFlags()) {

            if (!previous.activeFlags().contains(flag)) {

                gameEventManager.notifyObservers(
                        new GameEvent(
                                GameEventType.FLAG_UNLOCKED,
                                new FlagUnlocked(flag)
                        )
                );
            }
        }

        for (Map.Entry<String, Integer> entry :
                current.relationshipTrust().entrySet()) {

            String npcName = entry.getKey();

            Integer previousTrust =
                    previous.relationshipTrust().get(npcName);

            int currentTrust = entry.getValue();

            if (previousTrust != null &&
                    previousTrust != currentTrust) {

                gameEventManager.notifyObservers(
                        new GameEvent(
                                GameEventType.RELATIONSHIP_CHANGED,
                                new RelationshipChange(
                                        npcName,
                                        previousTrust,
                                        currentTrust
                                )
                        )
                );
            }
        }
    }

    public void addObserver(GameObserver observer) {
        gameEventManager.addObserver(observer);
    }

    public void removeObserver(GameObserver observer) {
        gameEventManager.removeObserver(observer);
    }

    public void notifySceneChanged() {

        SceneDto currentScene = loadCurrentScene();

        GameEvent event = new GameEvent(
                GameEventType.SCENE_CHANGED,
                currentScene
        );

        gameEventManager.notifyObservers(event);
    }

    public boolean isBackgroundMusicEnabled() {

        return userProfileService
                .loadUserProfile()
                .hasPreference(Preference.BACKGROUND_MUSIC);
    }

    public boolean isAutosaveEnabled() {

        return userProfileService
                .loadUserProfile()
                .hasPreference(Preference.AUTOSAVE);
    }

    public UserProfile loadUserProfile() {
        return userProfileService.loadUserProfile();
    }

    public void togglePreference(Preference preference) {
        userProfileService.togglePreference(preference);
    }

    public void updateLastPlayedSlot(String slotIndex) {
        userProfileService.setLastPlayedSlotId(slotIndex);
    }

    public List<AchievementDto> loadAchievements() {
        return achievementService.loadAchievementsDto();
    }

    public boolean hasNextScene() {

        return gameSession
                .getStoryService()
                .hasNextScene();
    }

    public boolean hasAvailableChapterFinalChoices() {

        return !gameSession
                .getStoryService()
                .getAvailableChapterFinalChoices()
                .isEmpty();
    }

    public boolean isGameCompleted() {
        return gameSession.isGameCompleted();
    }

    public boolean isGameOver() {
        return gameSession.isGameOver();
    }

    public void setGameIsCompleted() {

        gameSession.setGameIsCompleted();

        achievementService.saveUnlockedAchievements(gameSession.getGameState());
    }

    public boolean isFinalChapter() {

        return gameSession
                .getStoryService()
                .getCurrentChapter()
                .getIdNextChapter().equalsIgnoreCase("fim");
    }

    public void deleteGameSession(String slotIndex) {
        saveService.deleteSlotSave(slotIndex);
    }

    public void saveGame() {

        saveService.saveGame(
                gameSession.loadDataOfSave(),
                gameSession.getSlotIndex()
        );

        updateLastPlayedSlot(gameSession.getSlotIndex());
    }

    public boolean hasSaveDataAndSaveExists(String slotIndex) {
        return saveService.hasSaveDataAndSaveExists(slotIndex);
    }

    public void loadGame(String slotIndex) {

        this.gameSession =
                saveService.restoreGameSessionFromPersistence(
                        chapterDao,
                        slotIndex
                );

        updateLastPlayedSlot(slotIndex);
    }

    public void startNewGame(String slotIndex) {

        this.gameSession = new GameSession(
                chapterDao,
                slotIndex
        );
    }

    public void prepareSaveSlot(String slotIndex, boolean overrideSave) {
        saveService.prepareSaveSlot(slotIndex, overrideSave);
    }

    public SavePersistenceDto getDataSave(String slotIndex) {
        return gameSession.loadDataOfSave();
    }


    private String formatDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime).format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"));
    }

    public List<SaveViewDto> getDataOfAllSaves() {

        List<SavePersistenceDto> savePersistenceDtoList = saveService.getDataOfAllSaves();

        List<SaveViewDto> saveViewDtoList = new ArrayList<>();

        for (var save : savePersistenceDtoList) {

            if (save != null)  {

                GameStateDto state = save.gameStateDto();

                String progressSummary;

                if (state.isGameCompleted()) {

                    progressSummary = "Campanha concluída";

                } else {

                    String scene = state.currentSceneId() == null
                            ? "Início"
                            : state.currentSceneId();

                    int items = state.itemsId().size();
                    int flags = state.activeGameFlags().size();

                    progressSummary = "Cena " + scene
                            + " - " + items + " itens"
                            + " - " + flags + " marcadores narrativos";
                }

                saveViewDtoList.add(

                        new SaveViewDto(
                            save.playerName(),
                            formatDateTime(save.dataHora()),
                            save.lastChapterName(),
                            progressSummary,
                            save.gameStateDto().isGameCompleted()
                        )
                );

            } else {

                saveViewDtoList.add(null);
            }

        }
        return saveViewDtoList;
    }

    public ChapterDto loadCurrentChapter()
            throws ChapterNotFoundException {

        gameSession.getStoryService()
                .loadCurrentChapter(gameSession.getGameState());

        achievementService.saveUnlockedAchievements(gameSession.getGameState());

        ChapterDto chapterDto =  ChapterMapper.toDto(
                gameSession.getStoryService().getCurrentChapter(),
                gameSession.getStoryService().getAvailableChapterFinalChoices()
        );

        notifySceneChanged();

        return chapterDto;
    }

    public SceneDto loadCurrentScene() {
        return SceneMapper.toDto(
                gameSession.getStoryService().getCurrentScene(),
                gameSession.getStoryService().getAvailableSceneChoices(),
                gameSession.getStoryService().loadDialogueDtoOfCurrentScene()
        );
    }

    public PlayerDto loadPlayerData() {
        return PlayerMapper.toDto(gameSession.getPlayer());
    }

    public List<NpcDto> loadRelationshipData() {

        return Collections.unmodifiableList(
                gameSession.getStoryService().loadRelationshipDto()
        );
    }

    public List<ItemViewDto> loadInventoryData() {

        return Collections.unmodifiableList(
                gameSession.getStoryService().loadItemDtoInInventory()
        );
    }

    public void advanceToNextScene() {

        if (!hasNextScene()) {
            return;
        }

        gameSession.getStoryService().advanceToNextScene();

        notifySceneChanged();
    }

    public void applySceneChoiceConsequence(int choiceIndex) {

        GameStateSnapshot previousState = captureGameState();

        gameSession.getStoryService().applySceneChoiceConsequence(choiceIndex);

        achievementService.saveUnlockedAchievements(
                gameSession.getGameState()
        );

        GameStateSnapshot currentState = captureGameState();

        publishStateChanges(previousState, currentState);
    }

    public void applyChapterFinalChoiceConsequence(int choiceIndex) {

        GameStateSnapshot previousState = captureGameState();

        gameSession.getStoryService().applyChapterFinalChoiceConsequence(choiceIndex);

        achievementService.saveUnlockedAchievements(
                gameSession.getGameState()
        );

        GameStateSnapshot currentState = captureGameState();

        publishStateChanges(previousState, currentState);
    }
}