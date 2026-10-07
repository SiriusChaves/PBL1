package main.controller;

import main.dao.ChapterDao;
import main.dao.ItemDao;
import main.dao.SaveDao;
import main.dto.*;
import main.exception.ChapterNotFoundException;
import main.mapper.ChapterMapper;
import main.mapper.PlayerMapper;
import main.mapper.SceneMapper;
import main.service.GameSession;
import main.service.SaveService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {

    private GameSession gameSession;
    private SaveService saveService;
    private final ChapterDao chapterDao;

    public GameController(ChapterDao chapterDao, SaveDao saveDao, ItemDao itemDao) {
        this.chapterDao = chapterDao;
        this.saveService = new SaveService(saveDao, itemDao);
    }

    public void deleteGameSession(String slotIndex) {
        saveService.deleteSlotSave(slotIndex);
    }

    public void saveGame() {
        saveService.saveGame(
                gameSession.getDataOfSaveGame(), gameSession.getSlotIndex());
    }

    public boolean hasSaveDataAndSaveExists(String slotIndex) {
        return saveService.hasSaveDataAndSaveExists(slotIndex);
    }

    public void startGameSession(String slotIndex) {
        if (hasSaveDataAndSaveExists(slotIndex)) {
            this.gameSession = saveService.restoreGameSessionFromPersistence(chapterDao, slotIndex);
        } else {
            this.gameSession = new GameSession(chapterDao, slotIndex);
        }
    }

    public void createNewSlotOfGameSession(String slotIndex, boolean overrideSave) {
        saveService.createNewSlotOfGameSession(slotIndex, overrideSave);
    }

    public SavePersistenceDto getDataSave(String slotIndex) {
        return gameSession.getDataOfSaveGame();
    }

    public List<SaveViewDto> getDataOfAllSaves() {
        List<SavePersistenceDto> savePersistenceDtos = saveService.getDataOfAllSaves();

        List<SaveViewDto> saveViewDtos = new ArrayList<>();

        for (SavePersistenceDto savePersistenceDto : savePersistenceDtos) {
            if (savePersistenceDto != null)  {
                saveViewDtos.add(new SaveViewDto(
                        savePersistenceDto.playerName(),
                        savePersistenceDto.dataHora(),
                        savePersistenceDto.lastChapterName())
                );
            } else {
                saveViewDtos.add(null);
            }

        }
        return saveViewDtos;
    }

    public ChapterDto loadCurrentChapter() throws ChapterNotFoundException {
        gameSession.getStoryService().loadCurrentChapter();

        return ChapterMapper.toDto(
                gameSession.getStoryService().getCurrentChapter(),
                gameSession.getStoryService().getAvailableChapterFinalChoices()
        );
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
        return Collections.unmodifiableList(gameSession.getStoryService().loadRelationshipDto());
    }

    public List<ItemViewDto> loadInventoryData() {
        return Collections.unmodifiableList(gameSession.getStoryService().loadItemDtoInInventory());
    }

    public void advanceToNextScene() {
        if (gameSession.getStoryService().hasNextScene())
            gameSession.getStoryService().advanceToNextScene();
    }

    public void applySceneChoiceConsequence(int choiceIndex) {
        gameSession.getStoryService().applySceneChoiceConsequence(choiceIndex);
    }

    public void applyChapterFinalChoiceConsequence(int choiceIndex) {
        gameSession.getStoryService().applyChapterFinalChoiceConsequence(choiceIndex);
    }
}