package main.dao;

import main.dto.SaveGamePersistenceDto;
import main.exception.FileOfSaveNotCreateException;
import main.exception.GameNotSaveException;
import main.exception.SlotOfSaveGameNotFoundException;

public interface SaveGameDao {
    void createSlotOfSave(String slotIndex) throws FileOfSaveNotCreateException;
    void save(SaveGamePersistenceDto saveGamePersistenceDto, String slotIndex) throws GameNotSaveException;
    void delete(String slotIndex) throws SlotOfSaveGameNotFoundException;
    SaveGamePersistenceDto findBySlot(String slotIndex) throws SlotOfSaveGameNotFoundException;
    boolean exists(String slotIndex);
    boolean findAllSlots();
}
