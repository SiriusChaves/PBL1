package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import main.controller.GameController;
import main.model.profile.Preference;
import main.model.profile.UserProfile;
import main.view.javafx.controller.dependency.GameControllerAware;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.controller.dependency.ScreenInitializer;
import main.view.javafx.manager.ScreenManager;

import java.util.EnumSet;

public class PreferenceScreenController implements ScreenController, GameControllerAware, ScreenInitializer {

    private ScreenManager screenManager;

    private GameController gameController;

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

    @Override
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @FXML
    void handleBackToMainMenu(ActionEvent event) {
        screenManager.showMainMenu();
    }

    @FXML
    void handleToggleAutosave(ActionEvent event) {
        gameController.togglePreference(Preference.AUTOSAVE);
        initScreen();
    }

    @FXML
    void handleToggleShowCommandMenu(ActionEvent event) {
        gameController.togglePreference(Preference.SHOW_COMMAND_HUD);
        initScreen();
    }

    @FXML
    void handleToggleShowPLayerStatus(ActionEvent event) {
        gameController.togglePreference(Preference.SHOW_PLAYER_HUD);
        initScreen();
    }

    @Override
    public void initScreen() {
        UserProfile userProfile = gameController.loadUserProfile();

        EnumSet<Preference> preferences = userProfile.getPreferences();

        if (preferences.contains(Preference.AUTOSAVE)) {
            autosaveButton.setSelected(true);
            autosaveButton.setText("ATIVADO");
        } else {
            autosaveButton.setText("DESATIVADO");
            autosaveButton.setSelected(false);
        }

        if (preferences.contains(Preference.SHOW_COMMAND_HUD)) {
            commandMenuButton.setSelected(true);
            commandMenuButton.setText("ATIVADO");
        } else {
            commandMenuButton.setText("DESATIVADO");
            commandMenuButton.setSelected(false);
        }

        if (preferences.contains(Preference.SHOW_PLAYER_HUD)) {
            playerStatusButton.setSelected(true);
            playerStatusButton.setText("ATIVADO");
        } else {
            playerStatusButton.setText("DESATIVADO");
            playerStatusButton.setSelected(false);
        }
    }
}
