package main.mapper;

import main.dto.ItemDto;
import main.model.Item;

public class ItemMapper {

    public static ItemDto toDto(Item item) {
        return new ItemDto(
                item.getName(),
                item.getDescription(),
                item.getItemType().name());
    }
}
