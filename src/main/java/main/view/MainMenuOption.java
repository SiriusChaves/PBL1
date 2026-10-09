package main.view;

public enum MainMenuOption {
    START_GAME(1),
    INSTRUCTIONS(2),
    CREDITS(3),
    ACHIEVEMENTS(4),
    EXIT(5),
    DEFAULT(6);

    private final int valor;

    MainMenuOption(int valor) {
        this.valor = valor;
    }

    public static MainMenuOption toMainMenuOption(int valor) {
        return switch (valor) {
          case 1 -> START_GAME;
          case 2 -> INSTRUCTIONS;
          case 3 -> CREDITS;
          case 4 -> ACHIEVEMENTS;
          case 5 -> EXIT;
          default -> DEFAULT;
        };
    }

    public int getValue() {
        return valor;
    }
}
