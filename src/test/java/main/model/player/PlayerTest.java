package main.model.player;

import main.model.npc.Npc;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void newPlayerShouldStartWithDefaultState() {

        Player player = new Player("Teste");

        assertEquals("Teste", player.getName());
        assertEquals(50, player.getSanity());
        assertEquals(50, player.getKnowledge());
        assertTrue(player.getInventory().getItems().isEmpty());

        assertEquals(
                Set.of(
                        "Rebeca",
                        "Gabriel",
                        "Eng. Silas",
                        "Lia"
                ),
                player.getRelationships().keySet()
        );
    }

    @Test
    void sanityShouldRespectLowerAndUpperLimits() {

        Player player = new Player("Teste");

        player.decreaseSanityLevel(70);
        assertEquals(0, player.getSanity());

        player.increaseSanityLevel(150);
        assertEquals(100, player.getSanity());
    }

    @Test
    void decreaseSanityShouldAcceptNegativeMagnitude() {

        Player player = new Player("Teste");

        player.decreaseSanityLevel(-20);

        assertEquals(30, player.getSanity());
    }

    @Test
    void knowledgeShouldRespectLowerAndUpperLimits() {

        Player player = new Player("Teste");

        player.decreaseKnowledgeLevel(80);
        assertEquals(0, player.getKnowledge());

        player.increaseKnowledgeLevel(150);
        assertEquals(100, player.getKnowledge());
    }

    @Test
    void playerShouldDelegateTrustChangesToNpc() {

        Player player = new Player("Teste");

        player.increaseNpcTrustLevel("Gabriel", 20);
        assertEquals(
                70,
                player.getRelationships()
                        .get("Gabriel")
                        .getTrustLevel()
        );

        player.decreaseNpcTrustLevel("Gabriel", -30);
        assertEquals(
                40,
                player.getRelationships()
                        .get("Gabriel")
                        .getTrustLevel()
        );
    }

    @Test
    void relationshipMapShouldBeReadOnly() {

        Player player = new Player("Teste");

        Map<String, Npc> relationships =
                player.getRelationships();

        assertThrows(
                UnsupportedOperationException.class,
                () -> relationships.put(
                        "Outro",
                        new Npc("Outro")
                )
        );
    }

    @Test
    void restoredPlayerShouldKeepProvidedState() {

        Map<String, Npc> relationships = Map.of(
                "Gabriel",
                new Npc("Gabriel", 25)
        );

        Player player = new Player(
                "O Arquiteto",
                35,
                80,
                relationships
        );

        assertEquals("O Arquiteto", player.getName());
        assertEquals(35, player.getSanity());
        assertEquals(80, player.getKnowledge());
        assertEquals(
                25,
                player.getRelationships()
                        .get("Gabriel")
                        .getTrustLevel()
        );
    }
}
