package main.mapper;

import main.dto.DialogueDto;
import main.model.Dialogue;

public class DialogueMapper {
    public static DialogueDto toDto(Dialogue dialogue) {
        return new DialogueDto(
                dialogue.getSpeaker(),
                dialogue.getText());
    }
}
