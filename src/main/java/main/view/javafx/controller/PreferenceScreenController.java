package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import main.view.javafx.manager.ScreenManager;

public class PreferenceScreenController implements ScreenController {

    private ScreenManager screenManager;

    @FXML
    private ToggleButton autosaveButton;

    @FXML
    private Button backButton;

    @FXML
    private ToggleButton commandMenuButton;

    @FXML
    private ToggleButton playerStatusButton;

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @FXML
    void handleBackToMainMenu(ActionEvent event) {
        screenManager.showMainMenu();
    }

    @FXML
    void handleToggleAutosave(ActionEvent event) {

    }

    @FXML
    void handleToggleShowCommandMenu(ActionEvent event) {

    }

    @FXML
    void handleToggleShowPLayerStatus(ActionEvent event) {

    }
}
