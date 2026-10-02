package main.mapper;

import main.dto.ItemPersistenceDto;
import main.dto.ItemViewDto;
import main.model.Item;
import main.model.ItemType;

public class ItemMapper {

    public static ItemViewDto toDto(Item item) {
        return new ItemViewDto(
                item.getName(),
                item.getDescription());
    }

    public static Item toEntity(ItemPersistenceDto itemPersistenceDto) {
        return new Item(
                itemPersistenceDto.id(),
                itemPersistenceDto.name(),
                itemPersistenceDto.description(),
                ItemType.toItemType(itemPersistenceDto.type()));
    }
}
