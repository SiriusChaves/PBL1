package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chapter {
    private final String id;
    private String idNextChapter;
    private final String title;
    private final List<Scene> scenes;
    private final List<Choice> finalChoices;
    private final Item rewardItem;

    public Chapter(Chapter chapter) {
        this.id = chapter.getId();
        this.idNextChapter = chapter.getIdNextChapter();
        this.title = chapter.getTitle();
        this.scenes = new ArrayList<>(chapter.getScenes());
        this.finalChoices = new ArrayList<>(chapter.getFinalChoices());
        this.rewardItem = chapter.getRewardItem();
    }

    public String getTitle() {
        return title;
    }
    public String getId() {
        return id;
    }
    public String getIdNextChapter() {
        return idNextChapter;
    }
    public List<Choice> getFinalChoices() {
        return finalChoices;
    }
    public List<Scene> getScenes() {
        return scenes;
    }

    public Item getRewardItem() {
        return rewardItem;
    }

    public void setIdNextChapter(String id) {
        this.idNextChapter = id;
    }
}
