package main.persistence.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.persistence.dto.ItemPersistenceDto;
import main.exception.ItemNotFoundException;
import main.persistence.dao.ItemDao;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ItemDaoJson implements ItemDao {
    @Override
    public ItemPersistenceDto[] loadItems()
            throws ItemNotFoundException {

        String resourcePath = "/items/items.json";

        try (var inputStream =
                     getClass().getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new ItemNotFoundException(
                        "Arquivo items.json não encontrado."
                );
            }

            try (Reader reader = new java.io.InputStreamReader(
                    inputStream,
                    java.nio.charset.StandardCharsets.UTF_8
            )) {

                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();

                return gson.fromJson(
                        reader,
                        ItemPersistenceDto[].class
                );
            }

        } catch (IOException exception) {

            throw new ItemNotFoundException(
                    "Erro ao carregar os itens do jogo."
            );
        }
    }
}
