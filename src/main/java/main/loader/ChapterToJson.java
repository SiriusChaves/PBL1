package main.loader;


import main.dao.ChapterDaoJson;
import main.model.Chapter;

import java.io.IOException;
import java.util.List;

public class ChapterToJson {

    private final ChapterDaoJson chapterDaoJson = new ChapterDaoJson();

    public void parseChapterToJson(Chapter chapter) {
        try {
            chapterDaoJson.save(chapter);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChapterToJson chapterToJson = new ChapterToJson();

        Chapter chapter = ChapterLoader.buildChapter4B();

        chapterToJson.parseChapterToJson(chapter);

    }
}
