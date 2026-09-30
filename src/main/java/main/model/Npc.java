package main.model;

import java.util.Objects;

public class Npc implements Comparable<Npc> {

    public static final Npc NONE = new Npc("sem nome");

    private final String name;
    private int trustLevel;

    public Npc(String name) {
        this.name = name;
        this.trustLevel = 50;
    }

    public void increaseTrustLevel(int value) {
        trustLevel += value;
        if (trustLevel > 100) {trustLevel = 100;}
    }

    public void decreaseTrustLevel(int value) {
        trustLevel -= Math.abs(value);
        if (trustLevel < 0) {trustLevel = 0;}
    }

    public String getName() {
        return name;
    }
    public int getTrustLevel() {
        return trustLevel;
    }

    public String getRelationshipTier() {
        if (trustLevel <= 24) {
            return "EM SUSPEITA";
        } else if (trustLevel <= 59) {
            return "NEUTRO";
        } else {
            return "ALIADO PLENO";
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof Npc ps) {
            return Objects.equals(this.name, ps.getName());
        }
        return false;
    }

    @Override
    public int compareTo(Npc ps) {
        return this.name.compareTo(ps.getName());
    }
}
