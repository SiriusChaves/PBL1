package main.view.javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import main.view.javafx.manager.ScreenManager;

public class MegaBrainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ScreenManager screenManager = new ScreenManager(primaryStage);
        screenManager.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
