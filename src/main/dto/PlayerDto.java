package main.dto;

import main.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerDto {
    private final String name;
    private final int sanity;
    private final int knowledge;
    private final List<String> relationships;

    public PlayerDto(Player player) {
        this.name = player.getName();
        this.sanity = player.getSanity();
        this.knowledge = player.getKnowledge();
        this.relationships = loadRelationshipsText(player);
    }

    private List<String> loadRelationshipsText(Player player) {
        List<String> namesRelationships = new ArrayList<>();
        for (String name : player.getRelationships().keySet()){
            namesRelationships.add(name);
        }
        return namesRelationships;
    }

    public String getName() {
        return name;
    }

    public int getSanity() {
        return sanity;
    }

    public int getKnowledge() {
        return knowledge;
    }

    public List<String> getRelationships() {
        return relationships;
    }
}
