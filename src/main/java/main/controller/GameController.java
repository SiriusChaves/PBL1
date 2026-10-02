package main.controller;

import main.dao.ChapterDao;
import main.dao.SaveGameDao;
import main.dto.*;
import main.exception.ChapterNotFoundException;
import main.exception.GameNotSaveException;
import main.mapper.ChapterMapper;
import main.mapper.PlayerMapper;
import main.mapper.SceneMapper;
import main.service.GameSession;

import java.util.Collections;
import java.util.List;

public class GameController {

    private GameSession gameSession;
    private final ChapterDao chapterDao;
    private final SaveGameDao saveGameDao;

    public GameController(ChapterDao chapterDao, SaveGameDao saveGameDao) {
        this.chapterDao = chapterDao;
        this.saveGameDao = saveGameDao;
    }

    public void startNewGame(String playerName) {
        this.gameSession = new GameSession(chapterDao, saveGameDao, playerName);
    }

    public void saveGame(String slotIndex) throws GameNotSaveException {
        gameSession.saveGameSession(slotIndex);
    }

    public ChapterDto loadCurrentChapter() throws ChapterNotFoundException {
        gameSession.getStoryService().loadCurrentChapter();

        return ChapterMapper.toDto(
                gameSession.getStoryService().getCurrentChapter(),
                gameSession.getStoryService().getAvailableChapterFinalChoices());
    }

    public SceneDto loadCurrentScene() {
        return SceneMapper.toDto(
                gameSession.getStoryService().getCurrentScene(),
                gameSession.getStoryService().getAvailableSceneChoices(),
                gameSession.getStoryService().loadDialogueDtoOfCurrentScene());
    }

    public PlayerDto loadPlayerData() {
        return PlayerMapper.toDto(gameSession.getPlayer());
    }

    public List<NpcDto> loadRelationshipData() {
        return Collections.unmodifiableList(gameSession.getStoryService().loadRelationshipDto());
    }

    public List<ItemDto> loadInventoryData() {
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