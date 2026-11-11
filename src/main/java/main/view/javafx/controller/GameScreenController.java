package main.view.javafx.controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import main.controller.GameController;
import main.dto.ChapterDto;
import main.dto.DialogueDto;
import main.dto.SceneDto;
import main.exception.ChapterNotFoundException;
import main.view.javafx.controller.dependency.GameControllerAware;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.controller.dependency.ScreenInitializer;
import main.view.javafx.controller.dependency.SlotAware;
import main.view.javafx.manager.ScreenManager;

import java.util.List;
import java.util.Optional;


public class GameScreenController implements
        ScreenController,
        GameControllerAware,
        ScreenInitializer,
        SlotAware {

    private ScreenManager screenManager;

    private GameController gameController;

    private String slotNumber;

    private boolean showingFinalChoices = false;

    private List<Button> choiceButtons;

    private ChapterDto actualChapterInScreen;

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

    @FXML
    private Button continueButton;

    @FXML
    private ScrollPane choicesScrollPane;

    @FXML
    private StackPane containerPauseMenu;

    @FXML
    private void initialize() {

        choiceButtons = List.of(
                choice1Button,
                choice2Button,
                choice3Button
        );

        for (int i = 0; i < choiceButtons.size(); i++) {

            final int choiceIndex = i;

            choiceButtons.get(i).setOnAction(
                    event -> handleChoice(choiceIndex)
            );
        }

        displayChoices(List.of());

        continueButton.setVisible(false);
        continueButton.setManaged(false);

        choicesScrollPane.setVisible(false);
        choicesScrollPane.setManaged(false);

        containerPauseMenu.setVisible(false);
    }

    @FXML
    void handleResume(ActionEvent event) {
        containerPauseMenu.setVisible(false);
    }

    @FXML
    void handleSaveGame(ActionEvent event) {
        gameController.saveGame();
    }

    @FXML
    void handleBackToMainMenu(ActionEvent event) {

        if (confirmLeaveToMainMenu()) {

            screenManager.showMainMenu();
        }
    }

    @FXML
    private void handleContinue() {

        if (gameController.hasNextScene()) {

            gameController.advanceToNextScene();

            displayCurrentScene();

            return;
        }

        if (actualChapterInScreen.numberLastChoices() > 0) {

            displayFinalChoices(actualChapterInScreen);

            return;
        }

        finishChapter();
    }

    private void registerKeyBoardsEvents() {

        Scene scene = containerPauseMenu.getScene();

        scene.addEventFilter(
                KeyEvent.KEY_PRESSED,
                event -> {

                    if (event.getCode() == KeyCode.P) {

                        togglePause();

                        event.consume();
                    }
                }
        );
    }

    private boolean confirmLeaveToMainMenu() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Voltar ao Menu Principal");

        alert.setHeaderText(
                "ATENÇÃO: certifique-se de salvar antes de sair."
        );

        alert.setContentText("Você pode perder seu progresso.");

        ButtonType confirmButton = new ButtonType(
                "sair", ButtonBar.ButtonData.OK_DONE
        );

        ButtonType cancelButton = new ButtonType(
                "cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == confirmButton;
    }

    private void togglePause() {

        boolean gameIsPaused = containerPauseMenu.isVisible();

        containerPauseMenu.setVisible(!gameIsPaused);
    }

    private void displayCurrentChapter() {

        try {
            ChapterDto currentChapter = gameController.loadCurrentChapter();

            this.actualChapterInScreen = currentChapter;

            chapterActual.setText(currentChapter.title());

            displayCurrentScene();

        } catch (ChapterNotFoundException chapterNotFoundException) {

            System.out.println(chapterNotFoundException.getMessage());
        }
    }

    private void displayCurrentScene() {

        showingFinalChoices = false;

        SceneDto currentScene = gameController.loadCurrentScene();

        StringBuilder text = new StringBuilder();

        for (DialogueDto dialogue : currentScene.dialogues()) {

            text.append(dialogue.speaker().toUpperCase())
                    .append(": ")
                    .append(dialogue.text())
                    .append("\n\n");
        }

        storyTextLabel.setText(text.toString());


        displayChoices(currentScene.choices());
    }

    private void displayChoices(List<String> choices) {

        if (choices.size() > choiceButtons.size()) {
            throw new IllegalArgumentException(
                    "A interface suporta no máximo 3 escolhas."
            );
        }

        boolean hasChoice = !choices.isEmpty();

        choicesScrollPane.setVisible(hasChoice);
        choicesScrollPane.setManaged(hasChoice);

        continueButton.setVisible(!hasChoice);
        continueButton.setManaged(!hasChoice);

        for (int i = 0; i < choiceButtons.size(); i++) {

            Button button = choiceButtons.get(i);

            boolean available = i < choices.size();

            button.setVisible(available);
            button.setManaged(available);

            if (available) {
                button.setText(choices.get(i));
            }
        }
    }

    private void handleSceneChoice(int choiceIndex) {

        gameController.applySceneChoiceConsequence(choiceIndex);

        if (gameController.isGameOver()) {
            screenManager.showGameOverScreen();
            return;
        }

        if (gameController.hasNextScene()) {
            gameController.advanceToNextScene();

            displayCurrentScene();

        } else {

            if (this.actualChapterInScreen.numberLastChoices() > 0) {

                displayFinalChoices(actualChapterInScreen);

            } else {

                finishChapter();
            }
        }
    }

    private void handleFinalChoice(int choiceIndex) {
        gameController.applyChapterFinalChoiceConsequence(choiceIndex);

        if (gameController.isGameOver()) {
            screenManager.showGameOverScreen();
            return;
        }

        finishChapter();
    }

    private void finishChapter() {

        if (gameController.isFinalChapter()) {
            gameController.setGameIsCompleted();
            gameController.saveGame();
            System.out.println("Mostrar tela de finalização");
            return;
        }

        displayCurrentChapter();
    }

    private void handleChoice(int choiceIndex) {

        if (showingFinalChoices) {

            handleFinalChoice(choiceIndex);

        } else {

            handleSceneChoice(choiceIndex);
        }
    }


    private void displayFinalChoices(ChapterDto chapter) {
        showingFinalChoices = true;

        displayChoices(chapter.choices());
    }

    private void openSlot() {
        if (gameController.hasSaveDataAndSaveExists(this.slotNumber)) {

            gameController.loadGame(this.slotNumber);

        } else {

            gameController.prepareSaveSlot(this.slotNumber, false);
            gameController.startNewGame(this.slotNumber);
        }
    }

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void initScreen() {
        registerKeyBoardsEvents();

        openSlot();

        displayCurrentChapter();
    }

    @Override
    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }
}
