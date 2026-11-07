package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.controller.GameController;
import main.view.javafx.controller.dependency.GameControllerAware;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.manager.ScreenManager;

public class MainMenuController implements ScreenController, GameControllerAware {

    private ScreenManager screenManager;

    private GameController gameController;

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

    @Override
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
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
