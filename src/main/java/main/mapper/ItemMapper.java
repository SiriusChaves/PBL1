package main.mapper;

import main.persistence.dto.ItemPersistenceDto;
import main.dto.ItemViewDto;
import main.model.player.Item;
import main.model.player.ItemType;

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
