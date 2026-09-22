package main.dto;

import main.model.Choice;
import main.model.Dialogue;
import main.model.Scene;

import java.util.ArrayList;
import java.util.List;

public class SceneDto {
    private final int numberDialogues;
    private final int numberChoices;
    private final List<String> dialogues;
    private final List<String> choices;

    public SceneDto(Scene scene, List<Choice> avaliableChoices) {
        this.numberDialogues = scene.getDialogues().size();
        this.dialogues = loadDialogueText(scene);
        this.choices = loadChoicesText(avaliableChoices);
        this.numberChoices = scene.getChoices().size();
    }

    private List<String> loadDialogueText(Scene scene) {
        List<String> dialoguesText = new ArrayList<>();
        for (Dialogue dialogue : scene.getDialogues()) {
            dialoguesText.add(dialogue.getSpeaker() + ": " + dialogue.getText());
        }
        return dialoguesText;
    }

    private List<String> loadChoicesText(List<Choice> avaliableChoices) {
        List<String> choicesText = new ArrayList<>();
        for (Choice choice : avaliableChoices) {
            choicesText.add(choice.getText());
        }
        return choicesText;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getNumberDialogues() {
        return numberDialogues;
    }

    public int getNumberChoices() {
        return numberChoices;
    }

    public List<String> getDialogues() {
        return dialogues;
    }
}
