package main.model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final String id;
    private List<Dialogue> dialogues;
    private List<Choice> choices;

    public Scene(String id, List<Dialogue> dialogues) {
        this.id = id;
        this.dialogues = dialogues;
        this.choices = new ArrayList<>();
    }

    public Scene(String id, List<Dialogue> dialogues, List<Choice> choices) {
        this.id = id;
        this.dialogues = dialogues;
        this.choices = choices;
    }

    public List<Dialogue> getDialogues() {
        return dialogues;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public String getId() {
        return id;
    }

}
