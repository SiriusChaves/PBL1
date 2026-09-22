package main.dto;

import main.model.Item;

public class ItemDto {
    private final String name;
    private final String description;
    private final String itemType;

    public ItemDto(Item item) {
        this.name = item.getName();
        this.description = item.getDescription();
        this.itemType = item.getItemType().name();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getItemType() {
        return itemType;
    }
}
