package main.persistence.dto;

import main.dto.GameStateDto;

public record SavePersistenceDto(
        String playerName,
        String dataHora,
        String lastChapterName,
        GameStateDto gameStateDto
) {}
