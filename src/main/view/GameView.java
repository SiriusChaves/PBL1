package main.view;

import main.controller.GameController;
import main.dto.*;
import main.view.utils.Leitura;

import java.util.List;

import static main.view.Menu.*;

public class GameView {
    private final GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
    }

    public Menu startMenu() {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                      MENU INICIAL                       ║");
        System.out.println("╠═════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-55s ║%n", "[" + INICIAR_PARTIDA.getValor() + "] Iniciar nova partida");
        System.out.printf("║ %-55s ║%n", "[" + INSTRUCOES.getValor() + "] Instruções");
        System.out.printf("║ %-55s ║%n", "[" + CREDITOS.getValor() + "] Créditos");
        System.out.printf("║ %-55s ║%n", "[" + SAIR.getValor() + "] Sair");

        System.out.println("╚═════════════════════════════════════════════════════════╝");
        System.out.print("Selecione uma opção: ");

        return Menu.parseMenu(Leitura.lerInteiro());
    }

    public void start() {
        Menu menuChoice = DEFAULT;

        do {
            menuChoice = startMenu();

            switch (menuChoice) {
                case INICIAR_PARTIDA -> {
                    gameController.startNewGame();
                    runGame();
                }
                case INSTRUCOES -> showInstructions();
                case CREDITOS -> showCredits();
                case SAIR -> exitGame();
                case DEFAULT -> System.out.println("escolha invalida");
            }
        } while(menuChoice != SAIR);

    }

    public void runGame() {
        ChapterDto currentChapter;
        SceneDto currentScene;
        String playerInput;
        boolean isRunning = true;

        while (isRunning) {
            currentChapter = gameController.loadCurrentChapter();
            showTitle(currentChapter.getTitle());

            int numeroDeCenas = currentChapter.getNumberScenes();

            for (int i = 0; i < numeroDeCenas; i++) {
                currentScene = gameController.loadCurrentScene();
                showDialogues(currentScene.getDialogues());

                if (currentScene.getNumberChoices() > 0) {

                    boolean isValidChoiceMade = false;

                    while (!isValidChoiceMade) {
                        showChoices(currentScene.getChoices());
                        System.out.println(">>> Informe o número da escolha (ou comandos como 'inventario'): ");
                        playerInput = Leitura.lerString();

                        try {
                            int escolhaNumerica = Integer.parseInt(playerInput);

                            if (escolhaNumerica > 0 && escolhaNumerica <= currentScene.getNumberChoices()) {
                                gameController.applySceneChoiceConsequence(escolhaNumerica - 1);
                                isValidChoiceMade = true;
                            } else {
                                System.out.println("Opção inválida. Escolha um numero entre 1 e " + currentScene.getNumberChoices() + ".");
                            }

                        } catch (NumberFormatException ex) {
                            boolean comandoReconhecido = comandsMenu(playerInput);
                            if (!comandoReconhecido) {
                                System.out.println("Comando não existente. Digite um número ou um comando válido.");
                                System.out.println("Para verificar todos os comandos válidos, volte ao menu inicial e leia as instruções.");
                            }
                        }
                    }
                    System.out.println(">>> Pressione ENTER para prosseguir...");
                    Leitura.lerString();
                }
                gameController.advanceToNextScene();
            }

            if (currentChapter.getNumberLastChoices() > 0) {
                boolean escolhaValidaFinal = false;

                while (!escolhaValidaFinal) {
                    showChoices(currentChapter.getChoices());
                    System.out.println(">>> Informe o número da Escolha Final do Capítulo: ");
                    playerInput = Leitura.lerString();

                    try {
                        int escolhaNumerica = Integer.parseInt(playerInput);
                        if (escolhaNumerica > 0 && escolhaNumerica <= currentChapter.getNumberLastChoices()) {
                            gameController.applyChapterFinalChoiceConsequence(escolhaNumerica - 1);
                            escolhaValidaFinal = true;
                        } else {
                            System.out.println("Opção inválida. Escolha um número entre 1 e " + currentChapter.getNumberLastChoices() + ".");
                        }
                    } catch (NumberFormatException ex) {
                        boolean comandoReconhecido = comandsMenu(playerInput);
                            if (!comandoReconhecido) {
                                System.out.println("Comando não existente. Digite um número ou um comando válido.");
                                System.out.println("Para verificar todos os comandos válidos, volte ao menu inicial e leia as instruções.");
                            }
                    }
                }
            }

            System.out.println("Continuar jogando? (Digite 1 para encerrar a sessão ou ENTER para prosseguir para o próximo capitulo: ");
            playerInput = Leitura.lerString();
            if (playerInput.equals("1")) {
                isRunning = false;
            }
        }
    }


    private boolean comandsMenu(String entrada) {
        switch (entrada) {
            case "inventario" -> {
                TerminalUI.showInventory(gameController.loadInventoryData());
                return true;
            }
            case "status" -> {
                TerminalUI.showStatusPlayer(gameController.loadPlayerData());
                return true;
            }
            case "vinculos" -> {
                TerminalUI.showStatusRelationships(gameController.loadRelationshipData());
                return true;
            }
            default -> {
                return false;
            }
        }
    }
    public void showDialogues(List<String> dialogues) {
        System.out.println("\n╔═════════════════════════════════════════════════════════╗");

        for (String dialogue : dialogues) {
            System.out.printf("║ %-55s ║%n", dialogue);
        }

        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    public void showChoices(List<String> choices) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-55s ║%n", " O QUE VOCÊ DECIDE FAZER?");
        System.out.println("╠═════════════════════════════════════════════════════════╣");

        int numeroEscolha = 1;
        for (String choice : choices) {
            String linhaEscolha = "  [" + numeroEscolha + "] " + choice;
            System.out.printf("║ %-55s ║%n", linhaEscolha);
            numeroEscolha++;
        }
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    public void showTitle(String title) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-55s ║%n", title.toUpperCase());
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    public void showInstructions() {
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

    public void showCredits() {
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

    public void exitGame() {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-55s ║%n", "    Encerrando a aplicação...");
        System.out.printf("║ %-55s ║%n", "    Obrigado por jogar!");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

}
