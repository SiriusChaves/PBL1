package main;

import main.controller.GameController;
import main.dao.ChapterDaoJson;
import main.view.GameView;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController(new ChapterDaoJson());
        GameView gameView = new GameView(gameController);
        gameView.start();
    }
}