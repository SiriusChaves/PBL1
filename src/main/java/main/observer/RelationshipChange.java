package main.observer;

public record RelationshipChange(
        String npcName,
        int previousTrustLevel,
        int currentTrustLevel
) {

    public int difference() {
        return currentTrustLevel - previousTrustLevel;
    }
}
