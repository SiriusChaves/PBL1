package main.dto;

import main.model.Chapter;
import main.model.Choice;

import java.util.ArrayList;
import java.util.List;

public class ChapterDto {
    private final String id;
    private final String title;
    private final int numberScenes;
    private final int numberLastChoices;
    private final List<String> choices;

    public ChapterDto(Chapter chapter, List<Choice> avaliableFinalChoices) {
        this.id = chapter.getId();
        this.title = chapter.getTitle();
        this.numberScenes = chapter.getScenes().size();
        this.choices = loadChoicesText(avaliableFinalChoices);
        this.numberLastChoices = chapter.getFinalChoices().size();
    }

    private List<String> loadChoicesText(List<Choice> avaliableFinalChoices) {
        List<String> choicesText = new ArrayList<>();;
        for (Choice choice : avaliableFinalChoices)
            choicesText.add(choice.getText());

        return choicesText;
    }

    public String getTitle() {
        return title;
    }
    public int getNumberScenes() {
        return numberScenes;
    }
    public int getNumberLastChoices() {
        return numberLastChoices;
    }
    public List<String> getChoices() {
        return choices;
    }
}
