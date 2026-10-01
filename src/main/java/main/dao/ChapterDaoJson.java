package main.dao;

import com.google.gson.Gson;
import main.exception.ChapterNotFoundException;
import main.model.Chapter;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChapterDaoJson implements ChapterDao {

    public ChapterDaoJson() {}

    @Override
    public Chapter searchById(String idChapter) throws ChapterNotFoundException {
        Path path = Path.of("src", "main", "resources", "chapters", "chapter" + idChapter + ".json");

        Gson gson = new Gson();

        try (Reader reader = Files.newBufferedReader(path)) {

             return gson.fromJson(reader, Chapter.class);

        } catch (IOException ioException) {
            throw new ChapterNotFoundException("Erro ao carregar capitulo ou capitulo inexistente");
        }
    }
}
