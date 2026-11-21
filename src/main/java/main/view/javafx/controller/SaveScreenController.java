package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.controller.GameController;
import main.dto.SaveViewDto;
import main.model.profile.UserProfile;
import main.view.javafx.controller.dependency.GameControllerAware;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.controller.dependency.ScreenInitializer;
import main.view.javafx.manager.ScreenManager;

import java.util.List;
import java.util.Optional;

public class SaveScreenController implements
        ScreenController,
        GameControllerAware,
        ScreenInitializer {

    private record SlotView(
            VBox emptyContent,
            VBox saveDataContent,
            HBox actions,
            Label personName,
            Label dateTime,
            Label chapterName
    ) {}

    private List<SlotView> slotViews;

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
        screenManager.showGameScreen("1");
    }

    @FXML
    void handleClickedOnSlot2(ActionEvent event) {
        screenManager.showGameScreen("2");
    }

    @FXML
    void handleClickedOnSlot3(ActionEvent event) {
        screenManager.showGameScreen("3");
    }

    @FXML
    void handleDeleteSaveSlot1(ActionEvent event) {
        deleteSaveSlot(1);
    }

    @FXML
    void handleDeleteSaveSlot2(ActionEvent event) {
        deleteSaveSlot(2);
    }

    @FXML
    void handleDeleteSaveSlot3(ActionEvent event) {
        deleteSaveSlot(3);
    }

    private void deleteSaveSlot(int slotNumber) {

        if (confirmDelete(slotNumber)) {

            String slotNumberStr = String.valueOf(slotNumber);

            UserProfile userProfile = gameController.loadUserProfile();

            if (slotNumberStr.equals(userProfile.getLastPlayedSlotId())) {
                gameController.updateLastPlayedSlot("-1");
            }

            gameController.deleteGameSession(slotNumberStr);

            updateAllSlots();
        }
    }

    private void initializeSlotViews() {

        slotViews = List.of(
                new SlotView(
                        emptyContentSlot1,
                        saveDataContentSlot1,
                        saveActionsSlot1,
                        personNameSlot1Label,
                        datetimeSlot1Label,
                        chapterNameSlot1Label
                ),

                new SlotView(
                        emptyContentSlot2,
                        saveDataContentSlot2,
                        saveActionsSlot2,
                        personNameSlot2Label,
                        datetimeSlot2Label,
                        chapterNameSlot2Label
                ),

                new SlotView(
                        emptyContentSlot3,
                        saveDataContentSlot3,
                        saveActionsSlot3,
                        personNameSlot3Label,
                        datetimeSlot3Label,
                        chapterNameSlot3Label
                )
        );
    }

    private void setNodeState(Node node, boolean active) {
        node.setVisible(active);
        node.setManaged(active);
    }

    private void updateSlot(SlotView slot, SaveViewDto saveData) {

        boolean hasData = saveData != null;

        setNodeState(slot.emptyContent(), !hasData);
        setNodeState(slot.saveDataContent(), hasData);
        setNodeState(slot.actions(), hasData);

        if (!hasData) {
            return;
        }

        slot.personName.setText(saveData.playerName());
        slot.dateTime().setText(saveData.dateSave());
        slot.chapterName.setText(saveData.currentChapterName());
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

    public void updateAllSlots() {

        List<SaveViewDto> saves = gameController.getDataOfAllSaves();

        if (saves.size() != slotViews.size()) {

            throw new IllegalStateException(
                    "A quantidade de saves não corresponde a quantidade de slots."
            );
        }

        for (int i = 0; i < slotViews.size(); i++) {

            updateSlot(slotViews.get(i), saves.get(i));
        }
    }

    @Override
    public void initScreen() {
        initializeSlotViews();
        updateAllSlots();
    }
}

