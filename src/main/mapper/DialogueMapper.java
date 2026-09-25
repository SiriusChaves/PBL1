package main.mapper;

import main.dto.DialogueDtoRecord;
import main.model.Choice;
import main.model.Dialogue;

import java.util.ArrayList;
import java.util.List;

public class DialogueMapper {
    public static DialogueDtoRecord toDto(Dialogue dialogue) {
        return new DialogueDtoRecord(
                dialogue.getSpeaker(),
                dialogue.getText());
    }
}
