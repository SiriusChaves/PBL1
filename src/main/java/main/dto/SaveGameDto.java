package main.dto;

public record SaveGameDto (
        String playerName,
        String data_hora, // will be replaced with LocalDateTime class
        String lastChapterName,
        GameStateDto gameStateDto
) {}
