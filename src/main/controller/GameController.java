package main.controller;

import main.mapper.ChapterMapper;
import main.mapper.PlayerMapper;
import main.mapper.SceneMapper;
import main.service.GameSession;
import main.dto.*;

import java.util.Collections;
import java.util.List;

public class GameController {

    private GameSession gameSession;

    public void startNewGame() {
        this.gameSession = new GameSession();
    }

    public ChapterDtoRecord loadCurrentChapter() {
        gameSession.getStoryService().loadCurrentChapter();

        return ChapterMapper.toDto(
                gameSession.getStoryService().getCurrentChapter(),
                gameSession.getStoryService().getAvailableChapterFinalChoices());
    }

    public SceneDtoRecord loadCurrentScene() {
        return SceneMapper.toDto(
                gameSession.getStoryService().getCurrentScene(),
                gameSession.getStoryService().getAvaliableSceneChoices(),
                gameSession.getStoryService().loadDialogueDto());
    }

    public PlayerDtoRecord loadPlayerData() {
        return PlayerMapper.toDto(gameSession.getPlayer());
    }

    public List<NpcDtoRecord> loadRelationshipData() {
        return Collections.unmodifiableList(gameSession.getStoryService().loadRelationshipDto());
    }

    public List<ItemDtoRecord> loadInventoryData() {
        return Collections.unmodifiableList(gameSession.getStoryService().loadItemDto());
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