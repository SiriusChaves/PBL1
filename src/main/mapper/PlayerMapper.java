package main.mapper;

import main.dto.PlayerDtoRecord;
import main.model.Player;

public class PlayerMapper {
    public static PlayerDtoRecord toDto(Player player) {
        return new PlayerDtoRecord(
                player.getName(),
                player.getSanity(),
                player.getKnowledge());
    }
}
