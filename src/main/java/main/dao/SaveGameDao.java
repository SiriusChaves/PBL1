package main.dao;

import main.dto.SaveGameDto;
import main.exception.GameNotSaveException;
import main.exception.SlotOfSaveGameNotFoundException;

import java.io.FileNotFoundException;

public interface SaveGameDao {
    void createSlotOfSave(String slotIndex) throws FileNotFoundException;
    void save(SaveGameDto saveGameDto, String slotIndex) throws GameNotSaveException;
    void delete(String slotIndex) throws SlotOfSaveGameNotFoundException;
    SaveGameDto findBySlot(String slotIndex) throws SlotOfSaveGameNotFoundException;
    boolean exists(String slotIndex);
    boolean findAllSlots();
}
