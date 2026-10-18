package main.mapper;

import main.dto.ChapterDto;
import main.model.story.Chapter;
import main.model.story.Choice;

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

       return availableFinalChoices.stream()
                .map(Choice::getText)
                .toList();

    }
}
