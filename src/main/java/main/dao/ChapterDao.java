package main.dao;

import main.exception.ChapterNotFoundException;
import main.model.Chapter;

public interface ChapterDao {
    Chapter findByID(String chapterId) throws ChapterNotFoundException;
}
