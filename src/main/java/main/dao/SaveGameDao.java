package main.dao;

import main.dto.SaveGameDto;
import main.exception.GameNotSaveException;
import main.exception.SlotOfSaveGameNotFoundException;

public interface SaveGameDao {
    void saveGame(SaveGameDto saveGameDto, String slotIndex) throws GameNotSaveException;
    void deleteSaveGame(String slotIndex) throws SlotOfSaveGameNotFoundException;
    SaveGameDto searchBySlotIndex(String slotIndex) throws SlotOfSaveGameNotFoundException;
}
