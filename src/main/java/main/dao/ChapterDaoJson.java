package main.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.model.Chapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ChapterDaoJson implements ChapterDao {

    public ChapterDaoJson() {}

    @Override
    public Chapter searchById(String idChapter) throws IOException {
        Path path = Path.of("src", "main", "resources", "chapters", "chapter" + idChapter + ".json");

        Gson gson = new Gson();

        try (Reader reader = Files.newBufferedReader(path)) {

             return gson.fromJson(reader, Chapter.class);

        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException("O capitulo não existente");
        } catch (IOException ioException) {
            throw new IOException("Erro ao carregar capitulo ou capitulo inexistente");
        }
    }
}
