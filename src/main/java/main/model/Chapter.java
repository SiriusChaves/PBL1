package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chapter {
    private final String id;
    private final String idNextChapter;
    private final String title;
    private final List<Scene> scenes;
    private final List<Choice> finalChoices;
    private final Item rewardItem;

    public Chapter(String id, String idNextChapter, String title, List<Scene> scenes, List<Choice> lastChoices, Item reward) {
        this.id = id;
        this.idNextChapter = idNextChapter;
        this.title = title;
        this.scenes = scenes;
        this.finalChoices = lastChoices;
        this.rewardItem = reward;
    }

    public Chapter(String id, String idNextChapter, String title, List<Scene> scenes,  List<Choice> lastChoice) {
        this.id = id;
        this.idNextChapter = idNextChapter;
        this.title = title;
        this.scenes = scenes;
        this.finalChoices = lastChoice;
        this.rewardItem = Item.NONE;
    }

    public Chapter(String id, String idNextChapter, String title, List<Scene> scenes) {
        this.id = id;
        this.idNextChapter = idNextChapter;
        this.title = title;
        this.scenes = scenes;
        this.finalChoices = new ArrayList<>();
        this.rewardItem = Item.NONE;
    }

    public boolean containsFinalChoices() {
        return !finalChoices.isEmpty();
    }

    public boolean containsRewardItem() {
        return !rewardItem.equals(Item.NONE);
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
        return Collections.unmodifiableList(finalChoices);
    }
    public List<Scene> getScenes() {
        return Collections.unmodifiableList(scenes);
    }
}
