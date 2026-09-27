package main.view.utils;

import java.util.Scanner;

public class Reader {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static int readInteger() {
        while (true) {
            try {
                return Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Valor inválido digitado! Digite apenas números inteiros.");
            }
        }
    }

    public static String readString() {
        return SCANNER.nextLine();
    }
}
