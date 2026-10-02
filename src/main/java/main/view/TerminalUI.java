package main.view;

import jdk.swing.interop.SwingInterOpUtils;
import main.dto.*;
import main.view.utils.ConsoleInputReader;
import org.w3c.dom.Text;

import java.security.PublicKey;
import java.util.List;

import static main.view.MainMenuOption.*;
import static main.view.MainMenuOption.EXIT;
import static main.view.SaveMenuOption.*;
import static main.view.SaveMenuOption.BACK_TO_MAIN_MENU;

public class TerminalUI {

    public static void showText(String text) {
        System.out.println(text);
    }

     public static void showSlotsSaves(List<SaveGameViewDto> saves) {

         System.out.println("SAVES ATUAIS");
        int numberSave = 1;
        for (SaveGameViewDto saveGameViewDto : saves) {

            System.out.println("Save do Slot" + numberSave + ":");

            if (saveGameViewDto != null) {
                System.out.println("Nome do protagonista: " + saveGameViewDto.playerName());
                System.out.println("Capitulo atual: " + saveGameViewDto.currentChapterName());
                System.out.println("Data do save: " + saveGameViewDto.dateSave());
            } else {
                System.out.println("save vazio");
            }

            System.out.println("-------------------------------------------------");
        }
     }

    public static void showInventory(List<ItemViewDto> items) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                       INVENTÁRIO                        ║");
        System.out.println("╠══════════════════════════════════════╦══════════════════╣");
        System.out.println("║ NOME DO ITEM                         ║ DESCRIÇÃO            ║");
        System.out.println("╠══════════════════════════════════════╬══════════════════╣");

        if (items.isEmpty()) {
            System.out.printf("║ %-55s ║%n", "Seu inventário está vazio...");
        } else {
            for (ItemViewDto item : items) {

                System.out.printf("║ %-36s ║ %-16s ║%n", item.name(), item.description());
            }
        }

        System.out.println("╚══════════════════════════════════════╩══════════════════╝");
    }

    public static void showStatusPlayer(PlayerDto player) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                    STATUS DO JOGADOR                    ║");
        System.out.println("╠═════════════════════════════════════════════════════════╣");
        System.out.printf("║ OPERADOR:     %-41s ║%n", player.name());
        System.out.println("╠═════════════════════════════════════════════════════════╣");
        System.out.printf("║ SANIDADE:     %-41s ║%n", String.format("%3d / 100", player.sanity()));
        System.out.printf("║ CONHECIMENTO: %-41s ║%n", String.format("%3d / 100", player.knowledge()));

        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    public static void showStatusRelationships(List<NpcDto> relationships) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║               STATUS DOS RELACIONAMENTOS                ║");
        System.out.println("╠══════════════════════════════════════╦══════════════════╣");
        System.out.println("║ NOME DO ALIADO                       ║ STATUS           ║");
        System.out.println("╠══════════════════════════════════════╬══════════════════╣");

        if (relationships.isEmpty()) {
            System.out.printf("║ %-55s ║%n", "Nenhum vínculo estabelecido ainda...");
        } else {
            for (NpcDto npc : relationships) {
                System.out.printf("║ %-36s ║ %-16s ║%n", npc.name(), npc.trustLevelTier());
            }
        }
        System.out.println("╚══════════════════════════════════════╩══════════════════╝");
    }

    public static void showDialogues(List<DialogueDto> dialogues) {
        System.out.println("\n╔═════════════════════════════════════════════════════════╗");

        for (DialogueDto dialogue : dialogues) {
            String content = dialogue.speaker() + ": " + dialogue.text();

            System.out.printf("║ %-55s ║%n", content);
        }

        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    public static void showChoices(List<String> choices) {
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

    public static void showTitle(String title) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-55s ║%n", title.toUpperCase());
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    public static void showInstructions() {
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

    public static void showCredits() {
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

    public static void exitGame() {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-55s ║%n", "    Encerrando a aplicação...");
        System.out.printf("║ %-55s ║%n", "    Obrigado por jogar!");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    public static MainMenuOption showStartMenu() {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                      MENU INICIAL                       ║");
        System.out.println("╠═════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-55s ║%n", "[" + START_GAME.getValue() + "] Iniciar nova partida");
        System.out.printf("║ %-55s ║%n", "[" + INSTRUCTIONS.getValue() + "] Instruções");
        System.out.printf("║ %-55s ║%n", "[" + CREDITS.getValue() + "] Créditos");
        System.out.printf("║ %-55s ║%n", "[" + EXIT.getValue() + "] Sair");

        System.out.println("╚═════════════════════════════════════════════════════════╝");
        System.out.print("Selecione uma opção: ");

        return MainMenuOption.toMainMenuOption(ConsoleInputReader.readInteger());
    }

    public static SaveMenuOption showSaveMenu() {
        System.out.println("MENU DE CARREGAMENTO DE SAVE");
        System.out.printf("%s %n", "[" + CONTINUE.getValue() + "] Continuar ultima partida");
        System.out.printf("%s %n", "[" + LOAD_EXISTING_SAVE.getValue() + "] Carregar save existente");
        System.out.printf("%s %n", "[" + CREATE_NEW_SAVE.getValue() + "] Criar novo save");
        System.out.printf("%s %n", "[" + BACK_TO_MAIN_MENU.getValue() + "] Voltar para o menu principal");

        System.out.print("Selecione uma opção: ");

        return SaveMenuOption.toSaveMenuOption(ConsoleInputReader.readInteger());
    }
}