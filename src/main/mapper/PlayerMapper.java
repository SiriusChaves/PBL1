package main.mapper;

import main.dto.PlayerDto;
import main.model.Player;

public class PlayerMapper {
    public static PlayerDto toDto(Player player) {
        return new PlayerDto(
                player.getName(),
                player.getSanity(),
                player.getKnowledge());
    }
}
