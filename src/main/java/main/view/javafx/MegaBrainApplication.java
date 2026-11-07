package main.view.javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import main.controller.GameController;
import main.persistence.json.*;
import main.view.javafx.manager.ScreenManager;

public class MegaBrainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        GameController gameController = new GameController(
                new ChapterDaoJson(),
                new SaveDaoJson(),
                new ItemDaoJson(),
                new AchievementDaoJson(),
                new UserProfileDaoJson()
        );

        ScreenManager screenManager = new ScreenManager(primaryStage, gameController);

        screenManager.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
