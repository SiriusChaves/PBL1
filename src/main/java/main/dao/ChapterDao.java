package main.dao;

import main.model.Chapter;

import java.io.IOException;
import java.util.List;

public interface ChapterDao {
    Chapter searchById(String chapterId) throws IOException;
}
