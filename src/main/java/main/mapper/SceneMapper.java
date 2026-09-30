package main.mapper;

import main.dto.DialogueDto;
import main.dto.SceneDto;
import main.model.Choice;
import main.model.Scene;

import java.util.ArrayList;
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
        List<String> choicesText = new ArrayList<>();
        for (Choice choice : availableChoices) {
            choicesText.add(choice.getText());
        }
        return choicesText;
    }
}
