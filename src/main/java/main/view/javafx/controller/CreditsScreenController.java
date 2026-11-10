package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.manager.ScreenManager;

public class CreditsScreenController implements ScreenController {

    @FXML
    void handleBackToMainMenu(ActionEvent event) {
        screenManager.showMainMenu();
    }

    private ScreenManager screenManager;

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
}
