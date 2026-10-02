package main.dto;

public record SavePersistenceDto(
        String playerName,
        String dataHora, // will be replaced with LocalDateTime class
        String lastChapterName,
        GameStateDto gameStateDto
) {}
