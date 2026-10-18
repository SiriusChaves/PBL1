package main.persistence.dao;

import main.exception.ChapterNotFoundException;
import main.model.story.Chapter;

public interface ChapterDao {

    Chapter findByID(String chapterId) throws ChapterNotFoundException;
}
