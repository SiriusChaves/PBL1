package main.mapper;

import main.dto.NpcDtoRecord;
import main.model.Npc;

public class NpcMapper {
    public static NpcDtoRecord toDto(Npc npc) {
        return new NpcDtoRecord(
                npc.getName(),
                npc.getTrustLevel(),
                npc.getRelationshipTier());
    }
}
