package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import main.view.javafx.manager.SceneManager;

public class PreferenceScreenController implements SceneController{

    private SceneManager sceneManager;

    @FXML
    private ToggleButton autosaveButton;

    @FXML
    private Button backButton;

    @FXML
    private ToggleButton commandMenuButton;

    @FXML
    private ToggleButton playerStatusButton;

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    void handleBackToMainMenu(ActionEvent event) {
        sceneManager.showMainMenu();
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
