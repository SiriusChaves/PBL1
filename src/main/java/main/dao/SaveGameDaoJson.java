package main.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dto.SaveGameDto;
import main.exception.GameNotSaveException;
import main.exception.SlotGameSessionNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SaveGameDaoJson implements SaveGameDao {
    @Override
    public void saveGame(SaveGameDto saveGameDto, String slotIndex) throws GameNotSaveException {

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
    public void deleteSaveGame(String slotIndex) throws SlotGameSessionNotFoundException {

        Path path = Path.of("data", "saves", "slot" + slotIndex, "slot" + slotIndex + ".json");

       try {
           Files.deleteIfExists(path);
       } catch (IOException ioException) {
           throw new SlotGameSessionNotFoundException("Não encontrou o slot" + slotIndex + " para deletar");
       }
    }


    @Override
    public SaveGameDto searchBySlotIndex(String slotIndex) throws SlotGameSessionNotFoundException {

        Path path = Path.of("data", "saves", "slot" + slotIndex, "slot" + slotIndex + ".json");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (Reader reader = Files.newBufferedReader(path)) {

            return gson.fromJson(reader, SaveGameDto.class);

        } catch (IOException ioException) {
            throw new SlotGameSessionNotFoundException("Erro ao buscar o slot" + slotIndex + " para carregamento");
        }
    }
}
