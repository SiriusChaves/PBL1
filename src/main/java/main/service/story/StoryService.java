package main.service.story;

import main.dto.*;
import main.exception.ChapterNotFoundException;
import main.mapper.DialogueMapper;
import main.mapper.ItemMapper;
import main.mapper.NpcMapper;
import main.model.player.Item;
import main.model.player.Player;
import main.model.story.Chapter;
import main.model.story.Choice;
import main.model.story.Flag;
import main.model.story.Scene;

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
    private int currentSceneIndex;
    private final ChapterService chapterService;

    public StoryService(Player player, Flag gameFlags, ChapterService chapterService) {
        this.player = player;
        this.gameFlags = gameFlags;
        this.nextChapterId = "-1";
        this.currentSceneIndex = 0;
        this.chapterService = chapterService;
    }

    public StoryService(
            Player player,
            Flag gameFlags,
            ChapterService chapterService,
            String nextChapterId,
            Integer currentSceneIndex
    ) {
        this.player = player;
        this.gameFlags = gameFlags;
        this.nextChapterId = nextChapterId;

        this.currentSceneIndex =
                currentSceneIndex == null
                        ? 0
                        : currentSceneIndex;

        this.chapterService = chapterService;
    }

    public void loadCurrentChapter(GameStateDto gameState)
            throws ChapterNotFoundException {

        if (currentChapter != null)
            currentSceneIndex = 0;

        currentChapter = chapterService.loadNextChapter(gameState);

        nextChapterId = currentChapter.getIdNextChapter();

        currentScene = currentChapter
                .getScenes()
                .get(currentSceneIndex);
    }

    public void advanceToNextScene() {

        if (!hasNextScene())
            return;

        currentSceneIndex++;

        currentScene = currentChapter
                .getScenes()
                .get(currentSceneIndex);
    }

    public boolean hasNextScene() {

        return currentSceneIndex + 1 < currentChapter.getScenes().size();
    }

    public boolean isFinalChapter() {
        return currentChapter != null && currentChapter.getIdNextChapter().equalsIgnoreCase("fim");
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

    public List<Choice> getAvailableSceneChoices() {

        return currentScene.getChoices().stream()
                .filter(choice -> choice.isAvailable(player, gameFlags))
                .toList();
    }

    public List<Choice> getAvailableChapterFinalChoices() {

        return currentChapter.getFinalChoices().stream()
                .filter(choice -> choice.isAvailable(player, gameFlags))
                .toList();
    }

    public List<NpcDto> loadRelationshipDto() {

        return player.getRelationships().values().stream()
                .map(NpcMapper::toDto)
                .toList();
    }

    public List<ItemViewDto> loadItemDtoInInventory() {

        List<Item> items = new ArrayList<>(
                player.getInventory()
                        .getItems()
                        .values()
        );

        if (items.isEmpty()) {

            return Collections.emptyList();

        } else {

            return items.stream()
                    .map(ItemMapper::toDto)
                    .toList();
        }
    }

    public List<DialogueDto> loadDialogueDtoOfCurrentScene() {

        return currentScene.getDialogues().stream()
                .map(DialogueMapper::toDto)
                .toList();
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public Chapter getCurrentChapter() {
        return currentChapter;
    }

    public String getNextChapterId() {
        return nextChapterId;
    }

    public Integer getCurrentSceneIndex() {
        return currentSceneIndex;
    }
}
