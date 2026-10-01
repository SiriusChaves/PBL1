package main.service;

import main.dao.ChapterDao;
import main.model.Flag;
import main.model.Player;

public class GameSession {
    private final Player player;
    private ChapterService chapterService;
    private final StoryService storyService;
    private final Flag gameFlags;

    public GameSession(ChapterDao chapterDao) {
        this.player = new Player("O Estudante");
        this.gameFlags = new Flag();
        this.chapterService = new ChapterService(chapterDao);
        this.storyService = new StoryService(this.player, this.gameFlags, this.chapterService);
    }

    public void saveGameSession() {
        // ... a implementar
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