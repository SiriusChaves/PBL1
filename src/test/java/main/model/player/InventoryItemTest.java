package main.model.player;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

    @Test
    void itemEqualityShouldUseId() {

        Item first = new Item(
                "1",
                "A",
                "Primeiro",
                ItemType.KEY_ITEM
        );

        Item second = new Item(
                "1",
                "B",
                "Segundo",
                ItemType.CONSUMABLE
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void itemTypeShouldBeConvertedFromString() {

        assertEquals(
                ItemType.KEY_ITEM,
                ItemType.toItemType("KEY_ITEM")
        );

        assertEquals(
                ItemType.CONSUMABLE,
                ItemType.toItemType("CONSUMABLE")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> ItemType.toItemType("INVALID")
        );
    }

    @Test
    void inventoryShouldAddAndRemoveItems() {

        Inventory inventory = new Inventory();

        Item item = new Item(
                "1",
                "Script",
                "Descrição",
                ItemType.KEY_ITEM
        );

        inventory.addItem(item);

        assertTrue(inventory.hasItem("1"));
        assertEquals(1, inventory.getItems().size());

        inventory.removeItem("1");

        assertFalse(inventory.hasItem("1"));
    }

    @Test
    void inventoryShouldIgnoreNoneItem() {

        Inventory inventory = new Inventory();

        inventory.addItem(Item.NONE);

        assertTrue(inventory.getItems().isEmpty());
    }

    @Test
    void inventoryShouldStoreCopyInsteadOfOriginalReference() {

        Inventory inventory = new Inventory();

        Item item = new Item(
                "1",
                "Script",
                "Descrição",
                ItemType.KEY_ITEM
        );

        inventory.addItem(item);

        Item stored =
                inventory.getItems().get("1");

        assertEquals(item, stored);
        assertNotSame(item, stored);
    }

    @Test
    void duplicateIdShouldReplacePreviousItem() {

        Inventory inventory = new Inventory();

        inventory.addItem(
                new Item(
                        "1",
                        "Primeiro",
                        "...",
                        ItemType.KEY_ITEM
                )
        );

        inventory.addItem(
                new Item(
                        "1",
                        "Atualizado",
                        "...",
                        ItemType.UPGRADE
                )
        );

        assertEquals(1, inventory.getItems().size());
        assertEquals(
                "Atualizado",
                inventory.getItems()
                        .get("1")
                        .getName()
        );
    }

    @Test
    void inventoryMapShouldBeReadOnly() {

        Inventory inventory = new Inventory();

        Map<String, Item> items =
                inventory.getItems();

        assertThrows(
                UnsupportedOperationException.class,
                () -> items.put(
                        "1",
                        new Item(
                                "1",
                                "A",
                                "...",
                                ItemType.KEY_ITEM
                        )
                )
        );
    }
}
