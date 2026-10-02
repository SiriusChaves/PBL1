package main.controller;

import main.dao.ChapterDao;
import main.dao.ItemDao;
import main.dao.SaveGameDao;
import main.dto.*;
import main.exception.ChapterNotFoundException;
import main.mapper.ChapterMapper;
import main.mapper.PlayerMapper;
import main.mapper.SceneMapper;
import main.service.GameSession;
import main.service.SaveGameService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {

    private GameSession gameSession;
    private SaveGameService saveGameService;
    private final ChapterDao chapterDao;
    private final SaveGameDao saveGameDao;

    public GameController(ChapterDao chapterDao, SaveGameDao saveGameDao, ItemDao itemDao) {
        this.chapterDao = chapterDao;
        this.saveGameDao = saveGameDao;
        this.saveGameService = new SaveGameService(saveGameDao, itemDao);
    }

    public void startNewGame(String playerName, String slotIndex) {
        this.gameSession = new GameSession(chapterDao, playerName, slotIndex);
    }

    public SaveGamePersistenceDto getDataSAave(String slotIndex) {
        return gameSession.getDataOfSaveGame();
    }

    public List<SaveGameViewDto> getDataOfAllSaves() {
        List<SaveGamePersistenceDto> saveGamePersistenceDtos = saveGameService.getDataOfAllSaves();

        List<SaveGameViewDto> saveGameViewDtos = new ArrayList<>();

        for (SaveGamePersistenceDto saveGamePersistenceDto : saveGamePersistenceDtos) {
            saveGameViewDtos.add(new SaveGameViewDto(
                    saveGamePersistenceDto.playerName(),
                    saveGamePersistenceDto.dataHora(),
                    saveGamePersistenceDto.lastChapterName()));
        }

        return saveGameViewDtos;
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

    public List<ItemViewDto> loadInventoryData() {
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