package main.dao;

import main.model.Chapter;

import java.io.IOException;
import java.util.List;

public interface ChapterDao extends IDao<Chapter> {
    Chapter searchById(String chapterId) throws IOException;
}
