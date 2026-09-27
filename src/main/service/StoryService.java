package main.service;

import main.dto.DialogueDto;
import main.dto.ItemDto;
import main.dto.NpcDto;
import main.loader.ChapterLoader;
import main.mapper.DialogueMapper;
import main.mapper.ItemMapper;
import main.mapper.NpcMapper;
import main.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoryService {
    private static final String PROLOGUE_ID = "0";

    private final Player player;
    private final Flag gameFlags;
    private Chapter currentChapter;
    private Scene currentScene;
    private String nextChapterId;
    private int nextSceneIndex;

    public StoryService(Player player, Flag gameFlags) {
        this.player = player;
        this.gameFlags = gameFlags;
        this.nextChapterId = "-1";
        this.nextSceneIndex = 1;
    }

    public void loadCurrentChapter() {
        currentChapter = ChapterLoader.loadNextChapter(nextChapterId, player, gameFlags);
        nextChapterId = currentChapter.getIdNextChapter();
        currentScene = currentChapter.getScenes().getFirst();
        nextSceneIndex = 1;
    }

    public void advanceToNextScene() {
        currentScene = currentChapter.getScenes().get(nextSceneIndex++);
    }

    public boolean hasNextScene() {
        return nextSceneIndex < currentChapter.getScenes().size();
    }

    public List<Choice> getAvailableSceneChoices() {
        List<Choice> availableChoices = new ArrayList<>();

        for (Choice choice : currentScene.getChoices()) {
            if (choice.isAvailable(player, gameFlags)) {
                availableChoices.add(choice);
            }
        }

        return availableChoices;
    }

    public List<Choice> getAvailableChapterFinalChoices() {
        List<Choice> availableChoices = new ArrayList<>();
        for (Choice choice : this.currentChapter.getFinalChoices()) {
            if (choice.isAvailable(this.player, this.gameFlags)) {
                availableChoices.add(choice);
            }
        }
        return availableChoices;
    }

    public void applySceneChoiceConsequence(int choiceIndex) {
        List<Choice> availableChoices = getAvailableSceneChoices();
        if (choiceIndex >= 0 && choiceIndex < availableChoices.size()) {
            Choice selectedChoice = availableChoices.get(choiceIndex);
            selectedChoice.applyEffects(this.player, this.gameFlags);
        }
    }

    public void applyChapterFinalChoiceConsequence(int choiceIndex) {
        List<Choice> availableChoices = getAvailableChapterFinalChoices();

        if (PROLOGUE_ID.equals(this.currentChapter.getId())) {
            String choseName = (choiceIndex == 0) ? "O Arquiteto" : "O Estudante";
            player.setName(choseName);
        }

        if (choiceIndex >= 0 && choiceIndex < availableChoices.size()) {
            Choice selectedChoice = availableChoices.get(choiceIndex);
            selectedChoice.applyEffects(this.player, this.gameFlags);
        }
    }

    public List<NpcDto> loadRelationshipDto() {
        List<Npc> relationships = new ArrayList<>(
                player.getRelationships().values());

        List<NpcDto> relationshipsDto = new ArrayList<>();
        for (Npc npc : relationships) {
            relationshipsDto.add(NpcMapper.toDto(npc));
        }

        return relationshipsDto;
    }

    public List<ItemDto> loadItemDtoInInventory() {
        if (player.getInventory().getItems().isEmpty()) {
            return Collections.emptyList();
        } else {
            List<Item> items = new ArrayList<>(
                    player.getInventory().getItems().values());

            List<ItemDto> itemsDto = new ArrayList<>();
            for (Item item : items) {
                itemsDto.add(ItemMapper.toDto(item));
            }

            return itemsDto;
        }
    }

    public List<DialogueDto> loadDialogueDtoOfCurrentScene() {
        List<Dialogue> dialoguesScene = currentScene.getDialogues();

        List<DialogueDto> dialoguesDto = new ArrayList<>();
        for (Dialogue dialogue : dialoguesScene) {
            dialoguesDto.add(DialogueMapper.toDto(dialogue));
        }

        return dialoguesDto;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public Chapter getCurrentChapter() {
        return currentChapter;
    }
}
