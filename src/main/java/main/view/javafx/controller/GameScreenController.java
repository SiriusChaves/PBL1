package main.view.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.view.javafx.manager.ScreenManager;


public class GameScreenController implements ScreenController {

    private ScreenManager screenManager;

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
}
