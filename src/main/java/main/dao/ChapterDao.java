package main.dao;

import main.exception.ChapterNotFoundException;
import main.model.Chapter;

import java.io.IOException;
import java.util.List;

public interface ChapterDao {
    Chapter searchById(String chapterId) throws ChapterNotFoundException;
}
