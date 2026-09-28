package main.dto;

import java.util.List;

public record SceneDto(
        int numberDialogues,
        int numberChoices,
        List<String> choices,
        List<DialogueDto> dialogues) {}
