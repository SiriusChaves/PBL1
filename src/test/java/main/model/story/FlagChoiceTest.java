package main.model.story;

import com.google.gson.Gson;
import main.model.player.Item;
import main.model.player.ItemType;
import main.model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FlagChoiceTest {

    private static final Gson GSON = new Gson();

    @Test
    void flagShouldNotDuplicateValues() {

        Flag flag = new Flag();

        flag.addFlag("A");
        flag.addFlag("A");

        assertEquals(Set.of("A"), flag.getActiveFlags());
    }

    @Test
    void flagSetShouldBeReadOnly() {

        Flag flag = new Flag();

        assertThrows(
                UnsupportedOperationException.class,
                () -> flag.getActiveFlags().add("A")
        );
    }

    @Test
    void defaultChoiceShouldBeAvailable() {

        Choice choice = new Choice(
                "Escolha",
                "Gabriel",
                0,
                0,
                0
        );

        assertTrue(
                choice.isAvailable(
                        new Player("Teste"),
                        new Flag()
                )
        );
    }

    @Test
    void choiceShouldRequireItemAndFlag() {

        Choice choice = choiceFromJson(
                """
                {
                  "text":"Protegida",
                  "nameTargetNpc":"Gabriel",
                  "sanityChange":0,
                  "knowledgeChange":0,
                  "trustLevelChange":0,
                  "requireItemId":"1",
                  "rewardItem":{
                    "id":"0",
                    "name":"sem nome",
                    "description":"sem descricao",
                    "type":"NONE"
                  },
                  "requireFlag":"FLAG_OK",
                  "generatedFlag":"NONE"
                }
                """
        );

        Player player = new Player("Teste");
        Flag flags = new Flag();

        assertFalse(choice.isAvailable(player, flags));

        player.storeItemInInventory(
                new Item(
                        "1",
                        "Chave",
                        "...",
                        ItemType.KEY_ITEM
                )
        );

        assertFalse(choice.isAvailable(player, flags));

        flags.addFlag("FLAG_OK");

        assertTrue(choice.isAvailable(player, flags));
    }

    @Test
    void applyEffectsShouldChangeStateAndConsumeRequiredItem() {

        Choice choice = choiceFromJson(
                """
                {
                  "text":"Escolha completa",
                  "nameTargetNpc":"Gabriel",
                  "sanityChange":-15,
                  "knowledgeChange":10,
                  "trustLevelChange":-20,
                  "requireItemId":"1",
                  "rewardItem":{
                    "id":"2",
                    "name":"Recompensa",
                    "description":"...",
                    "type":"KEY_ITEM"
                  },
                  "requireFlag":"NONE",
                  "generatedFlag":"FLAG_GERADA"
                }
                """
        );

        Player player = new Player("Teste");

        player.storeItemInInventory(
                new Item(
                        "1",
                        "Consumido",
                        "...",
                        ItemType.KEY_ITEM
                )
        );

        Flag flags = new Flag();

        choice.applyEffects(player, flags);

        assertEquals(35, player.getSanity());
        assertEquals(60, player.getKnowledge());
        assertEquals(
                30,
                player.getRelationships()
                        .get("Gabriel")
                        .getTrustLevel()
        );

        assertFalse(
                player.getInventory().hasItem("1")
        );

        assertTrue(
                player.getInventory().hasItem("2")
        );

        assertTrue(
                flags.isFlagActive("FLAG_GERADA")
        );
    }

    private Choice choiceFromJson(String json) {
        return GSON.fromJson(json, Choice.class);
    }
}
