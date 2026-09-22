package main.dto;

import main.model.Npc;

public class NpcDto {
    private final String name;
    private final int trustLevel;
    private final String relationshipTier;

    public NpcDto(Npc npc) {
        this.name = npc.getName();
        this.trustLevel = npc.getTrustLevel();
        this.relationshipTier = npc.getRelationshipTier();
    }

    public String getName() {
        return name;
    }

    public int getTrustLevel() {
        return trustLevel;
    }

    public String getRelationshipTier() {
        return relationshipTier;
    }
}
