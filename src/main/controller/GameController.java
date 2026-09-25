package main.controller;

import main.mapper.DialogueMapper;
import main.mapper.PlayerMapper;
import main.mapper.SceneMapper;
import main.model.Dialogue;
import main.model.Player;
import main.model.Scene;
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

    public SceneDtoRecord loadCurrentScene() {
        List<Choice> avaliableChoices = gameSession.getStoryService().getAvaliableSceneChoices();

        Scene currentScene = gameSession.getStoryService().getCurrentScene();
        List<Dialogue> dialogues = currentScene.getDialogues();

        List<DialogueDtoRecord> dialoguesDto = new ArrayList<>();
        for (Dialogue dialogue : dialogues) {
            DialogueDtoRecord dialogueDto = DialogueMapper.toDto(dialogue);
            dialoguesDto.add(dialogueDto);
        }

        return SceneMapper.toDto(
                currentScene,
                avaliableChoices,
                dialoguesDto);

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

    public void applySceneChoiceConsequence(int choiceIndex) {
        this.gameSession.getStoryService().applySceneChoiceConsequence(choiceIndex);
    }

    public void applyChapterFinalChoiceConsequence(int choiceIndex) {
        this.gameSession.getStoryService().applyChapterFinalChoiceConsequence(choiceIndex);
    }
}