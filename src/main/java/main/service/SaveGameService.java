package main.service;

import main.dao.ItemDao;
import main.dao.SaveGameDao;
import main.dto.SaveGamePersistenceDto;
import main.exception.SaveSlotsFullException;
import main.exception.SlotOfSaveGameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SaveGameService {
    private SaveGameDao saveGameDao;
    private ItemDao itemDao;

    public SaveGameService(SaveGameDao saveGameDao, ItemDao itemDao) {
        this.saveGameDao = saveGameDao;
        this.itemDao = itemDao;
    }

    public void createNewGame(String slotIndex) throws SaveSlotsFullException {
        if (saveGameDao.findAllSlots()) {
            throw new SaveSlotsFullException("Todos os saves estão preenchidos!");
        } else {
            saveGameDao.createSlotOfSave(slotIndex);
        }
    }

    public void deleteSlotSave(String slotIndex) throws SlotOfSaveGameNotFoundException {
        saveGameDao.delete(slotIndex);
    }

    public List<SaveGamePersistenceDto> getDataOfAllSaves() {
        List<SaveGamePersistenceDto> savesOfGameSession = new ArrayList<>();
        List<String> indexOfAllSlots = List.of("1", "2", "3");

        for (String index : indexOfAllSlots)
            savesOfGameSession.add(saveGameDao.findBySlot(index));

        return savesOfGameSession;

    }
}
