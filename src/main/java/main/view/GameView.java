package main.view;

import main.controller.GameController;

import main.dto.ChapterDto;
import main.dto.SceneDto;
import main.exception.ChapterNotFoundException;
import main.exception.ComandNonExistsException;
import main.exception.FileOfSaveNotCreateException;
import main.model.profile.Preference;
import main.model.profile.UserProfile;
import main.view.menu.MainMenuOption;
import main.view.menu.SaveMenuOption;
import main.view.terminal.ConsoleInputReader;

import static main.view.menu.MainMenuOption.*;
import static main.view.menu.SaveMenuOption.*;
import static main.view.terminal.TerminalUI.*;

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
                case PREFERENCES -> updatePreference();
                case ACHIEVEMENTS -> showAchievements(gameController.loadAchievements());
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
                case NEW_GAME -> createNewGame();
                case LOAD_GAME -> loadExistingGame();
                case DELETE_SAVE -> deleteGameSession();
                default -> showText("Opção invalida escolhida");
            }

        } while (saveMenuOption != BACK_TO_MAIN_MENU);
    }

    private void runGame() {

        UserProfile profile = gameController.loadUserProfile();

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

            if (profile.hasPreference(Preference.AUTOSAVE)) saveGame();

            showTitle(currentChapter.title());

            while (true) {

                if (profile.hasPreference(Preference.SHOW_PLAYER_HUD)) {

                    showPlayerHud(gameController.loadPlayerData());
                }

                currentScene = gameController.loadCurrentScene();

                showDialogues(currentScene.dialogues());

                if (currentScene.numberChoices() > 0) {

                    boolean isValidChoiceMade = false;

                    while (!isValidChoiceMade) {

                        if (profile.hasPreference(Preference.SHOW_COMMAND_HUD)) {

                            showCommandsHud();
                        }

                        showChoices(currentScene.choices());

                        showText(
                                ">>> Informe o número da escolha "
                                + "(ou comandos como 'inventario'): "
                        );

                        playerInput = ConsoleInputReader.readString();

                        try {

                            int indexChoice = Integer.parseInt(playerInput);

                            if (indexChoice > 0 && indexChoice <= currentScene.numberChoices()) {

                                gameController.applySceneChoiceConsequence(indexChoice - 1);

                                if (gameController.isGameOver()) {
                                    showGameOverScreen();
                                    return;
                                }

                                isValidChoiceMade = true;

                            } else {

                                showText(
                                        "Opção inválida. Escolha um numero entre 1 e "
                                                + currentScene.numberChoices()
                                                + "."
                                );
                            }

                        } catch (NumberFormatException numberFormatException) {

                            try {

                                if (playerInput.equalsIgnoreCase("sair"))
                                    return;

                                executeCommand(playerInput.toLowerCase());

                            } catch (ComandNonExistsException comandNonExistsException) {

                                showText(comandNonExistsException.getMessage());
                            }
                        }
                    }

                    showText(
                            ">>> Pressione ENTER para prosseguir "
                            + "para a próxima cena: "
                    );

                    ConsoleInputReader.readString();
                }

                if (!gameController.hasNextScene())
                    break;

                gameController.advanceToNextScene();
            }

            if (currentChapter.numberLastChoices() > 0) {

                boolean isValidChoiceMade = false;

                while (!isValidChoiceMade) {

                    if (profile.hasPreference(Preference.SHOW_COMMAND_HUD)) {

                        showCommandsHud();
                    }

                    showChoices(currentChapter.choices());

                    showText(
                            ">>> Informe o número da Escolha Final do Capítulo: "
                    );

                    playerInput = ConsoleInputReader.readString();

                    try {

                        int indexChoice = Integer.parseInt(playerInput);

                        if (indexChoice > 0 && indexChoice <= currentChapter.numberLastChoices()) {

                            gameController.applyChapterFinalChoiceConsequence(indexChoice - 1);

                            if (gameController.isGameOver()) {
                                showGameOverScreen();
                                return;
                            }

                            isValidChoiceMade = true;

                            showText(
                                    ">>> Pressione ENTER para prosseguir"
                                            + " para o próximo capitulo: "
                            );

                            ConsoleInputReader.readString();

                        } else {

                            showText(
                                    "Opção inválida. Escolha um número entre 1 e "
                                            + currentChapter.numberLastChoices()
                                            + "."
                            );
                        }

                    } catch (NumberFormatException numberFormatException) {

                        try {

                            if (playerInput.equalsIgnoreCase("sair"))
                                return;

                            executeCommand(playerInput.toLowerCase());

                        } catch (ComandNonExistsException comandNonExistsException){
                            showText(comandNonExistsException.getMessage());
                        }
                    }
                }
            }


            if (gameController.isFinalChapter()) {

                showEndingScreen();
                gameController.setGameIsCompleted();

                saveGame();
                return;
            }


        }
    }

    private void executeCommand(String userInput) throws ComandNonExistsException {

        switch (userInput) {

            case "inventario" -> showInventory(gameController.loadInventoryData());

            case "status" -> showStatusPlayer(gameController.loadPlayerData());

            case "vinculos" -> showStatusRelationships(gameController.loadRelationshipData());

            case "salvar" -> saveGame();

            default ->
                throw new ComandNonExistsException(
                        "Comando não existente. Digite um número ou um comando válido.\n"
                        + "Para verificar todos os comandos válidos, volte ao menu inicial "
                        + "e leia as instruções."
                );
        }
    }

    private void updatePreference() {

        UserProfile profile = gameController.loadUserProfile();

        Preference preference = showPreferenceMenu(profile.getPreferences());

        switch (preference) {

            case AUTOSAVE -> gameController.togglePreference(Preference.AUTOSAVE);

            case SHOW_COMMAND_HUD -> gameController.togglePreference(Preference.SHOW_COMMAND_HUD);

            case SHOW_PLAYER_HUD -> gameController.togglePreference(Preference.SHOW_PLAYER_HUD);

            case DEFAULT -> showText("Preferência inválida");
        }
    }

    private void saveGame() {
        gameController.saveGame();
    }

    private void continueLastGame() {

        UserProfile profile = gameController.loadUserProfile();

        String lastPlayedSlot = profile.getLastPlayedSlotId();

        if (lastPlayedSlot.equals("-1") || !gameController.hasSaveDataAndSaveExists(lastPlayedSlot)) {

            showText("Você não tem um jogo para continuar");

        } else {

            gameController.loadGame(lastPlayedSlot);

            runGame();
        }
    }

    private void loadExistingGame() {

        while (true) {

            showText("Informe o slot que voce deseja jogar: ");

            String slotIndex = Integer.toString(ConsoleInputReader.readInteger());

            if (!isValidSlotIndex(Integer.parseInt(slotIndex))) {

                showText(
                        "Número de slot inválido! Existem apenas slots"
                                + " com index 1, 2 ou 3!"
                );

            } else {

                if (!gameController.hasSaveDataAndSaveExists(slotIndex)) {

                    showText("Não existe partida salva nesse slot.");
                    return;
                }

                gameController.loadGame(slotIndex);

                gameController.updateLastPlayedSlot(slotIndex);

                runGame();
                return;
            }
        }
    }

    private boolean isValidSlotIndex(Integer slotIndex) {
        return slotIndex >= 1 && slotIndex <= 3;
    }

    private void deleteGameSession() {

        while (true) {

            showText("Informe o número do save que você deseja apagar");

            String slotIndex =  Integer.toString(ConsoleInputReader.readInteger());

            if (!isValidSlotIndex(Integer.parseInt(slotIndex))) {

                showText(
                        "Número de slot inválido! Existem apenas slots"
                                + " com index 1, 2 ou 3!"
                );
            } else {

                showText(
                        "Você deseja realmente excluir PERMANENTEMENTE"
                                + " o save do slot " + slotIndex + "?\n"
                                + ">>> Digite 1 para cancelar ou"
                                + " ENTER para prosseguir com a exclusão"
                );

                if (ConsoleInputReader.readString().equals("1")) {

                    showText("Cancelando exclusão do save " + slotIndex + ".");
                    return;
                }

                UserProfile profile = gameController.loadUserProfile();

                if (slotIndex.equals(profile.getLastPlayedSlotId()))
                    gameController.updateLastPlayedSlot("-1");

                gameController.deleteGameSession(slotIndex);

                showText("Save " + slotIndex + "deletado com sucesso!");
                return;
            }
        }
    }

    private void createNewGame() {

         while (true) {

             try {

                 showText(
                         "Selecione o número do slot que você "
                          + "deseja para criar um novo jogo"
                 );

                 String numberSlot =
                         Integer.toString(ConsoleInputReader.readInteger());

                 if (!isValidSlotIndex(Integer.parseInt(numberSlot))) {

                     showText(
                             "Número de slot inválido! Existem apenas slots"
                                     + " com index 1, 2 ou 3!"
                     );
                 } else {

                     if (gameController.hasSaveDataAndSaveExists(numberSlot)) {

                         showText("O save já possui dados carregados.");

                         showText(
                                 "Digite 1 se você deseja sobrescrever o save"
                                         + " ou pressione ENTER para cancelar."
                         );

                         if (!ConsoleInputReader.readString().equals("1")) {
                             showText("Cancelando sobrescrita de save!");
                             return;

                         } else {

                             showText("Save " + numberSlot + "sobrescrito!");

                             gameController.prepareSaveSlot(numberSlot, true);

                             gameController.startNewGame(numberSlot);

                             gameController.updateLastPlayedSlot(numberSlot);

                             runGame();

                             return;
                         }

                     } else {

                         gameController.prepareSaveSlot(
                                 numberSlot,
                                 false
                         );

                         gameController.startNewGame(numberSlot);

                         gameController.updateLastPlayedSlot(numberSlot);

                         runGame();

                         return;
                     }
                 }

             } catch (FileOfSaveNotCreateException saveException) {

                 showText(saveException.getMessage());
                 return;
             }
         }
    }
}
