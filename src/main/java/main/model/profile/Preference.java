package main.model.profile;

public enum Preference {
    AUTOSAVE(1,"Save automático no início dos capítulos"),
    SHOW_COMMAND_HUD(2, "Sempre exibir na tela o menu de comandos usáveis"),
    SHOW_PLAYER_HUD(3, "Sempre exibir o status atual do jogador"),
    DEFAULT(4, "Opção default");


    private final Integer value;
    private final String description;

    Preference(Integer value, String description) {
        this.value = value;
        this.description = description;
    }


    public static Preference toPreference(int value) {

        return switch (value) {
            case 1 -> AUTOSAVE;
            case 2 -> SHOW_COMMAND_HUD;
            case 3 -> SHOW_PLAYER_HUD;
            default -> DEFAULT;
        };
    }

    public String getDescription() {
        return description;
    }

    public Integer getValue() {
        return value;
    }
}
