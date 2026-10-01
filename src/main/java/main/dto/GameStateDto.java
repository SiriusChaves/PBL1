package main.dto;

import java.util.List;

public record GameStateDto(
        PlayerDto player,
        String currentChapterId,
        String nextChapterId,
        String currentSceneId,
        List<String> itemsId,
        List<NpcDto> relationships,
        List<String> activeGameFlags
) {}
