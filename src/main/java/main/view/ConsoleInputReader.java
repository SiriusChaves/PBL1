package main.view.utils;

import main.view.TerminalUI;

import java.util.Scanner;

public class ConsoleInputReader {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static int readInteger() {
        while (true) {
            try {
                return Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException numberFormatException) {
                TerminalUI.showText("Valor inválido digitado! Digite apenas números inteiros.");
            }
        }
    }

    public static String readString() {
        return SCANNER.nextLine();
    }
}
