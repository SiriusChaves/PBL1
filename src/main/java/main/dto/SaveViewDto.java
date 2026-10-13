package main.dto;

public record SaveViewDto(
        String playerName,
        String dateSave,
        String currentChapterName,
        boolean isGameCompleted
) {}
