package main.model;

public enum ItemType {
    NONE(0, "Item nulo"),
    CONSUMABLE(1, "Item consumível pelo personagem"),
    KEY_ITEM(2, "Item para desbloquear eventos"),
    UPGRADE(3, "Item para melhorias nos equipamentos"),
    ITEM_NARRATIVO(4, "Item que revela informações sobre a narrativa");


    private final int value;
    private final String description;

    ItemType(int valor, String description) {
        this.value = valor;
        this.description = description;
    }

    public static ItemType toItemType(String itemTypeString) {
        return switch (itemTypeString) {
            case "KEY_ITEM" -> KEY_ITEM;
            case "CONSUMABLE" -> CONSUMABLE;
            case "UPGRADE" -> UPGRADE;
            case "ITEM_NARRATIVO" -> ITEM_NARRATIVO;
            case "NONE" -> NONE;
            default -> throw new IllegalArgumentException("Unexpected value: " + itemTypeString);
        };
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
