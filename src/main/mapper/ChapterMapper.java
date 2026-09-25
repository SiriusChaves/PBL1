package main.mapper;

import main.dto.ChapterDto;
import main.model.Chapter;
import main.model.Choice;

import java.util.ArrayList;
import java.util.List;

public class ChapterMapper {
    public static ChapterDto toDto(Chapter chapter, List<Choice> avaliableFinalChoices) {
        return new ChapterDto(
                chapter.getId(),
                chapter.getTitle(),
                chapter.getScenes().size(),
                chapter.getFinalChoices().size(),
                loadChoicesText(avaliableFinalChoices));
    }

    private static List<String> loadChoicesText(List<Choice> avaliableFinalChoices) {
        List<String> choicesText = new ArrayList<>();;
        for (Choice choice : avaliableFinalChoices)
            choicesText.add(choice.getText());

        return choicesText;
    }
}
