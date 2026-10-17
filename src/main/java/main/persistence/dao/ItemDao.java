package main.persistence.dao;

import main.persistence.dto.ItemPersistenceDto;
import main.exception.ItemNotFoundException;

public interface ItemDao {
    ItemPersistenceDto[] loadItems() throws ItemNotFoundException;

}
