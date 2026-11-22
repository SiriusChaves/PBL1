package main.observer;

public enum PlayerAttribute {
    SANITY("Sanidade"),
    KNOWLEDGE("Conhecimento");

    private final String displayName;

    PlayerAttribute(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
