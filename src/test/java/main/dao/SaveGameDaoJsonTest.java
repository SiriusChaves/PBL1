package main.dao;

import main.dto.GameStateDto;
import main.dto.NpcDto;
import main.dto.PlayerDto;
import main.dto.SaveGameDto;
import main.exception.GameNotSaveException;
import main.exception.SlotOfSaveGameNotFoundException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaveGameDaoJsonTest {

    @Test
    public void saveGameDaoJsonFlowTest() {
        SaveGameDto saveGameDto = new SaveGameDto(
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

        SaveGameDaoJson saveGameDaoJson = new SaveGameDaoJson();

        try {

            saveGameDaoJson.save(saveGameDto, "1");

        } catch (GameNotSaveException gameNotSaveException) {
            assertThrows(GameNotSaveException.class, () -> saveGameDaoJson.save(saveGameDto, "7"));
        }

        assertTrue(Files.exists(Path.of("data", "saves", "slot1", "slot1.json")));

        try {
            SaveGameDto saveGameSlot1 =  saveGameDaoJson.findBySlot("1");

            assertEquals("2A", saveGameSlot1.gameStateDto().currentChapterId());
        } catch (SlotOfSaveGameNotFoundException slotOfSaveGameNotFoundException) {}


        try {
            saveGameDaoJson.delete("1");
        } catch (SlotOfSaveGameNotFoundException slotOfSaveGameNotFoundException) {}

        assertTrue(Files.notExists(Path.of("data", "saves", "slot1", "slot1.json")));
    }

    @Test
    public void findSlotsOfSaveGameTest() {
        SaveGameDaoJson saveGameDaoJson = new SaveGameDaoJson();

        assertTrue(saveGameDaoJson.exists("2"));

        assertFalse(saveGameDaoJson.findAllSlots());
    }
}
