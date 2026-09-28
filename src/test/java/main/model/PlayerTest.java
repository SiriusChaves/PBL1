package main.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void increaseSanityPlayer() {
        Player playerTest = new Player("O Estudante");

        assertEquals(50, playerTest.getSanity(),
                "O player deve começar com 50 de Sanidade por padrão.");
        assertEquals("O Estudante", playerTest.getName(),
                "É esperado que o player possua o mesmo nome que lhe foi dado.");

        playerTest.increaseSanityLevel(30);

        assertEquals(80, playerTest.getSanity(),
                "Após aumentar em 30, a sanidade do player deve ser 80.");

        playerTest.increaseSanityLevel(200);

        assertEquals(100, playerTest.getSanity(),
                "Após ultrapassar o limite, o player deve ter no máximo 100 de sanidade.");;
    }
}
