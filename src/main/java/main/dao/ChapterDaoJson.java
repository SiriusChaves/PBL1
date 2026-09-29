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
    public void save(Chapter chapter) throws IOException {
        String idChapter = chapter.getId();
        Path path = Path.of("src", "main", "resources", "chapters", "chapter" + idChapter + ".json");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (Writer writer = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {

            gson.toJson(chapter, writer);

        } catch (IOException ioException) {
            throw new IOException("Erro ao salvar o arquivo");
        }
    }

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

    @Override
    public void deleteFileByChapterId(String chapterId) throws IOException {
        Path path = Path.of("src", "main", "resources", "chapters", "chapter" + chapterId + ".json");
        Files.deleteIfExists(path);
    }

    @Override
    public void deleteObjectById(String chapterId) throws IOException {
        Path path = Path.of("src", "main", "resources", "chapters", "chapter" + chapterId + ".json");
        if (Files.exists(path))
            Files.writeString(path, "");
         else
             throw new FileNotFoundException("O Arquivo não existe!");
    }
}
