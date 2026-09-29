package main.dao;

import main.model.Chapter;

import java.util.List;

public abstract class ChapterDao implements IDao<Chapter> {
    @Override
    public abstract void save(Chapter chapter);

    @Override
    public abstract Chapter searchById(String idChapter);

    @Override
    public abstract void deleteObjectById(String idChapter);

    public abstract List<Chapter> listAllChapter();
}
