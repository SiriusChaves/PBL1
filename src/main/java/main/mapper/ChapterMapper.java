package main.mapper;

import main.dto.ChapterDto;
import main.model.Chapter;
import main.model.Choice;

import java.util.ArrayList;
import java.util.List;

public class ChapterMapper {
    public static ChapterDto toDto(Chapter chapter, List<Choice> availableFinalChoices) {
        return new ChapterDto(
                chapter.getId(),
                chapter.getTitle(),
                chapter.getScenes().size(),
                availableFinalChoices.size(),
                loadChoicesText(availableFinalChoices));
    }

    private static List<String> loadChoicesText(List<Choice> availableFinalChoices) {
        List<String> choicesText = new ArrayList<>();;
        for (Choice choice : availableFinalChoices)
            choicesText.add(choice.getText());

        return choicesText;
    }
}
