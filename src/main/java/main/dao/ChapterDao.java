package main.dao;

import main.model.Chapter;

import java.util.List;

public interface ChapterDao extends IDao<Chapter> {
    public abstract List<Chapter> listAllChapter();
}
