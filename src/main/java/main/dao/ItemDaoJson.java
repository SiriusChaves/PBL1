package main.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dto.ItemPersistenceDto;
import main.exception.ItemNotFoundException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ItemDaoJson implements ItemDao {
    @Override
    public ItemPersistenceDto[] loadItems() throws ItemNotFoundException {
        Path path = Path.of("src", "main", "resources", "items", "items.json");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (Reader reader = Files.newBufferedReader(path)) {

            return gson.fromJson(reader, ItemPersistenceDto[].class);

        } catch (IOException ioException) {
                throw new ItemNotFoundException("Erro ao carregar um item do arquivo de items.json");
        }
    }
}
