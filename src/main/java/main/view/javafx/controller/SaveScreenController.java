package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.controller.GameController;
import main.dto.SaveViewDto;
import main.view.javafx.controller.dependency.GameControllerAware;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.controller.dependency.ScreenInitializer;
import main.view.javafx.manager.ScreenManager;

import java.util.List;
import java.util.Optional;

/* Lembrar de usar setVisible() e setManeged() para controlar, respectivamente,
* visibilidade dos elementos e se eles serão usados no calculo de espaço do layout
* */
public class SaveScreenController implements ScreenController, GameControllerAware, ScreenInitializer {

    private ScreenManager screenManager;

    private GameController gameController;

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
    private HBox saveActionsSlot1;

    @FXML
    private HBox saveActionsSlot2;

    @FXML
    private HBox saveActionsSlot3;

    @FXML
    private VBox saveDataContentSlot1;

    @FXML
    private VBox saveDataContentSlot2;

    @FXML
    private VBox saveDataContentSlot3;

    @FXML
    private Button slot1Button;

    @FXML
    private Button slot2Button;

    @FXML
    private Button slot3Button;

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @FXML
    void handleBackToMenu(ActionEvent event) {
        screenManager.showMainMenu();
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
        if (confirmDelete(1)) {
            gameController.deleteGameSession(String.valueOf(1));
        }

        updateAllSlots();
    }

    @FXML
    void handleDeleteSaveSlot2(ActionEvent event) {
        if (confirmDelete(2)) {
            gameController.deleteGameSession(String.valueOf(2));
        }

        updateAllSlots();
    }

    @FXML
    void handleDeleteSaveSlot3(ActionEvent event) {
        if (confirmDelete(3)) {
            gameController.deleteGameSession(String.valueOf(3));
        }

        updateAllSlots();
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

    private boolean confirmDelete(int slotNumber) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(
                "Deletar Partida"
        );

        alert.setHeaderText(
                "Deseja realmente excluir o Slot " + slotNumber + " ?"
        );

        alert.setContentText(
                "Todo o progresso será perdido."
        );

        ButtonType deleteButton = new ButtonType(
                "Excluir", ButtonBar.ButtonData.OK_DONE
        );

        ButtonType cancelButton = new ButtonType(
                "Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE
        );

        alert.getButtonTypes().setAll(deleteButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == deleteButton;
    }

    private void updateSlot1(SaveViewDto saveData) {
        boolean hasData = saveData != null;

        if (!hasData) {
            saveDataContentSlot1.setVisible(false);
            saveDataContentSlot1.setManaged(false);

            emptyContentSlot1.setVisible(true);
            emptyContentSlot1.setManaged(true);

            saveActionsSlot1.setVisible(false);
            saveActionsSlot1.setManaged(false);

        } else {

            emptyContentSlot1.setVisible(false);
            emptyContentSlot1.setManaged(false);

            saveDataContentSlot1.setVisible(true);
            saveDataContentSlot1.setManaged(true);

            personNameSlot1Label.setText(saveData.playerName());
            datetimeSlot1Label.setText(saveData.dateSave());
            chapterNameSlot1Label.setText(saveData.currentChapterName());

            saveActionsSlot1.setVisible(true);
            saveActionsSlot1.setManaged(true);
        }
    }

    private void updateSlot2(SaveViewDto saveData) {
        boolean hasData = saveData != null;

        if (!hasData) {
            saveDataContentSlot2.setVisible(false);
            saveDataContentSlot2.setManaged(false);

            emptyContentSlot2.setVisible(true);
            emptyContentSlot2.setManaged(true);

            saveActionsSlot2.setVisible(false);
            saveActionsSlot2.setManaged(false);

        } else {

            emptyContentSlot2.setVisible(false);
            emptyContentSlot2.setManaged(false);

            saveDataContentSlot2.setVisible(true);
            saveDataContentSlot2.setManaged(true);

            personNameSlot2Label.setText(saveData.playerName());
            datetimeSlot2Label.setText(saveData.dateSave());
            chapterNameSlot2Label.setText(saveData.currentChapterName());

            saveActionsSlot2.setVisible(true);
            saveActionsSlot2.setManaged(true);
        }
    }

    private void updateSlot3(SaveViewDto saveData) {
        boolean hasData = saveData != null;

        if (!hasData) {

            saveDataContentSlot3.setVisible(false);
            saveDataContentSlot3.setManaged(false);

            emptyContentSlot3.setVisible(true);
            emptyContentSlot3.setManaged(true);

            saveActionsSlot3.setVisible(false);
            saveActionsSlot3.setManaged(false);

        } else {

            emptyContentSlot3.setVisible(false);
            emptyContentSlot3.setManaged(false);

            saveDataContentSlot3.setVisible(true);
            saveDataContentSlot3.setManaged(true);

            personNameSlot3Label.setText(saveData.playerName());
            datetimeSlot3Label.setText(saveData.dateSave());
            chapterNameSlot3Label.setText(saveData.currentChapterName());

            saveActionsSlot3.setVisible(true);
            saveActionsSlot3.setManaged(true);
        }
    }

    public void updateAllSlots() {

        List<SaveViewDto> saves = gameController.getDataOfAllSaves();

        updateSlot1(saves.getFirst());
        updateSlot2(saves.get(1));
        updateSlot3(saves.getLast());
    }

    @Override
    public void initScreen() {
        updateAllSlots();
    }
}
