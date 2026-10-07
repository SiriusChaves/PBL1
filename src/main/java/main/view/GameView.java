package main.view;

import main.controller.GameController;

import main.dto.ChapterDto;
import main.dto.SceneDto;
import main.exception.ChapterNotFoundException;
import main.exception.ComandNonExistsException;
import main.exception.FileOfSaveNotCreateException;
import main.exception.SaveSlotsFullException;

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
                case CONTINUE -> continueLastGame(); // Ainda nn implementada
                case CREATE_NEW_SAVE -> createNewGame(); // Implementado sem sobrescrita de save
                case LOAD_EXISTING_SAVE -> loadExistingGame(); // Ainda nn
                case DELETE_SAVE -> deleteGameSession();
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

            saveGame();

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

                                if (playerInput.equals("sair"))
                                    isRunning = false;

                                comandsMenu(playerInput);
                            } catch (ComandNonExistsException comandNonExistsException) {
                                showText(comandNonExistsException.getMessage());
                            }
                        }
                    }

                    showText(">>> Pressione ENTER para prosseguir para a próxima cena: ");
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

                            if (playerInput.equals("sair"))
                                isRunning = false;

                            comandsMenu(playerInput);
                        } catch (ComandNonExistsException comandNonExistsException){
                            showText(comandNonExistsException.getMessage());
                        }
                    }
                }
            }

            showText(">>> Pressione ENTER para prosseguir para o próximo capitulo: ");
            ConsoleInputReader.readString();
        }
    }

    private void comandsMenu(String userInput) throws ComandNonExistsException {
        switch (userInput) {
            case "inventario" ->
                showInventory(gameController.loadInventoryData());
            case "status" ->
                showStatusPlayer(gameController.loadPlayerData());
            case "vinculos" ->
                showStatusRelationships(gameController.loadRelationshipData());
            case "salvar" -> saveGame();
            default ->
                throw new ComandNonExistsException("Comando não existente. Digite um número ou um comando válido.\n" +
                        "Para verificar todos os comandos válidos, volte ao menu inicial e leia as instruções.");
        }
    }

    private void saveGame() {
        gameController.saveGame();
    }

    private void continueLastGame() {

    }

    private void loadExistingGame() {

        showText("Informe o slot que voce deseja jogar: ");
        String numberSlot = Integer.toString(ConsoleInputReader.readInteger());

        showText("Informe seu nickname:");
        String personName = ConsoleInputReader.readString();

        gameController.startGameSession(personName, numberSlot);

        runGame(numberSlot);
    }

    private void deleteGameSession() {
        showText("Informe o número do save que você deseja apagar");

        String numberSlot =  Integer.toString(ConsoleInputReader.readInteger());

        gameController.deleteGameSession(numberSlot);
    }

    private void createNewGame() {
        boolean creatingNewGame = true;
         while (creatingNewGame) {
             try {
                 showText("Selecione o número do slot que você deseja para criar um novo jogo");

                 String numberSlot =  Integer.toString(ConsoleInputReader.readInteger());

                 gameController.createNewSlotOfGameSession(numberSlot);

                 showText("Slot" + numberSlot + " inicializado com sucesso!");
                 showText("Você já pode iniciar o jogo por meio dele");

                 showText("Digite 1 para inicializar outro slot:");
                 if (!ConsoleInputReader.readString().equals("1"))
                     creatingNewGame = false;
             } catch (FileOfSaveNotCreateException fileOfSaveNotCreateException) {
                 showText(fileOfSaveNotCreateException.getMessage());

             } catch (SaveSlotsFullException saveSlotsFullException) {
                 creatingNewGame = false;
                 showText(saveSlotsFullException.getMessage());
             }
         }
    }
}
