package main.service.game;

import main.persistence.dao.ChapterDao;
import main.dto.GameStateDto;
import main.persistence.dto.SavePersistenceDto;
import main.mapper.PlayerMapper;
import main.model.story.Flag;
import main.model.player.Player;
import main.service.story.ChapterService;
import main.service.story.StoryService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private Player player;
    private final ChapterService chapterService;
    private final StoryService storyService;
    private final Flag gameFlags;
    private final String slotIndex;
    private boolean gameCompleted = false;

    public GameSession(ChapterDao chapterDao, String slotIndex) {
        this.player = new Player("O Estudante");
        this.gameFlags = new Flag();
        this.chapterService = new ChapterService(chapterDao);
        this.storyService = new StoryService(this.player, this.gameFlags, this.chapterService);
        this.slotIndex = slotIndex;
    }

    public GameSession(
            Player player,
            Flag gameFlags,
            String slotIndex,
            ChapterService chapterService,
            StoryService storyService,
            boolean gameCompleted
    ) {
        this.player = player;
        this.gameFlags = gameFlags;
        this.chapterService = chapterService;
        this.storyService = storyService;
        this.slotIndex = slotIndex;
        this.gameCompleted = gameCompleted;
    }

    public SavePersistenceDto loadDataOfSave() {

        return new SavePersistenceDto(
                player.getName(),
                LocalDateTime.now().toString(),
                storyService.getCurrentChapter().getTitle(),
                getGameState()
        );
    }

    public GameStateDto getGameState() {

        List<String> itemsId = new ArrayList<>(
                player.getInventory()
                        .getItems()
                        .keySet()
        );

        List<String> activeFlags = new ArrayList<>(gameFlags.getActiveFlags());

        return new GameStateDto (

                PlayerMapper.toDto(player),

                storyService.getCurrentChapter() == null
                        ? null
                        : storyService.getCurrentChapter().getId(),

                storyService.getNextChapterId(),

                storyService.getCurrentScene() == null
                        ? null
                        : storyService.getCurrentScene().getId(),

                itemsId,
                storyService.loadRelationshipDto(),
                activeFlags,
                isGameCompleted(),
                storyService.getCurrentSceneIndex()
        );
    }

    public Player getPlayer() {
        return player;
    }

    public StoryService getStoryService() {
        return storyService;
    }

    public Flag getGameFlags() {
        return gameFlags;
    }

    public String getSlotIndex() {
        return slotIndex;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isGameCompleted() {
        return gameCompleted;
    }

    public void setGameIsCompleted() {
        this.gameCompleted = true;
    }
}