package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.controller.GameController;
import main.model.profile.UserProfile;
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
        screenManager.showAchievementScreen();
    }

    @FXML
    void handleShowCredits(ActionEvent event) {
        screenManager.showCreditsScreen();
    }

    @FXML
    void handleShowInstructions(ActionEvent event) {
        screenManager.showInstructionsScreen();
    }

    @FXML
    void handleShowPreferences(ActionEvent event) {
        screenManager.showPreferences();
    }

    @FXML
    void handleStartGame(ActionEvent event) {
        screenManager.showSaveScreen();
    }

    @FXML
    void handleContinueLastGame(ActionEvent event) {

        String lastPlayedSlotId = gameController.loadUserProfile().getLastPlayedSlotId();

        if (lastPlayedSlotId.equals("-1")) {
            showNoGameToContinueAlert();
            return;
        }

        screenManager.showGameScreen(lastPlayedSlotId);
    }

    private void showNoGameToContinueAlert() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Nenhuma partida encontrada");
        alert.setHeaderText("Não há jogo para continuar!");
        alert.setContentText(
                "Você ainda não possui uma partida salva neste slot.\n"
                        + "Inicie um novo jogo para começar sua jornada."
        );

        alert.showAndWait();
    }

}
