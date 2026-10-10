package main.model;

public enum Preference {
    AUTOSAVE(1,"Save automático no início dos capítulos"),
    DEFAULT(2, "Opção default");

    private final Integer value;
    private final String description;

    Preference(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    String getDescription() {
        return description;
    }

    public static Preference toPreference(int value) {
        return switch (value) {
            case 1 -> AUTOSAVE;
            default -> DEFAULT;
        };
    }
}
