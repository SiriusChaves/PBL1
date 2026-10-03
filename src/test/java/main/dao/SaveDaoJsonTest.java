package main.dao;

import main.dto.GameStateDto;
import main.dto.NpcDto;
import main.dto.PlayerDto;
import main.dto.SavePersistenceDto;
import main.exception.GameNotSaveException;
import main.exception.SlotOfSaveNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaveDaoJsonTest {

    @Test
    public void saveGameDaoJsonFlowTest() {
        SavePersistenceDto savePersistenceDto = new SavePersistenceDto(
                "Rodrigo",
                "01-10-2026",
                "Arquivos Ocultos de CyberFall",
                new GameStateDto(
                        new PlayerDto("O Estudante", 80, 60),
                        "2A",
                        "3",
                        "5A",
                        List.of("1", "3"),
                        List.of(
                                new NpcDto("Sirius", 70, "ALIADO PLENO"),
                                new NpcDto("Becca", 100, "ALIADO PLENO")),
                        List.of("flagTest01", "flagTest02")));

        SaveDaoJson saveDaoJson = new SaveDaoJson();

        try {

            saveDaoJson.save(savePersistenceDto, "1");

        } catch (GameNotSaveException gameNotSaveException) {
            assertThrows(GameNotSaveException.class, () -> saveDaoJson.save(savePersistenceDto, "7"));
        }

        assertTrue(Files.exists(Path.of("data", "saves", "slot1", "slot1.json")));

        try {
            SavePersistenceDto saveGameSlot1 =  saveDaoJson.findBySlot("1");

            assertEquals("2A", saveGameSlot1.gameStateDto().currentChapterId());
        } catch (SlotOfSaveNotFoundException slotOfSaveNotFoundException) {}


        try {
            saveDaoJson.delete("1");
        } catch (SlotOfSaveNotFoundException slotOfSaveNotFoundException) {}

        assertTrue(Files.notExists(Path.of("data", "saves", "slot1", "slot1.json")));
    }

    @Test
    public void findSlotsOfSaveGameTest() {
        SaveDaoJson saveDaoJson = new SaveDaoJson();

        assertTrue(saveDaoJson.saveFileExists("2"));

        assertFalse(saveDaoJson.findAllSlots());
    }

    @Test
    public void createSlotOfSaveTest() {
        SaveDaoJson saveDaoJson = new SaveDaoJson();
        Path pathSlot1 = Path.of("data", "saves", "slot1","slot1.json");

        assertFalse(Files.exists(pathSlot1));

        saveDaoJson.createSlotOfSave("1");

        assertTrue(Files.exists(pathSlot1));
    }
    @Test
    public void createAndDeleteSlotOfSave() {
        Path pathSlot3 = Path.of("data", "saves", "slot3", "slot3.json");

        SaveDaoJson saveDaoJson = new SaveDaoJson();

        saveDaoJson.createSlotOfSave("3");

        assertTrue(Files.exists(pathSlot3));

        try {
            saveDaoJson.delete("3");
        } catch (SlotOfSaveNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertTrue(Files.notExists(pathSlot3));
    }

    public void hasSaveDataInSlotOfSave() {

        Path pathSlot1 = Path.of("data", "saves", "slot1", "slot1.json");
        String slotVazioIndex = "1";

        try {
            Files.deleteIfExists(pathSlot1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SaveDao saveDao = new SaveDaoJson();

        saveDao.createSlotOfSave(slotVazioIndex);

        assertTrue(Files.exists(pathSlot1));

        assertTrue(saveDao.saveFileExists(slotVazioIndex));

        assertFalse(saveDao.hasSaveData(slotVazioIndex));

        String slotWithData = "3";
        assertTrue(saveDao.hasSaveData(slotWithData));
    }

}
