package main.view.menu;

public enum SaveMenuOption {
    CONTINUE(1),
    NEW_GAME(2),
    LOAD_GAME(3),
    DELETE_SAVE(4),
    BACK_TO_MAIN_MENU(5),
    DEFAULT(6);

    private final int value;

    SaveMenuOption(int value) {
        this.value = value;
    }

    public static SaveMenuOption toSaveMenuOption(int value) {

        return switch (value) {

            case 1 -> CONTINUE;
            case 2 -> NEW_GAME;
            case 3 -> LOAD_GAME;
            case 4 -> DELETE_SAVE;
            case 5 -> BACK_TO_MAIN_MENU;
            default -> DEFAULT;
        };
    }

    public int getValue() {
        return value;
    }
}
