package main.dao;

import main.dto.SavePersistenceDto;
import main.exception.FileOfSaveNotCreateException;
import main.exception.GameNotSaveException;
import main.exception.SlotOfSaveNotFoundException;

public interface SaveDao {
    void createSlotOfSave(String slotIndex) throws FileOfSaveNotCreateException;
    void save(SavePersistenceDto savePersistenceDto, String slotIndex) throws GameNotSaveException;
    void delete(String slotIndex) throws SlotOfSaveNotFoundException;
    SavePersistenceDto findBySlot(String slotIndex) throws SlotOfSaveNotFoundException;
    boolean saveFileExists(String slotIndex);
    boolean findAllSlots();
    boolean hasSaveData(String slotIndex);
}
