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
        this.nextChapterId = "0";
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

    public List<Choice> getAvaliableSceneChoices() {
        List<Choice> avaliableChoices = new ArrayList<>();

        for (Choice choice : currentScene.getChoices()) {
            if (choice.isAvailable(player, gameFlags)) {
                avaliableChoices.add(choice);
            }
        }

        return avaliableChoices;
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
        List<Choice> availableChoices = getAvaliableSceneChoices();
        if (choiceIndex >= 0 && choiceIndex < availableChoices.size()) {
            Choice selectedChoice = availableChoices.get(choiceIndex);
            selectedChoice.applyEffects(this.player, this.gameFlags);
        }
    }

    public void applyChapterFinalChoiceConsequence(int choiceIndex) {
        List<Choice> availableChoices = getAvailableChapterFinalChoices();

        if (PROLOGUE_ID.equals(this.currentChapter.getId())) {
            // Baseado na lista filtrada (que no prólogo sempre tem as 2)
            String chosenName = switch (choiceIndex) {
                case 0 -> "O Arquiteto";
                default -> "O Estudante";
            };
            this.player.setName(chosenName);
        }

        if (choiceIndex >= 0 && choiceIndex < availableChoices.size()) {
            Choice selectedChoice = availableChoices.get(choiceIndex);
            selectedChoice.applyEffects(this.player, this.gameFlags);
        }
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public Chapter getCurrentChapter() {
        return currentChapter;
    }

    public List<NpcDto> loadRelationshipDto() {
        List<Npc> relationships = new ArrayList<>(
                player.getRelationships().values());

        List<NpcDto> relationshipsDto = new ArrayList<>();
        for (Npc npc : relationships) {

            NpcDto npcDto = NpcMapper.toDto(npc);

            relationshipsDto.add(npcDto);
        }

        return relationshipsDto;
    }

    public List<ItemDto> loadItemDto() {
        if (player.getInventory().getItems().isEmpty()) {
            return Collections.emptyList();
        } else {
            List<Item> items = new ArrayList<>(
                    player.getInventory().getItems().values());

            List<ItemDto> itemsDto = new ArrayList<>();
            for (Item item : items) {
                ItemDto itemDto = ItemMapper.toDto(item);
                itemsDto.add(itemDto);
            }

            return itemsDto;
        }
    }

    public List<DialogueDto> loadDialogueDto() {
        List<Dialogue> dialoguesScene = currentScene.getDialogues();

        List<DialogueDto> dialoguesDto = new ArrayList<>();
        for (Dialogue dialogue : dialoguesScene) {
            dialoguesDto.add(
                    DialogueMapper.toDto(dialogue));
        }

        return dialoguesDto;
    }
}
