package main.service;

import main.model.Flag;
import main.model.Player;

public class GameSession {
    private Player player;
    private StoryService storyService;
    private Flag gameFlags; // Decisoes relevantes para a historia tomadas pelo jogador

    public GameSession() {
        this.player = new Player("O Estudante");
        this.gameFlags = new Flag();
        this.storyService = new StoryService(this.player, this.gameFlags);
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