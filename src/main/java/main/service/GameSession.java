package main.service;

import main.dao.ChapterDao;
import main.dto.GameStateDto;
import main.dto.SavePersistenceDto;
import main.model.Flag;
import main.model.Player;

public class GameSession {
    private Player player;
    private ChapterService chapterService;
    private StoryService storyService;
    private Flag gameFlags;
    private String slotIndex;

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

    public SavePersistenceDto getDataOfSaveGame()  {
        return storyService.loadDataOfSave(slotIndex);
    }

    public GameStateDto getGameState() {
        return storyService.createGameStateDto();
    }

    public void loadSaveGameSession() {
        // ... a implementar
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