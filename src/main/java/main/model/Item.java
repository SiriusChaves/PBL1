package main.model;

import java.util.Objects;

public class Item {

    public static final Item NONE = new Item(
           "0", "sem nome", "sem descricao", ItemType.NONE);

    private final String name;
    private final String description;
    private final ItemType type;
    private final String id;

    public Item(String id, String name, String description, ItemType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;

    }

    public Item(Item item) {
        this.name = item.getName();
        this.description = item.getDescription();
        this.type = item.getItemType();
        this.id = item.getId();
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public ItemType getItemType() {
        return type;
    }
    public String getId() { return id; }

    @Override
    public String toString() {
        return "Item: " + this.name +
                " | Descrição do Item: " + this.description +
                " | Tipo: " + this.type +
                " | Descrição do tipo:" +  this.type.getDescription();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof Item objItem) {
            return Objects.equals(this.id, objItem.getId());
        }
        return false;
    }
}
