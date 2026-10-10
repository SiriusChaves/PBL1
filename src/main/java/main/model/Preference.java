package main.model;

public enum Preference {
    AUTOSAVE("Save automático no início dos capítulos");

    private final String description;

    Preference(String description) {
        this.description = description;
    }

    String getDescription() {
        return description;
    }
}
