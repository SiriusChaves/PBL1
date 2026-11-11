package main.view.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.manager.ScreenManager;

public class GameOverScreenController implements ScreenController {

    private ScreenManager screenManager;

    @FXML
    void handleBackToMainMenu(KeyEvent event) {

        if (event.getCode() == KeyCode.ESCAPE) {

            screenManager.showMainMenu();
        }
    }

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
}
