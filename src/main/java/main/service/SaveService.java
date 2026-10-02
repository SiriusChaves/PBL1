package main.service;

import main.dao.ItemDao;
import main.dao.SaveDao;
import main.dto.SavePersistenceDto;
import main.exception.SaveSlotsFullException;
import main.exception.SlotOfSaveNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SaveService {
    private SaveDao saveGameDao;
    private ItemDao itemDao;

    public SaveService(SaveDao saveGameDao, ItemDao itemDao) {
        this.saveGameDao = saveGameDao;
        this.itemDao = itemDao;
    }

    public void saveGame(SavePersistenceDto saveDto, String slotIndex) {
        saveGameDao.save(saveDto, slotIndex);
    }

    public void createNewSlotOfGameSession(String slotIndex) throws SaveSlotsFullException {
        if (saveGameDao.findAllSlots()) {
            throw new SaveSlotsFullException("Todos os saves estão preenchidos!");
        } else {
            saveGameDao.createSlotOfSave(slotIndex);
        }
    }

    public void deleteSlotSave(String slotIndex) throws SlotOfSaveNotFoundException {
        saveGameDao.delete(slotIndex);
    }

    public List<SavePersistenceDto> getDataOfAllSaves() {
        List<SavePersistenceDto> savesOfGameSession = new ArrayList<>();
        List<String> indexOfAllSlots = List.of("1", "2", "3");

        for (String index : indexOfAllSlots)
            savesOfGameSession.add(saveGameDao.findBySlot(index));

        return savesOfGameSession;

    }
}
