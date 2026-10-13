package main.service;

import main.dao.ChapterDao;
import main.dto.GameStateDto;
import main.dto.SavePersistenceDto;
import main.mapper.PlayerMapper;
import main.model.Flag;
import main.model.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private Player player;
    private final ChapterService chapterService;
    private final StoryService storyService;
    private final Flag gameFlags;
    private final String slotIndex;

    public GameSession(ChapterDao chapterDao, String slotIndex) {
        this.player = new Player("O Estudante");
        this.gameFlags = new Flag();
        this.chapterService = new ChapterService(chapterDao);
        this.storyService = new StoryService(this.player, this.gameFlags, this.chapterService);
        this.slotIndex = slotIndex;
    }

    public GameSession(Player player, Flag gameFlags, String slotIndex, ChapterService chapterService, StoryService storyService) {
        this.player = player;
        this.gameFlags = gameFlags;
        this.chapterService = chapterService;
        this.storyService = storyService;
        this.slotIndex = slotIndex;
    }

    public SavePersistenceDto loadDataOfSave() {

        return new SavePersistenceDto(
                player.getName(),
                LocalDateTime.now().toString(),
                storyService.getCurrentChapter().getTitle(),
                getGameState());
    }

    public GameStateDto getGameState() {
        List<String> itemsId = new ArrayList<>(

                player.getInventory().getItems().keySet()
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
                storyService.isFinalChapter()
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
}