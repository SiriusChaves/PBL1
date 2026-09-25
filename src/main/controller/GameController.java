package main.controller;

import main.mapper.PlayerMapper;
import main.model.Player;
import main.service.GameSession;
import main.dto.*;
import main.model.Choice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {

    private GameSession gameSession;

    public void startNewGame() {
        this.gameSession = new GameSession();
    }

    public ChapterDto loadCurrentChapter() {
        gameSession.getStoryService().loadCurrentChapter();
        return new ChapterDto(
                gameSession.getStoryService().getCurrentChapter(),
                gameSession.getStoryService().getAvailableChapterFinalChoices());
    }

    public SceneDto loadCurrentScene() {
        return new SceneDto(
                gameSession.getStoryService().getCurrentScene(),
                gameSession.getStoryService().getAvaliableSceneChoices());
    }

    public PlayerDtoRecord loadPlayerData() {
        Player player = gameSession.getPlayer();
        return PlayerMapper.toDto(player);
    }

    public List<NpcDtoRecord> loadRelationshipData() {
        return Collections.unmodifiableList(gameSession.getStoryService().loadRelationshipDto());
    }

    public List<ItemDtoRecord> loadInventoryData() {
        return Collections.unmodifiableList(gameSession.getStoryService().loadItemDto());
    }

    public void advanceToNextScene() {
        if (gameSession.getStoryService().hasNextScene()) {
            gameSession.getStoryService().advanceToNextScene();
        }
    }

    public List<String> getAvailableSceneChoicesText() {
        List<String> choiceTexts = new ArrayList<>();
        for (Choice choice : this.gameSession.getStoryService().getAvaliableSceneChoices()) {
            choiceTexts.add(choice.getText());
        }
        return choiceTexts;
    }

    public List<String> getAvailableChapterFinalChoicesText() {
        List<String> choiceTexts = new ArrayList<>();
        for (Choice choice : this.gameSession.getStoryService().getAvailableChapterFinalChoices()) {
            choiceTexts.add(choice.getText());
        }
        return choiceTexts;
    }

    public void applySceneChoiceConsequence(int choiceIndex) {
        this.gameSession.getStoryService().applySceneChoiceConsequence(choiceIndex);
    }

    public void applyChapterFinalChoiceConsequence(int choiceIndex) {
        this.gameSession.getStoryService().applyChapterFinalChoiceConsequence(choiceIndex);
    }
}