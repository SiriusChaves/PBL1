package main.view.javafx.controller.dependency;

import main.view.javafx.manager.BackgroundMusicManager;

public interface MusicManagerAware {
    void setMusicManager(BackgroundMusicManager musicManager);
}
