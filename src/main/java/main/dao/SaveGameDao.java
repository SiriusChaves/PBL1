package main.dao;

import main.dto.SaveGameDto;

import java.io.IOException;

public interface SaveGameDao {
    void saveGame(SaveGameDto saveGameDto, String slotIndex) throws IOException;
    void deleteSaveGame(String slotIndex) throws  IOException;
    SaveGameDto searchBySlotIndex(String slotIndex) throws IOException;
}
