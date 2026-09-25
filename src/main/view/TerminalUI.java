package main.view;

import main.dto.ItemDtoRecord;
import main.dto.NpcDtoRecord;
import main.dto.PlayerDtoRecord;

import java.util.List;

public class TerminalUI {
    public static void showInventory(List<ItemDtoRecord> items) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                       INVENTÁRIO                        ║");
        System.out.println("╠══════════════════════════════════════╦══════════════════╣");
        System.out.println("║ NOME DO ITEM                         ║ TIPO             ║");
        System.out.println("╠══════════════════════════════════════╬══════════════════╣");

        if (items.isEmpty()) {
            System.out.printf("║ %-55s ║%n", "Seu inventário está vazio...");
        } else {
            for (ItemDtoRecord item : items) {

                System.out.printf("║ %-36s ║ %-16s ║%n", item.name(), item.itemType());
            }
        }

        System.out.println("╚══════════════════════════════════════╩══════════════════╝");
    }

    public static void showStatusPlayer(PlayerDtoRecord player) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                    STATUS DO JOGADOR                    ║");
        System.out.println("╠═════════════════════════════════════════════════════════╣");
        System.out.printf("║ OPERADOR:     %-41s ║%n", player.name());
        System.out.println("╠═════════════════════════════════════════════════════════╣");
        System.out.printf("║ SANIDADE:     %-41s ║%n", String.format("%3d / 100", player.sanity()));
        System.out.printf("║ CONHECIMENTO: %-41s ║%n", String.format("%3d / 100", player.knowledge()));

        System.out.println("╚═════════════════════════════════════════════════════════╝");
    }

    public static void showStatusRelationships(List<NpcDtoRecord> relationships) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║               STATUS DOS RELACIONAMENTOS                ║");
        System.out.println("╠══════════════════════════════════════╦══════════════════╣");
        System.out.println("║ NOME DO ALIADO                       ║ STATUS           ║");
        System.out.println("╠══════════════════════════════════════╬══════════════════╣");

        if (relationships.isEmpty()) {
            System.out.printf("║ %-55s ║%n", "Nenhum vínculo estabelecido ainda...");
        } else {
            for (NpcDtoRecord npc : relationships) {
                System.out.printf("║ %-36s ║ %-16s ║%n", npc.name(), npc.trustLevelTier());
            }
        }
        System.out.println("╚══════════════════════════════════════╩══════════════════╝");
    }
}