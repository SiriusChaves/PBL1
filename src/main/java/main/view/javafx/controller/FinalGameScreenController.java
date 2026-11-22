package main.view.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.controller.dependency.ScreenInitializer;
import main.view.javafx.manager.ScreenManager;

public class FinalGameScreenController implements ScreenController, ScreenInitializer {

    private ScreenManager screenManager;

    @FXML
    private StackPane rootPane;

    private void registerKeyboardsEvents() {

        Scene scene = rootPane.getScene();

        scene.addEventFilter( KeyEvent.KEY_PRESSED, event -> {

            if (event.getCode() == KeyCode.SPACE) {

                event.consume();

                screenManager.showMainMenu();
            }
        });
    }

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void initScreen() {
        registerKeyboardsEvents();
    }
}
