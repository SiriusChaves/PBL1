package main.dao;

import main.dto.ItemPersistenceDto;
import main.mapper.ItemMapper;
import main.model.Item;
import main.model.ItemType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemDaoJsonTest {
    @Test
    public void convertPersistenceItemToItemEntity() {
        ItemDao itemDao = new ItemDaoJson();

        Item itemExemploTest = new Item(
                "3",
                "Chip Neuro-Virtual",
                "item consumivel",
                ItemType.CONSUMABLE);

        ItemPersistenceDto[] itemsPersistenceDto = itemDao.loadItems();

        List<Item> items = new ArrayList<>();

        for (ItemPersistenceDto itemPersistenceDto : itemsPersistenceDto) {
            items.add(ItemMapper.toEntity(itemPersistenceDto));
        }

        assertTrue(items.contains(itemExemploTest));

        Item itemPersistence = Item.NONE;
        for (Item item : items) {
            if (item.getId().equals("3")) {
                itemPersistence = item;
            }
        }

        assertEquals(itemExemploTest, itemPersistence,
                "O item persistido deve ser igual aos item existentes em memoria");
    }
}
