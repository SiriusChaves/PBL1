package main.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<String, Item> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public void addItem(Item item) {
        if (!item.equals(Item.NONE))
            items.put(item.getId(), new Item(item));
    }

    public void removeItem(String itemId) {
        items.remove(itemId);
    }

    public boolean hasItem(String itemId) {
        return items.containsKey(itemId);
    }

    public Map<String, Item> getItems() {
        return Collections.unmodifiableMap(items);
    }
}
