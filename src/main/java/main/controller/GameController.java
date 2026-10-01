package main.controller;

import main.dao.ChapterDao;
import main.dto.*;
import main.exception.ChapterNotFoundException;
import main.mapper.ChapterMapper;
import main.mapper.PlayerMapper;
import main.mapper.SceneMapper;
import main.service.GameSession;

import java.util.Collections;
import java.util.List;

public class GameController {

    private GameSession gameSession;
    private final ChapterDao chapterDao;

    public GameController(ChapterDao chapterDao) {
        this.chapterDao = chapterDao;
    }

    public void startNewGame() {

        this.gameSession = new GameSession(chapterDao);
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