package main.mapper;

import main.dto.ItemViewDto;
import main.model.Item;

public class ItemMapper {

    public static ItemViewDto toDto(Item item) {
        return new ItemViewDto(
                item.getName(),
                item.getDescription());
    }
}
