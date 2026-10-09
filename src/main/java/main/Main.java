package main;

import main.controller.GameController;
import main.dao.AchievementDaoJson;
import main.dao.ChapterDaoJson;
import main.dao.ItemDaoJson;
import main.dao.SaveDaoJson;
import main.view.GameView;

public class Main {

    public static void main(String[] args) {

        GameController gameController = new GameController(
                new ChapterDaoJson(),
                new SaveDaoJson(),
                new ItemDaoJson(),
                new AchievementDaoJson()
        );

        GameView gameView = new GameView(gameController);

        gameView.start();
    }
}