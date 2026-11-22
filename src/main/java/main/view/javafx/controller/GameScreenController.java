package main.view.javafx.controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ProgressBar;
import main.controller.GameController;
import main.dto.*;
import main.exception.ChapterNotFoundException;
import main.facade.GameFacade;
import main.facade.GameFlowResult;
import main.observer.GameEvent;
import main.observer.GameEventType;
import main.observer.GameObserver;
import main.view.javafx.controller.dependency.*;
import main.view.javafx.manager.ScreenManager;
import main.view.javafx.util.CharacterSpriteResolver;

import java.util.List;
import java.util.Optional;



public class GameScreenController implements
        ScreenController,
        GameControllerAware,
        GameFacadeAware,
        ScreenInitializer,
        SlotAware,
        GameObserver,
        ScreenDisposable {

    private ScreenManager screenManager;

    private GameController gameController;

    private GameFacade gameFacade;

    private final CharacterSpriteResolver spriteResolver =
            new CharacterSpriteResolver();

    private boolean observerRegistered = false;

    private boolean disposed = false;

    private String slotNumber;

    private boolean showingFinalChoices = false;

    private List<Button> choiceButtons;

    private ChapterDto actualChapterInScreen;

    private static final int MAX_SANITY = 100;

    private static final int MAX_KNOWLEDGE = 100;

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
    private VBox statusContainer;

    @FXML
    private StackPane containerPlayerPanel;

    @FXML
    private HBox hudActions;

    @FXML
    private VBox inventoryItemsContainer;

    @FXML
    private ScrollPane inventoryPanel;

    @FXML
    private StackPane playerPanelContent;

    @FXML
    private Label playerPanelTitle;

    @FXML
    private ScrollPane relationshipPanel;

    @FXML
    private VBox relationshipsContainer;

    @FXML
    private ScrollPane statusPanel;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label sanityValueLabel;

    @FXML
    private Label knowledgeValueLabel;

    @FXML
    private ProgressBar sanityProgressBar;

    @FXML
    private ProgressBar knowledgeProgressBar;

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

        hideAllPlayerPanel();

        containerPlayerPanel.setVisible(false);
    }

    @FXML
    void closePlayerPanel(ActionEvent event) {
        containerPlayerPanel.setVisible(false);
    }

    @FXML
    void handleShowInventory(ActionEvent event) {

        displayInventory();

        showPlayerPanel(inventoryPanel, "Inventário");
    }

    @FXML
    void handleShowRelationships(ActionEvent event) {

        displayRelationships();

        showPlayerPanel(relationshipPanel, "Vínculos");
    }

    @FXML
    void handleShowStatus(ActionEvent event) {

        displayPlayerStatus();

        showPlayerPanel(statusPanel, "Status do Jogador");
    }

    @FXML
    void handlePause(ActionEvent event) {
        togglePause();
    }

    @FXML
    void handleResume(ActionEvent event) {
        containerPauseMenu.setVisible(false);
    }

    @FXML
    void handleSaveGame(ActionEvent event) {

        if (!confirmSaveOverwrite()) {
            return;
        }

        try {

            gameFacade.saveCurrentGame();

            showSaveSuccessAlert();

        } catch (RuntimeException exception) {
            showSaveErrorAlert(exception.getMessage());
        }
    }

    @FXML
    void handleBackToMainMenu(ActionEvent event) {

        if (confirmLeaveToMainMenu()) {

            screenManager.showMainMenu();
        }
    }

    @FXML
    private void handleContinue() {

        GameFlowResult result = gameFacade.continueNarrative();

        handleGameFlowResult(result);
    }

    private boolean confirmSaveOverwrite() {

        if (!gameController.hasSaveDataAndSaveExists(slotNumber)) {
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Sobrescrever partida");

        alert.setHeaderText("Deseja substituir o save atual?");

        alert.setContentText(
                "O progresso anteriormente salvo será substituído pelo atual."
        );

        ButtonType saveButton = new ButtonType(
                "Salvar", ButtonBar.ButtonData.OK_DONE
        );

        ButtonType cancelButton = new ButtonType(
                "Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE
        );

        alert.getButtonTypes().setAll(saveButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == saveButton;
    }

    private void showSaveErrorAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Erro no gerenciamento de saves");
        alert.setHeaderText("Não foi possível concluir a operação.");
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void handleGameFlowResult(GameFlowResult result) {

        switch (result) {

            case  SCENE_UPDATED -> displayCurrentScene();

            case FINAL_CHOICES_AVAILABLE ->
                displayFinalChoices(actualChapterInScreen);

            case CHAPTER_FINISHED -> displayCurrentChapter();

            case GAME_COMPLETED -> screenManager.showEndingScreen();

            case GAME_OVER -> screenManager.showGameOverScreen();
        }
    }

    private void updateProgressBar(
            ProgressBar progressBar,
            Label valueLabel,
            int currentValue,
            int maxValue) {

        double progress = (double) currentValue / maxValue;

        progress = Math.max(0, Math.min(1, progress));

        progressBar.setProgress(progress);

        valueLabel.setText(currentValue + " / " + maxValue);
    }

    private void displayPlayerStatus() {

        PlayerDto player = gameController.loadPlayerData();

        playerNameLabel.setText(player.name());

        updateProgressBar(
                sanityProgressBar,
                sanityValueLabel,
                player.sanity(),
                MAX_SANITY
        );

        updateProgressBar(
                knowledgeProgressBar,
                knowledgeValueLabel,
                player.knowledge(),
                MAX_KNOWLEDGE
        );
    }

    private void displayInventory() {

        List<ItemViewDto> items = gameController.loadInventoryData();

        inventoryItemsContainer.getChildren().clear();

        Label title = new Label("Itens do Inventário");

        title.getStyleClass().add("game-dynamic-list-heading");

        inventoryItemsContainer.getChildren().add(title);

        if (items == null || items.isEmpty()) {

            Label emptyMessage = new Label("Nenhum item obtido.");

            emptyMessage.getStyleClass().add("game-dynamic-empty-message");

            inventoryItemsContainer.getChildren().add(emptyMessage);

            return;
        }

        for (ItemViewDto item : items) {

                VBox itemCard = createItemCard(item);

                inventoryItemsContainer.getChildren().add(itemCard);
        }
    }

    private VBox createItemCard(ItemViewDto item) {

        VBox card = new VBox();

        card.setMaxWidth(Double.MAX_VALUE);

        card.getStyleClass().add("game-dynamic-card");


        Label itemName = new Label(item.name());

        itemName.setWrapText(true);

        itemName.getStyleClass().add("game-dynamic-card-title");


        Label itemDescription = new Label(item.description());

        itemDescription.setWrapText(true);
        itemDescription.setMaxWidth(Double.MAX_VALUE);

        itemDescription.getStyleClass().add("game-dynamic-card-description");


        card.getChildren().addAll(
                itemName,
                itemDescription
        );

        return card;
    }

    private void displayRelationships() {

        List<NpcDto> relationships =
                gameController.loadRelationshipData();

        relationshipsContainer.getChildren().clear();

        Label title = new Label("Vínculos com os personagens");

        title.getStyleClass().add("game-dynamic-list-heading");

        relationshipsContainer.getChildren().add(title);

        if (relationships == null || relationships.isEmpty()) {

            Label emptyMessage = new Label("Nenhum vínculo registrado.");

            emptyMessage.getStyleClass().add("game-dynamic-empty-message");

            relationshipsContainer.getChildren().add(emptyMessage);

            return;
        }

        for (NpcDto npc : relationships) {

            VBox relationshipCard = createRelationshipCard(npc);

            relationshipsContainer.getChildren().add(relationshipCard);
        }
    }

    private VBox createRelationshipCard(NpcDto npc) {

        VBox card = new VBox();

        card.setMaxWidth(Double.MAX_VALUE);

        card.getStyleClass().add("game-dynamic-card");


        Label npcName = new Label(npc.name());

        npcName.setWrapText(true);

        npcName.getStyleClass().add("game-dynamic-card-title");


        Label trustTier = new Label(
                "Nível do vínculo: " + npc.trustLevelTier()
        );

        trustTier.getStyleClass().add(
                "game-dynamic-card-description"
        );


        Label trustValue = new Label(
                "Confiança: " + npc.trustLevel()
        );

        trustValue.getStyleClass().add(
                "game-dynamic-card-description"
        );


        card.getChildren().addAll(
                npcName,
                trustTier,
                trustValue
        );

        return card;
    }

    private void showSaveSuccessAlert() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Salvar jogo");
        alert.setHeaderText("Jogo salvo!");
        alert.setContentText("Seu progresso foi salvo com sucesso.");

        alert.showAndWait();
    }

    private void hideAllPlayerPanel() {

        statusPanel.setVisible(false);
        statusPanel.setManaged(false);

        relationshipPanel.setVisible(false);
        relationshipPanel.setManaged(false);

        inventoryPanel.setVisible(false);
        inventoryPanel.setManaged(false);
    }

    private void showPlayerPanel(ScrollPane playerPane, String title) {

        hideAllPlayerPanel();

        playerPane.setManaged(true);
        playerPane.setVisible(true);

        playerPanelTitle.setText(title);

        containerPlayerPanel.setVisible(true);
    }

    private void registerKeyboardsEvents() {

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

    private void handleChoice(int choiceIndex) {

        GameFlowResult result;

        if (showingFinalChoices) {
            result = gameFacade.chooseFinalOption(choiceIndex);
        } else {
            result = gameFacade.chooseSceneOption(choiceIndex);
        }

        handleGameFlowResult(result);
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

    private void updateCharacterSprite(SceneDto scene) {

        PlayerDto player = gameController.loadPlayerData();

        String protagonistName = player.name();



        CharacterSpriteResolver.ResolvedCharacter protagonist =
                spriteResolver.resolveProtagonist(protagonistName);

        CharacterSpriteResolver.ResolvedCharacter companion =
                spriteResolver.resolveSceneCharacter(
                        scene,
                        protagonistName
                );

        imgCharacterA.setImage(protagonist.image());
        nameCharacterA.setText(protagonist.name());

        imgCharacterB.setImage(companion.image());
        nameCharacterB.setText(companion.name());
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

        disposed = false;

        if (!observerRegistered) {
            gameController.addObserver(this);

            observerRegistered = true;
        }

        registerKeyboardsEvents();

        openSlot();

        displayCurrentChapter();
    }

    @Override
    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    @Override
    public void setGameFacade(GameFacade gameFacade) {
        this.gameFacade = gameFacade;
    }

    @Override
    public void onGameEvent(GameEvent event) {

        if (event.type() != GameEventType.SCENE_CHANGED) {
            return;
        }

        if (!(event.payload() instanceof SceneDto scene)) {
            return;
        }

        Runnable updateTask = () -> {

            if (!disposed) {
                updateCharacterSprite(scene);
            }
        };

        if (Platform.isFxApplicationThread()) {

            updateTask.run();

        } else {

            Platform.runLater(updateTask);
        }
    }

    @Override
    public void dispose() {
        disposed = true;

        if (observerRegistered) {

            gameController.removeObserver(this);

            observerRegistered = false;
        }
    }
}
