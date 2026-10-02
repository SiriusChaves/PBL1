package main.view;

import main.controller.GameController;

import main.dto.ChapterDto;
import main.dto.SceneDto;
import main.exception.ChapterNotFoundException;
import main.exception.ComandNonExistsException;
import main.view.utils.ConsoleInputReader;

import static main.view.MainMenuOption.*;
import static main.view.SaveMenuOption.*;
import static main.view.TerminalUI.*;

public class GameView {
    private final GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
    }

    public void start() {
        MainMenuOption mainMenuOption;

        do {
            mainMenuOption = showStartMenu();

            switch (mainMenuOption) {
                case START_GAME -> startGame();
                case INSTRUCTIONS -> showInstructions();
                case CREDITS -> showCredits();
                case EXIT -> exitGame();
                default -> showText("Escolha inválida realizada!");
            }
        } while(mainMenuOption != EXIT);
    }

    private void startGame() {
        SaveMenuOption saveMenuOption;

        do {
            showSlotsSaves(gameController.getDataOfAllSaves());
            saveMenuOption = showSaveMenu();

            switch (saveMenuOption) {
                case CONTINUE -> continueLastGame();
                case CREATE_NEW_SAVE -> createNewGame();
                case LOAD_EXISTING_SAVE -> loadExistingGame();
                default -> showText("Opção invalida escolhida");
            }
        } while (saveMenuOption != BACK_TO_MAIN_MENU);
    }

    private void runGame(String slotIndex) {
        ChapterDto currentChapter;
        SceneDto currentScene;
        String playerInput;
        boolean isRunning = true;

        while (isRunning) {
            try {
                currentChapter = gameController.loadCurrentChapter();
            } catch (ChapterNotFoundException chapterNotFoundException) {
               showText(chapterNotFoundException.getMessage());
                return;
            }

            showTitle(currentChapter.title());

            int numberScenes = currentChapter.numberScenes();

            for (int numberActualScene = 0; numberActualScene < numberScenes; numberActualScene++) {
                currentScene = gameController.loadCurrentScene();
                showDialogues(currentScene.dialogues());

                if (currentScene.numberChoices() > 0) {

                    boolean isValidChoiceMade = false;

                    while (!isValidChoiceMade) {
                        showChoices(currentScene.choices());
                        showText(">>> Informe o número da escolha (ou comandos como 'inventario'): ");
                        playerInput = ConsoleInputReader.readString();

                        try {
                            int indexChoice = Integer.parseInt(playerInput);

                            if (indexChoice > 0 && indexChoice <= currentScene.numberChoices()) {
                                gameController.applySceneChoiceConsequence(indexChoice - 1);
                                isValidChoiceMade = true;
                            } else {
                                showText("Opção inválida. Escolha um numero entre 1 e " + currentScene.numberChoices() + ".");
                            }

                        } catch (NumberFormatException numberFormatException) {
                            try {
                                comandsMenu(playerInput);
                            } catch (ComandNonExistsException comandNonExistsException) {
                                showText(comandNonExistsException.getMessage());
                            }
                        }
                    }
                    showText(">>> Pressione ENTER para prosseguir...");
                    ConsoleInputReader.readString();
                }
                gameController.advanceToNextScene();
            }

            if (currentChapter.numberLastChoices() > 0) {
                boolean isValidChoiceMade = false;

                while (!isValidChoiceMade) {
                    showChoices(currentChapter.choices());
                    showText(">>> Informe o número da Escolha Final do Capítulo: ");
                    playerInput = ConsoleInputReader.readString();

                    try {
                        int indexChoice = Integer.parseInt(playerInput);
                        if (indexChoice > 0 && indexChoice <= currentChapter.numberLastChoices()) {
                            gameController.applyChapterFinalChoiceConsequence(indexChoice - 1);
                            isValidChoiceMade = true;
                        } else {
                            showText("Opção inválida. Escolha um número entre 1 e " + currentChapter.numberLastChoices() + ".");
                        }
                    } catch (NumberFormatException numberFormatException) {
                        try {
                            comandsMenu(playerInput);
                        } catch (ComandNonExistsException comandNonExistsException){
                            showText(comandNonExistsException.getMessage());
                        }
                    }
                }
            }

            showText("Continuar jogando? (Digite 1 para encerrar a sessão ou ENTER para prosseguir para o próximo capitulo: ");
            playerInput = ConsoleInputReader.readString();
            if (playerInput.equals("1")) isRunning = false;
        }
    }

    private void comandsMenu(String userInput) throws ComandNonExistsException {
        switch (userInput) {
            case "inventario" ->
                TerminalUI.showInventory(gameController.loadInventoryData());
            case "status" ->
                TerminalUI.showStatusPlayer(gameController.loadPlayerData());
            case "vinculos" ->
                TerminalUI.showStatusRelationships(gameController.loadRelationshipData());
            default ->
                throw new ComandNonExistsException("Comando não existente. Digite um número ou um comando válido.\n" +
                        "Para verificar todos os comandos válidos, volte ao menu inicial e leia as instruções.");
        }
    }

    private void continueLastGame() {

    }

    private void loadExistingGame() {

    }

    private void createNewGame() {

    }
}
