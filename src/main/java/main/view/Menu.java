package main.view;

public enum Menu {
    INICIAR_PARTIDA(1),
    INSTRUCOES(2),
    CREDITOS(3),
    SAIR(4),
    DEFAULT(5);

    private final int valor;

    Menu(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static Menu parseMenu(int valor) {
        return switch (valor) {
          case 1 -> INICIAR_PARTIDA;
          case 2 -> INSTRUCOES;
          case 3 -> CREDITOS;
          case 4 -> SAIR;
          default -> DEFAULT;
        };
    }

}
