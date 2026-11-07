package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.view.javafx.manager.ScreenManager;

public class MainMenuController implements ScreenController {

    private ScreenManager screenManager;

    @FXML
    private Button achievementsButton;

    @FXML
    private Button creditsButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button instructionsButton;

    @FXML
    private Button preferencesButton;

    @FXML
    private Button startGameButton;

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @FXML
    void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleShowAchievements(ActionEvent event) {

    }

    @FXML
    void handleShowCredits(ActionEvent event) {

    }

    @FXML
    void handleShowInstructions(ActionEvent event) {

    }

    @FXML
    void handleShowPreferences(ActionEvent event) {
        screenManager.showPreferences();
    }

    @FXML
    void handleStartGame(ActionEvent event) {
        screenManager.showSaveScreen();
    }

}
