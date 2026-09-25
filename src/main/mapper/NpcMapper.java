package main.mapper;

import main.dto.NpcDto;
import main.model.Npc;

public class NpcMapper {
    public static NpcDto toDto(Npc npc) {
        return new NpcDto(
                npc.getName(),
                npc.getTrustLevel(),
                npc.getRelationshipTier());
    }
}
