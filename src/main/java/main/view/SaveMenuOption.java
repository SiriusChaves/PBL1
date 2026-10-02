package main.view;

public enum SaveMenuOption {
    CONTINUE(1),
    CREATE_NEW_SAVE(2),
    LOAD_EXISTING_SAVE(3),
    DEFAULT(4);

    private final int value;

    SaveMenuOption(int value) {
        this.value = value;
    }

    public static SaveMenuOption toSaveMenuOption(int value) {
        return switch (value) {
            case 1 -> CONTINUE;
            case 2 -> CREATE_NEW_SAVE;
            case 3 -> LOAD_EXISTING_SAVE;
            default -> DEFAULT;
        };
    }

    public int getValue() {
        return value;
    }
}
