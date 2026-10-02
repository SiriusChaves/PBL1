package main.view;

import main.controller.GameController;

import main.dto.ChapterDto;
import main.dto.DialogueDto;
import main.dto.SceneDto;
import main.exception.ChapterNotFoundException;
import main.exception.ComandNonExistsException;
import main.exception.GameNotSaveException;
import main.view.utils.ConsoleInputReader;

import java.util.List;

import static main.view.Menu.*;

public class GameView {
    private final GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
    }

    public void start() {
        Menu menuChoice;

        do {
            menuChoice = startMenu();

            switch (menuChoice) {
                case INICIAR_PARTIDA -> {
                    System.out.println("Informe o seu nickname: ");
                    String playerName = ConsoleInputReader.readString();

                    System.out.println("Informe o número do slot que você deseja salvar: ");
                    String slotIndex = ConsoleInputReader.readString();
                    gameController.startNewGame(playerName);
                    runGame(slotIndex);
                }
                case INSTRUCOES -> showInstructions();
                case CREDITOS -> showCredits();
                case SAIR -> exitGame();
                default -> System.out.println("Escolha inválida realizada!");
            }
        } while(menuChoice != SAIR);
    }

    private Menu startMenu() {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                      MENU INICIAL                       ║");
        System.out.println("╠═════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-55s ║%n", "[" + INICIAR_PARTIDA.getValor() + "] Iniciar nova partida");
        System.out.printf("║ %-55s ║%n", "[" + INSTRUCOES.getValor() + "] Instruções");
        System.out.printf("║ %-55s ║%n", "[" + CREDITOS.getValor() + "] Créditos");
        System.out.printf("║ %-55s ║%n", "[" + SAIR.getValor() + "] Sair");

        System.out.println("╚═════════════════════════════════════════════════════════╝");
        System.out.print("Selecione uma opção: ");

        return Menu.parseMenu(ConsoleInputReader.readInteger());
    }

    private void loadGameMenu () {
        System.out.println("MENU DE CARREGAMENTO DE SAVE");
        System.out.println("1 - Continuar ultima partida");
        System.out.println("2 - Iniciar nova partida");
        System.out.println("3 - Carregar partida");
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
                System.out.println(chapterNotFoundException.getMessage());
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
                        System.out.println(">>> Informe o número da escolha (ou comandos como 'inventario'): ");
                        playerInput = ConsoleInputReader.readString();

                        try {
                            int indexChoice = Integer.parseInt(playerInput);

                            if (indexChoice > 0 && indexChoice <= currentScene.numberChoices()) {
                                gameController.applySceneChoiceConsequence(indexChoice - 1);
                                isValidChoiceMade = true;
                            } else {
                                System.out.println("Opção inválida. Escolha um numero entre 1 e " + currentScene.numberChoices() + ".");
                            }

                        } catch (NumberFormatException numberFormatException) {
                            try {
                                comandsMenu(playerInput);
                            } catch (ComandNonExistsException comandNonExistsException) {
                                System.out.println(comandNonExistsException.getMessage());
                            }
                        }
                    }
                    System.out.println(">>> Pressione ENTER para prosseguir...");
                    ConsoleInputReader.readString();
                }
                gameController.advanceToNextScene();
            }

            if (currentChapter.numberLastChoices() > 0) {
                boolean isValidChoiceMade = false;

                while (!isValidChoiceMade) {
                    showChoices(currentChapter.choices());
                    System.out.println(">>> Informe o número da Escolha Final do Capítulo: ");
                    playerInput = ConsoleInputReader.readString();

                    try {
                        int indexChoice = Integer.parseInt(playerInput);
                        if (indexChoice > 0 && indexChoice <= currentChapter.numberLastChoices()) {
                            gameController.applyChapterFinalChoiceConsequence(indexChoice - 1);
                            isValidChoiceMade = true;
                        } else {
                            System.out.println("Opção inválida. Escolha um número entre 1 e " + currentChapter.numberLastChoices() + ".");
                        }
                    } catch (NumberFormatException numberFormatException) {
                        try {
                            comandsMenu(playerInput);
                        } catch (ComandNonExistsException comandNonExistsException){
                            System.out.println(comandNonExistsException.getMessage());
                        }
                    }
                }
            }

            System.out.println("Continuar jogando? (Digite 1 para encerrar a sessão ou ENTER para prosseguir para o próximo capitulo: ");
            playerInput = ConsoleInputReader.readString();
            if (playerInput.equals("1")) {
                isRunning = false;
            } else {
                try {
                    gameController.saveGame(slotIndex);
                } catch (GameNotSaveException gameNotSaveException) {
                    System.out.printf(gameNotSaveException.getMessage());
                }
            }
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

    private void showDialogues(List<DialogueDto> dialogues) {
        System.out.println("\n╔═════════════════════════════════════════════════════════╗");

        for (DialogueDto dialogue : dialogues) {
            String content = dialogue.speaker() + ": " + dialogue.text();

            System.out.printf("║ %-55s ║%n", content);
        }

        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    private void showChoices(List<String> choices) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-55s ║%n", " O QUE VOCÊ DECIDE FAZER?");
        System.out.println("╠═════════════════════════════════════════════════════════╣");

        int indexChoice = 1;
        for (String choice : choices) {
            String lineChoice = "  [" + indexChoice + "] " + choice;
            System.out.printf("║ %-55s ║%n", lineChoice);
            indexChoice++;
        }
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    private void showTitle(String title) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-55s ║%n", title.toUpperCase());
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    private void showInstructions() {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                       INSTRUÇÕES                        ║");
        System.out.println("╠═════════════════════════════════════════════════════════╣");

        System.out.printf("║ %-55s ║%n", "Bem-vindo ao Mundo de CyberFall.");
        System.out.printf("║ %-55s ║%n", "Suas escolhas moldam o destino do seu personagem.");
        System.out.printf("║ %-55s ║%n", "");

        System.out.printf("║ %-55s ║%n", "ATRIBUTOS E REGRAS:");
        System.out.printf("║ %-55s ║%n", "- SANIDADE: É a sua saúde mental. Chegar a zero");
        System.out.printf("║ %-55s ║%n", "  significa Game Over. Pense bem antes de agir.");
        System.out.printf("║ %-55s ║%n", "- CONHECIMENTO: Ajuda a desvendar segredos e pode");
        System.out.printf("║ %-55s ║%n", "  liberar caminhos e opções ocultas no futuro.");
        System.out.printf("║ %-55s ║%n", "- VÍNCULOS: Suas ações agradam ou irritam os NPCs.");
        System.out.printf("║ %-55s ║%n", "  Ter aliados pode salvar sua vida nos momentos finais.");
        System.out.printf("║ %-55s ║%n", "");

        System.out.printf("║ %-55s ║%n", "COMO JOGAR:");
        System.out.printf("║ %-55s ║%n", "Durante a narrativa, opções numeradas aparecerão.");
        System.out.printf("║ %-55s ║%n", "Digite apenas o NÚMERO correspondente (ex: 1, 2, 3)");
        System.out.printf("║ %-55s ║%n", "e aperte ENTER para confirmar sua decisão.");
        System.out.printf("║ %-55s ║%n", "");

        System.out.printf("║ %-55s ║%n", "COMANDOS ESPECIAIS (A QUALQUER MOMENTO):");
        System.out.printf("║ %-55s ║%n", "Sempre que o jogo pedir uma escolha, você pode");
        System.out.printf("║ %-55s ║%n", "digitar as seguintes palavras em vez de um número:");
        System.out.printf("║ %-55s ║%n", " > 'status'     - Verifica seus atributos atuais.");
        System.out.printf("║ %-55s ║%n", " > 'inventario' - Mostra os itens que você carrega.");
        System.out.printf("║ %-55s ║%n", " > 'vinculos'   - Exibe a situação com os NPCs.");
        System.out.printf("║ %-55s ║%n", "");
        System.out.printf("║ %-55s ║%n", "Pressione ENTER nas pausas para continuar lendo.");
        System.out.printf("║ %-55s ║%n", "Tenha um bom jogo..");

        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    private void showCredits() {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                        CRÉDITOS                         ║");
        System.out.println("╠═════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-55s ║%n", "Desenvolvimento e Programação:");
        System.out.printf("║ %-55s ║%n", " - Sirius e Rodrigo");
        System.out.printf("║ %-55s ║%n", "");
        System.out.printf("║ %-55s ║%n", "Roteiro e Game Design:");
        System.out.printf("║ %-55s ║%n", " - Sirius e Rodrigo");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    private void exitGame() {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-55s ║%n", "    Encerrando a aplicação...");
        System.out.printf("║ %-55s ║%n", "    Obrigado por jogar!");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

}
