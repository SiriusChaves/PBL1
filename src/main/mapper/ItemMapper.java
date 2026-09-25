package main.mapper;

import main.dto.ItemDtoRecord;
import main.model.Item;

public class ItemMapper {

    public ItemDtoRecord toDto(Item item) {
        return new ItemDtoRecord(
                item.getName(),
                item.getDescription(),
                item.getItemType().name());
    }
}
