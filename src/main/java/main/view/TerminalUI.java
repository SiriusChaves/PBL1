package main.view;

import main.dto.ItemViewDto;
import main.dto.NpcDto;
import main.dto.PlayerDto;

import java.util.List;

public class TerminalUI {
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
}