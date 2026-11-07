package main.view.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.controller.GameController;
import main.view.javafx.controller.dependency.GameControllerAware;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.manager.ScreenManager;


public class GameScreenController implements ScreenController, GameControllerAware {

    private ScreenManager screenManager;

    private GameController gameController;

    @FXML
    private Label chapterActual;

    @FXML
    private Button choice1Button;

    @FXML
    private Button choice2Button;

    @FXML
    private Button choice3Button;

    @FXML
    private ImageView imgCharacterA;

    @FXML
    private ImageView imgCharacterB;

    @FXML
    private Label nameCharacterA;

    @FXML
    private Label nameCharacterB;

    @FXML
    private Label storyTextLabel;

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
