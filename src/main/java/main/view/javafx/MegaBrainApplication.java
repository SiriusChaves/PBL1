package main.view.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.view.javafx.manager.SceneManager;

import java.util.Objects;

public class MegaBrainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
