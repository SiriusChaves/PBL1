package main.mapper;

import main.dto.*;
import main.model.achievement.Achievement;
import main.model.npc.Npc;
import main.model.player.Item;
import main.model.player.ItemType;
import main.model.player.Player;
import main.model.story.Choice;
import main.model.story.Dialogue;
import main.model.story.Scene;
import main.persistence.dto.ItemPersistenceDto;
import main.persistence.json.ChapterDaoJson;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    @Test
    void playerMapperShouldCopyVisibleState() {

        Player player = new Player("O Arquiteto");
        player.increaseSanityLevel(10);
        player.decreaseKnowledgeLevel(5);

        PlayerDto dto = PlayerMapper.toDto(player);

        assertEquals("O Arquiteto", dto.name());
        assertEquals(60, dto.sanity());
        assertEquals(45, dto.knowledge());
    }

    @Test
    void npcMapperShouldConvertBothDirections() {

        Npc npc = new Npc("Gabriel", 75);

        NpcDto dto = NpcMapper.toDto(npc);

        assertEquals("Gabriel", dto.name());
        assertEquals(75, dto.trustLevel());
        assertEquals("ALIADO PLENO", dto.trustLevelTier());

        Npc restored = NpcMapper.toEntity(dto);

        assertEquals("Gabriel", restored.getName());
        assertEquals(75, restored.getTrustLevel());
    }

    @Test
    void itemMapperShouldCreateViewDtoAndEntity() {

        Item item = new Item(
                "7",
                "root_override.bin",
                "item chave",
                ItemType.KEY_ITEM
        );

        ItemViewDto viewDto = ItemMapper.toDto(item);

        assertEquals("root_override.bin", viewDto.name());
        assertEquals("item chave", viewDto.description());

        ItemPersistenceDto persistenceDto =
                new ItemPersistenceDto(
                        "Chip",
                        "consumível",
                        "CONSUMABLE",
                        "3"
                );

        Item restored =
                ItemMapper.toEntity(persistenceDto);

        assertEquals("3", restored.getId());
        assertEquals("Chip", restored.getName());
        assertEquals(
                ItemType.CONSUMABLE,
                restored.getItemType()
        );
    }

    @Test
    void dialogueMapperShouldPreserveFields() {

        DialogueDto dto =
                DialogueMapper.toDto(
                        new Dialogue(
                                "Lia",
                                "Teste"
                        )
                );

        assertEquals("Lia", dto.speaker());
        assertEquals("Teste", dto.text());
    }

    @Test
    void sceneMapperShouldUseOnlyAvailableChoices() {

        Choice available =
                new Choice(
                        "Disponível",
                        "Gabriel",
                        0,
                        0,
                        0
                );

        Choice blocked =
                new Choice(
                        "Bloqueada",
                        "Gabriel",
                        0,
                        0,
                        0
                );

        Scene scene = new Scene(
                "1",
                List.of(
                        new Dialogue(
                                "Narrator",
                                "Texto"
                        )
                ),
                List.of(
                        available,
                        blocked
                )
        );

        SceneDto dto = SceneMapper.toDto(
                scene,
                List.of(available),
                List.of(
                        new DialogueDto(
                                "Narrator",
                                "Texto"
                        )
                )
        );

        assertEquals(1, dto.numberDialogues());
        assertEquals(1, dto.numberChoices());
        assertEquals(
                List.of("Disponível"),
                dto.choices()
        );
    }

    @Test
    void achievementMapperShouldCopyDisplayData() {

        Achievement achievement =
                new Achievement(
                        "1",
                        "Conquista",
                        "Descrição",
                        state -> true
                );

        AchievementDto dto =
                AchievementMapper.toDto(achievement);

        assertEquals("Conquista", dto.name());
        assertEquals("Descrição", dto.description());
    }
}
