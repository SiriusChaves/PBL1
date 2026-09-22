package main.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;
    private int sanity;
    private int knowledge;
    private final Map<String, Npc> relationships = new HashMap<>();
    private final Inventory inventory;

    public Player(String name) {
        this.name = name;
        this.sanity = 50;
        this.knowledge = 50;
        this.inventory = new Inventory();
        initializeNpcRelationships();
    }

    private void initializeNpcRelationships() {
        this.relationships.put("Rebeca", new Npc("Rebeca"));
        this.relationships.put("Gabriel", new Npc("Gabriel"));
        this.relationships.put("Eng. Silas", new Npc("Eng. Silas"));
        this.relationships.put("Lia", new Npc("Lia"));
    }

    public void storeItemInInventory(Item item) {
        inventory.addItem(item);
    }

    public void decreaseNpcTrustLevel(String name, int value) {
        Npc npc = relationships.get(name);
        npc.decreaseTrustLevel(value);
    }

    public void increaseNpcTrustLevel(String name, int value) {
        Npc npc = relationships.get(name);
        npc.increaseTrustLevel(value);
    }

    public void decreaseSanityLevel(int value) {
        sanity -= Math.abs(value);
        if (sanity < 0)
            sanity = 0;
    }

    public void increaseSanityLevel(int value) {
        sanity += value;
        if (sanity > 100)
            sanity = 100;
    }

    public void increaseKnowledgeLevel(int value) {
        knowledge += value;
        if (knowledge > 100)
            knowledge = 100;
    }

    public void decreaseKnowledgeLevel(int value) {
        knowledge -= value;
        if (knowledge < 0)
            knowledge = 0;
    }

    public int getSanity() {
        return sanity;
    }
    public int getKnowledge() {
        return knowledge;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Inventory getInventory() {
        return inventory;
    }

    public Map<String, Npc> getRelationships() {
        return Collections.unmodifiableMap(relationships);
    }
}
