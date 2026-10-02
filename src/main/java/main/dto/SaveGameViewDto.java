package main.dto;

public record SaveGameViewDto(
        String playerName,
        String dateSave,
        String currentChapterName
) {}
