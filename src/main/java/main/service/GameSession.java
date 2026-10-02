package main.service;

import main.dao.ChapterDao;
import main.dao.SaveGameDao;
import main.exception.GameNotSaveException;
import main.model.Flag;
import main.model.Player;

public class GameSession {
    private final Player player;
    private ChapterService chapterService;
    private final StoryService storyService;
    private final Flag gameFlags;
    private String playerName; // nome da pessoa q está jogando

    public GameSession(ChapterDao chapterDao, SaveGameDao saveGameDao, String playerName) {
        this.playerName = playerName;
        this.player = new Player("O Estudante");
        this.gameFlags = new Flag();
        this.chapterService = new ChapterService(chapterDao);
        this.storyService = new StoryService(this.player, this.gameFlags, this.chapterService, saveGameDao);
    }

    public void saveGameSession(String slotIndex) throws GameNotSaveException {
        storyService.saveGame(playerName, slotIndex);
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
}