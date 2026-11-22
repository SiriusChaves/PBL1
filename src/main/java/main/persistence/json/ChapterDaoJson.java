package main.persistence.json;

import com.google.gson.Gson;
import main.exception.ChapterNotFoundException;
import main.model.story.Chapter;
import main.persistence.dao.ChapterDao;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChapterDaoJson implements ChapterDao {

    public ChapterDaoJson() {}

    @Override
    public Chapter findByID(String idChapter) throws ChapterNotFoundException {

        String resourcePath = "/chapters/chapter" + idChapter + ".json";

        try (var inputStream = getClass().getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new ChapterNotFoundException(
                        "Capítulo inexistente: " + idChapter
                );
            }

            try (Reader reader = new java.io.InputStreamReader(
                    inputStream,
                    StandardCharsets.UTF_8
            )) {
                Gson gson = new Gson();

                return gson.fromJson(reader, Chapter.class);
            }

        } catch (IOException ioException) {

            throw new ChapterNotFoundException(
                    "Erro ao carregar capitulo ou capitulo inexistente"
            );
        }
    }
}
