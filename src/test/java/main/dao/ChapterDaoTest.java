package main.dao;

import main.service.ChapterService;
import main.model.Chapter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class ChapterDaoTest {

    @Test
    public void saveChapterToJsonFileTest() {
        String introduceChapterId = "-1";
        Chapter chapter = ChapterService.loadNextChapter(introduceChapterId, null, null);

        ChapterDaoJson chapterDaoJson = new ChapterDaoJson();

        try {
            chapterDaoJson.save(chapter);

            Chapter chapterFromJson = chapterDaoJson.searchById(introduceChapterId);
            assertEquals(introduceChapterId, chapterFromJson.getId(),
                    "O id deve ser igual ao guardado no arquivo.");

            chapterDaoJson.deleteFileByChapterId(introduceChapterId);

            assertThrows(IOException.class, () -> chapterDaoJson.searchById(introduceChapterId),
                    "Buscar um capitulo que não existe arquivo gera uma exceção");

        } catch (IOException ignored) {}
    }
}
