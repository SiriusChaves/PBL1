package main.dto;

import java.util.List;

public record GameStateDto(
        PlayerDto playerDto,
        String currentChapterId,
        int currentSceneIndex,
        List<String> itemsId,
        List<NpcDto> relationships,
        List<String> activeGameFlags
) {}
