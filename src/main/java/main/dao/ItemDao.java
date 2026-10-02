package main.dao;

import main.dto.ItemPersistenceDto;
import main.exception.ItemNotFoundException;

public interface ItemDao {
    ItemPersistenceDto[] loadItems() throws ItemNotFoundException;

}
