package main.view;

import main.dto.*;

import java.util.List;

import static main.view.MainMenuOption.*;
import static main.view.MainMenuOption.EXIT;
import static main.view.SaveMenuOption.*;
import static main.view.SaveMenuOption.BACK_TO_MAIN_MENU;

public class TerminalUI {

    public static void showText(String text) {
        System.out.println(text);
    }

    public static void showEndingScreen() {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                    FIM DA JORNADA                       ║"
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "Você concluiu um dos finais de MegaBrain."
        );

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void showAchievements(List<AchievementDto> achievements) {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                  GALERIA DE CONQUISTAS                  ║"
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        if (achievements.isEmpty()) {

            System.out.printf(
                    "║ %-55s ║%n",
                    "Nenhuma conquista foi desbloqueada."
            );

            System.out.println(
                    "╚═════════════════════════════════════════════════════════╝"
            );

            return;
        }

        for (int i = 0; i < achievements.size(); i++) {

            AchievementDto achievement = achievements.get(i);

            if (achievement == null) {

                System.out.printf(
                        "║ %-55s ║%n",
                        "[" + (i + 1) + "] ?"
                );

            } else {

                System.out.printf(
                        "║ %-55s ║%n",
                        "[" + (i + 1) + "] " + achievement.name()
                );

                String[] words = achievement.description().split(" ");
                String line = "";

                for (String word : words) {

                    if ((line + word).length() > 54) {

                        System.out.printf(
                                "║ %-55s ║%n",
                                line
                        );

                        line = "";
                    }

                    line += word + " ";
                }

                if (!line.isEmpty()) {
                    System.out.printf(
                            "║ %-55s ║%n",
                            line
                    );
                }
            }

            if (i < achievements.size() - 1) {
                System.out.println(
                        "╠═════════════════════════════════════════════════════════╣"
                );
            }
        }

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void showSlotsSaves(List<SaveViewDto> saves) {

        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                     SAVES ATUAIS                        ║");
        System.out.println("╠═════════════════════════════════════════════════════════╣");

        int numberSave = 1;

        for (SaveViewDto save : saves) {

            if (save == null) {

                System.out.printf(
                        "║ %-55s ║%n",
                        "SLOT " + numberSave + " - VAZIO"
                );

            } else {

                System.out.printf(
                        "║ %-55s ║%n",
                        "SLOT " + numberSave + " - OCUPADO"
                );

                System.out.printf(
                        "║ %-55s ║%n",
                        "Nome do protagonista: " + save.playerName()
                );

                String chapterText =
                        "Capítulo atual: " + save.currentChapterName();

                String[] words = chapterText.split(" ");
                String line = "";

                for (String word : words) {

                    if ((line + word).length() > 55) {

                        System.out.printf(
                                "║ %-55s ║%n",
                                line
                        );

                        line = "";
                    }

                    line += word + " ";
                }

                if (!line.isEmpty()) {
                    System.out.printf(
                            "║ %-55s ║%n",
                            line
                    );
                }

                System.out.printf(
                        "║ %-55s ║%n",
                        "Data do save: " + save.dateSave()
                );
            }

            if (numberSave < saves.size()) {
                System.out.println(
                        "╠═════════════════════════════════════════════════════════╣"
                );
            }

            numberSave++;
        }

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void showInventory(List<ItemViewDto> items) {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                       INVENTÁRIO                        ║"
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        if (items.isEmpty()) {

            System.out.printf(
                    "║ %-55s ║%n",
                    "Seu inventário está vazio..."
            );

        } else {

            int index = 1;

            for (ItemViewDto item : items) {

                System.out.printf(
                        "║ %-55s ║%n",
                        "[" + index + "] " + item.name()
                );

                String text =
                        "Descrição: " + item.description();

                String[] words = text.split(" ");
                String line = "";

                for (String word : words) {

                    if ((line + word).length() > 55) {

                        System.out.printf(
                                "║ %-55s ║%n",
                                line
                        );

                        line = "";
                    }

                    line += word + " ";
                }

                if (!line.isEmpty()) {
                    System.out.printf(
                            "║ %-55s ║%n",
                            line
                    );
                }

                if (index < items.size()) {
                    System.out.println(
                            "╠═════════════════════════════════════════════════════════╣"
                    );
                }

                index++;
            }
        }

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void showStatusPlayer(PlayerDto player) {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                    STATUS DO JOGADOR                    ║"
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        System.out.printf(
                "║ OPERADOR:     %-41s ║%n",
                player.name()
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        System.out.printf(
                "║ SANIDADE:     %-41s ║%n",
                String.format("%3d / 100", player.sanity())
        );

        System.out.printf(
                "║ CONHECIMENTO: %-41s ║%n",
                String.format("%3d / 100", player.knowledge())
        );

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void showStatusRelationships(List<NpcDto> relationships) {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║               STATUS DOS RELACIONAMENTOS                ║"
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        if (relationships.isEmpty()) {

            System.out.printf(
                    "║ %-55s ║%n",
                    "Nenhum vínculo estabelecido ainda..."
            );

        } else {

            int index = 1;

            for (NpcDto npc : relationships) {

                System.out.printf(
                        "║ %-55s ║%n",
                        npc.name()
                );

                System.out.printf(
                        "║ %-55s ║%n",
                        "Status: " + npc.trustLevelTier()
                );

                if (index < relationships.size()) {

                    System.out.println(
                            "╠═════════════════════════════════════════════════════════╣"
                    );
                }

                index++;
            }
        }

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void showDialogues(List<DialogueDto> dialogues) {

        System.out.println(
                "\n╔═════════════════════════════════════════════════════════╗"
        );

        for (int i = 0; i < dialogues.size(); i++) {

            DialogueDto dialogue = dialogues.get(i);

            System.out.printf(
                    "║ %-55s ║%n",
                    dialogue.speaker().toUpperCase()
            );

            String[] words = dialogue.text().split(" ");
            String line = "";

            for (String word : words) {

                if ((line + word).length() > 55) {

                    System.out.printf(
                            "║ %-55s ║%n",
                            line
                    );

                    line = "";
                }

                line += word + " ";
            }

            if (!line.isEmpty()) {
                System.out.printf(
                        "║ %-55s ║%n",
                        line
                );
            }

            if (i < dialogues.size() - 1) {
                System.out.println(
                        "╠═════════════════════════════════════════════════════════╣"
                );
            }
        }

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void showChoices(List<String> choices) {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "O QUE VOCÊ DECIDE FAZER?"
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        int indexChoice = 1;

        for (String choice : choices) {

            String text =
                    "[" + indexChoice + "] " + choice;

            String[] words = text.split(" ");
            String line = "";

            for (String word : words) {

                if ((line + word).length() > 55) {

                    System.out.printf(
                            "║ %-55s ║%n",
                            line
                    );

                    line = "";
                }

                line += word + " ";
            }

            if (!line.isEmpty()) {
                System.out.printf(
                        "║ %-55s ║%n",
                        line
                );
            }

            if (indexChoice < choices.size()) {
                System.out.printf(
                        "║ %-55s ║%n",
                        ""
                );
            }

            indexChoice++;
        }

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void showTitle(String title) {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        String titleUpper = title.toUpperCase();

        String[] words = titleUpper.split(" ");
        String line = "";

        for (String word : words) {

            if ((line + word).length() > 55) {

                System.out.printf(
                        "║ %-55s ║%n",
                        line
                );

                line = "";
            }

            line += word + " ";
        }

        if (!line.isEmpty()) {
            System.out.printf(
                    "║ %-55s ║%n",
                    line
            );
        }

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void showInstructions() {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                       INSTRUÇÕES                        ║"
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "Bem-vindo ao Mundo de CyberFall."
        );

        System.out.printf(
                "║ %-55s ║%n",
                "Suas escolhas moldam o destino do seu personagem."
        );

        System.out.printf("║ %-55s ║%n", "");

        System.out.printf(
                "║ %-55s ║%n",
                "ATRIBUTOS E REGRAS:"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "- SANIDADE: É a sua saúde mental. Chegar a zero"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "  significa Game Over. Pense bem antes de agir."
        );

        System.out.printf(
                "║ %-55s ║%n",
                "- CONHECIMENTO: Ajuda a desvendar segredos e pode"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "  liberar caminhos e opções ocultas no futuro."
        );

        System.out.printf(
                "║ %-55s ║%n",
                "- VÍNCULOS: Suas ações agradam ou irritam os NPCs."
        );

        System.out.printf(
                "║ %-55s ║%n",
                "  Ter aliados pode ajudar nos momentos finais."
        );

        System.out.printf("║ %-55s ║%n", "");

        System.out.printf(
                "║ %-55s ║%n",
                "COMO JOGAR:"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "Durante a narrativa, opções numeradas aparecerão."
        );

        System.out.printf(
                "║ %-55s ║%n",
                "Digite o número correspondente à escolha."
        );

        System.out.printf(
                "║ %-55s ║%n",
                "Pressione ENTER para confirmar."
        );

        System.out.printf("║ %-55s ║%n", "");

        System.out.printf(
                "║ %-55s ║%n",
                "COMANDOS ESPECIAIS:"
        );

        System.out.printf(
                "║ %-55s ║%n",
                " > 'status'     - Ver seus atributos."
        );

        System.out.printf(
                "║ %-55s ║%n",
                " > 'inventario' - Ver seus itens."
        );

        System.out.printf(
                "║ %-55s ║%n",
                " > 'vinculos'   - Ver relações com NPCs."
        );

        System.out.printf("║ %-55s ║%n", "");

        System.out.printf(
                "║ %-55s ║%n",
                "Pressione ENTER nas pausas para continuar."
        );

        System.out.printf(
                "║ %-55s ║%n",
                "Tenha um bom jogo!"
        );

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void showCredits() {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                        CRÉDITOS                         ║"
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "Desenvolvimento e Programação:"
        );

        System.out.printf(
                "║ %-55s ║%n",
                " - Sirius e Rodrigo"
        );

        System.out.printf("║ %-55s ║%n", "");

        System.out.printf(
                "║ %-55s ║%n",
                "Roteiro e Game Design:"
        );

        System.out.printf(
                "║ %-55s ║%n",
                " - Sirius e Rodrigo"
        );

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static void exitGame() {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "Encerrando a aplicação..."
        );

        System.out.printf(
                "║ %-55s ║%n",
                "Obrigado por jogar!"
        );

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );
    }

    public static MainMenuOption showStartMenu() {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                      MENU INICIAL                       ║"
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "[" + START_GAME.getValue() + "] Iniciar partida"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "[" + INSTRUCTIONS.getValue() + "] Instruções"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "[" + CREDITS.getValue() + "] Créditos"
        );
        System.out.printf(
                "║ %-55s ║%n",
                "[" + ACHIEVEMENTS.getValue() + "] Galeria de conquistas"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "[" + EXIT.getValue() + "] Sair"
        );

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );

        System.out.print("Selecione uma opção: ");

        return MainMenuOption.toMainMenuOption(
                ConsoleInputReader.readInteger()
        );
    }

    public static SaveMenuOption showSaveMenu() {

        System.out.println(
                "╔═════════════════════════════════════════════════════════╗"
        );

        System.out.println(
                "║                      MENU DE SAVES                      ║"
        );

        System.out.println(
                "╠═════════════════════════════════════════════════════════╣"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "[" + CONTINUE.getValue() +
                        "] Continuar última partida"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "[" + CREATE_NEW_SAVE.getValue() +
                        "] Criar nova partida"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "[" + LOAD_EXISTING_SAVE.getValue() +
                        "] Carregar partida existente"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "[" + DELETE_SAVE.getValue() +
                        "] Excluir save"
        );

        System.out.printf(
                "║ %-55s ║%n",
                "[" + BACK_TO_MAIN_MENU.getValue() +
                        "] Voltar ao menu principal"
        );

        System.out.println(
                "╚═════════════════════════════════════════════════════════╝"
        );

        System.out.print("Selecione uma opção: ");

        return SaveMenuOption.toSaveMenuOption(
                ConsoleInputReader.readInteger()
        );
    }
}