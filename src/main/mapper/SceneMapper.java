package main.mapper;

import main.dto.DialogueDto;
import main.dto.SceneDtoRecord;
import main.model.Choice;
import main.model.Scene;

import java.util.ArrayList;
import java.util.List;

public class SceneMapper {
    public static SceneDtoRecord toDto(Scene scene, List<Choice> avaliableChoices, List<DialogueDto> dialogues) {
        return new SceneDtoRecord(
                scene.getDialogues().size(),
                scene.getChoices().size(),
                loadChoicesText(avaliableChoices),
                dialogues);
    }

    private static List<String> loadChoicesText(List<Choice> avaliableChoices) {
        List<String> choicesText = new ArrayList<>();
        for (Choice choice : avaliableChoices) {
            choicesText.add(choice.getText());
        }
        return choicesText;
    }
}
