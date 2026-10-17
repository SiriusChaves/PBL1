package main.persistence.dto;

import main.dto.GameStateDto;

public record SavePersistenceDto(
        String playerName,
        String dataHora, // will be replaced with LocalDateTime class
        String lastChapterName,
        GameStateDto gameStateDto
) {}
