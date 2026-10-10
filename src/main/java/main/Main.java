package main;

import main.controller.GameController;
import main.dao.*;
import main.view.GameView;

public class Main {

    public static void main(String[] args) {

        GameController gameController = new GameController(
                new ChapterDaoJson(),
                new SaveDaoJson(),
                new ItemDaoJson(),
                new AchievementDaoJson(),
                new UserProfileDaoJson()
        );

        GameView gameView = new GameView(gameController);

        gameView.start();
    }
}