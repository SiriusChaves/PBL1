package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.view.javafx.manager.SceneManager;

/* Lembrar de usar setVisible() e setManeged() para controlar, respectivamente,
* visibilidade dos elementos e se eles serão usados no calculo de espaço do layout
* */
public class SaveScreenController implements SceneController {

    @FXML
    private Button backToMenuButton;

    @FXML
    private Label chapterNameSlot1Label;

    @FXML
    private Label chapterNameSlot2Label;

    @FXML
    private Label chapterNameSlot3Label;

    @FXML
    private Label datetimeSlot1Label;

    @FXML
    private Label datetimeSlot2Label;

    @FXML
    private Label datetimeSlot3Label;

    @FXML
    private Button deleteSaveSlot1Button;

    @FXML
    private Button deleteSaveSlot2Button;

    @FXML
    private Button deleteSaveSlot3Button;

    @FXML
    private VBox emptyContentSlot1;

    @FXML
    private VBox emptyContentSlot2;

    @FXML
    private VBox emptyContentSlot3;

    @FXML
    private ImageView imgSlot1;

    @FXML
    private ImageView imgSlot2;

    @FXML
    private ImageView imgSlot3;

    @FXML
    private VBox occupiedContentSlot1;

    @FXML
    private VBox occupiedContentSlot2;

    @FXML
    private VBox occupiedContentSlot3;

    @FXML
    private Label personNameSlot1Label;

    @FXML
    private Label personNameSlot2Label;

    @FXML
    private Label personNameSlot3Label;

    @FXML
    private Button restartSaveSlot1Button;

    @FXML
    private Button restartSaveSlot2Button;

    @FXML
    private Button restartSaveSlot3Button;

    @FXML
    private Button slot1Button;

    @FXML
    private Button slot2Button;

    @FXML
    private Button slot3Button;

    private SceneManager sceneManager;

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    void handleBackToMenu(ActionEvent event) {

    }

    @FXML
    void handleClickedOnSlot1(ActionEvent event) {

    }

    @FXML
    void handleClickedOnSlot2(ActionEvent event) {

    }

    @FXML
    void handleClickedOnSlot3(ActionEvent event) {

    }

    @FXML
    void handleDeleteSaveSlot1(ActionEvent event) {

    }

    @FXML
    void handleDeleteSaveSlot2(ActionEvent event) {

    }

    @FXML
    void handleDeleteSaveSlot3(ActionEvent event) {

    }

    @FXML
    void handleRestartSaveSlot1(ActionEvent event) {

    }

    @FXML
    void handleRestartSaveSlot2(ActionEvent event) {

    }

    @FXML
    void handleRestartSaveSlot3(ActionEvent event) {

    }


}
