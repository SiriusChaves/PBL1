package main.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dto.SaveGameDto;
import main.exception.FileOfSaveNotCreateException;
import main.exception.GameNotSaveException;
import main.exception.SaveSlotsFullException;
import main.exception.SlotOfSaveGameNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SaveGameDaoJson implements SaveGameDao {
    @Override
    public void createSlotOfSave(String slotIndex) throws FileOfSaveNotCreateException {
        Path path = Path.of("data", "saves", "slot" + slotIndex, "slot" + slotIndex + ".json");

        try {
            Files.createFile(path);
        } catch (IOException ioException) {
            throw new FileOfSaveNotCreateException(
                    "Não foi possivel criar o arquivo de save para o slot" + slotIndex);
        }
    }

    @Override
    public void save(SaveGameDto saveGameDto, String slotIndex) throws GameNotSaveException {

        Path path = Path.of("data", "saves", "slot" + slotIndex, "slot" + slotIndex + ".json");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (Writer writer = Files.newBufferedWriter(
                path,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {

            gson.toJson(saveGameDto, writer);

        } catch (IOException ioException) {
            throw new GameNotSaveException("Erro ao salvar o jogo no slot" + slotIndex);
        }
    }

    @Override
    public void delete(String slotIndex) throws SlotOfSaveGameNotFoundException {

        Path path = Path.of("data", "saves", "slot" + slotIndex, "slot" + slotIndex + ".json");

       try {
           Files.deleteIfExists(path);
       } catch (IOException ioException) {
           throw new SlotOfSaveGameNotFoundException("Não encontrou o slot" + slotIndex + " para deletar");
       }
    }


    @Override
    public SaveGameDto findBySlot(String slotIndex) throws SlotOfSaveGameNotFoundException {

        Path path = Path.of("data", "saves", "slot" + slotIndex, "slot" + slotIndex + ".json");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (Reader reader = Files.newBufferedReader(path)) {

            return gson.fromJson(reader, SaveGameDto.class);

        } catch (IOException ioException) {
            throw new SlotOfSaveGameNotFoundException("Erro ao buscar o slot" + slotIndex + " para carregamento");
        }
    }

    @Override
    public boolean exists(String slotIndex) {
        Path path = Path.of("data", "saves", "slot" + slotIndex, "slot" + slotIndex + ".json");
            return (Files.exists(path));
    }

    @Override
    public boolean findAllSlots() {
        Path pathSlot1 = Path.of("data", "saves", "slot1", "slot1.json");
        Path pathSlot2 = Path.of("data", "saves", "slot2", "slot2.json");
        Path pathSlot3 = Path.of("data", "saves", "slot3", "slot3.json");

        return Files.exists(pathSlot1) && Files.exists(pathSlot2) && Files.exists(pathSlot3);
    }
}
