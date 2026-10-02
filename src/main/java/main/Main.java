package main;

import main.controller.GameController;
import main.dao.ChapterDaoJson;
import main.dao.ItemDaoJson;
import main.dao.SaveGameDaoJson;
import main.view.GameView;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController(
                new ChapterDaoJson(), new SaveGameDaoJson(), new ItemDaoJson());
        GameView gameView = new GameView(gameController);
        gameView.start();
    }
}