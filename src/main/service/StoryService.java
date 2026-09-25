package main.Service;

import main.dto.ItemDtoRecord;
import main.dto.NpcDtoRecord;
import main.loader.ChapterLoader;
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

    public List<NpcDtoRecord> loadRelationshipDto() {
        List<Npc> relationships = new ArrayList<>(
                player.getRelationships().values());

        List<NpcDtoRecord> relationshipsDto = new ArrayList<>();
        for (Npc npc : relationships) {

            NpcDtoRecord npcDtoRecord = NpcMapper.toDto(npc);

            relationshipsDto.add(npcDtoRecord);
        }

        return relationshipsDto;
    }

    public List<ItemDtoRecord> loadItemDto() {
        if (player.getInventory().getItems().isEmpty()) {
            return Collections.emptyList();
        } else {
            List<Item> items = new ArrayList<>(
                    player.getInventory().getItems().values());

            List<ItemDtoRecord> itemsDto = new ArrayList<>();
            for (Item item : items) {
                ItemDtoRecord itemDtoRecord = ItemMapper.toDto(item);
                itemsDto.add(itemDtoRecord);
            }

            return itemsDto;
        }
    }
}
