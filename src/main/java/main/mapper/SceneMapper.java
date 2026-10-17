package main.mapper;

import main.dto.DialogueDto;
import main.dto.SceneDto;
import main.model.story.Choice;
import main.model.story.Scene;

import java.util.List;

public class SceneMapper {
    public static SceneDto toDto(Scene scene, List<Choice> availableChoices, List<DialogueDto> dialogues) {
        return new SceneDto(
                scene.getDialogues().size(),
                availableChoices.size(),
                loadChoicesText(availableChoices),
                dialogues);
    }

    private static List<String> loadChoicesText(List<Choice> availableChoices) {

        return availableChoices.stream()
                .map(Choice::getText)
                .toList();

    }
}
