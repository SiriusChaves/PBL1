package main.service;

import main.dao.ChapterDao;
import main.dto.SaveGamePersistenceDto;
import main.model.Flag;
import main.model.Player;

public class GameSession {
    private final Player player;
    private ChapterService chapterService;
    private final StoryService storyService;
    private final Flag gameFlags;
    private final String personName;
    private final String slotIndex;

    public GameSession(ChapterDao chapterDao, String personName, String slotIndex) {
        this.personName = personName;
        this.player = new Player("O Estudante");
        this.gameFlags = new Flag();
        this.chapterService = new ChapterService(chapterDao);
        this.storyService = new StoryService(this.player, this.gameFlags, this.chapterService);
        this.slotIndex = slotIndex;
    }

    public SaveGamePersistenceDto getDataOfSaveGame()  {
        return storyService.loadDataOfSaveGame(personName, slotIndex);
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