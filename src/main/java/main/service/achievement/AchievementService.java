package main.service.achievement;

import main.model.achievement.AchievementUnlockStrategy;
import main.persistence.dao.AchievementDao;
import main.dto.AchievementDto;
import main.dto.GameStateDto;
import main.mapper.AchievementMapper;
import main.model.achievement.Achievement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AchievementService {
    private final AchievementDao achievementDao;
    private final List<Achievement> achievements;

    public AchievementService(AchievementDao achievementDao) {
        this.achievementDao = achievementDao;
        this.achievements = loadAchievements();
    }

    public void saveUnlockedAchievements(GameStateDto gameState) {

        AchievementDto[] unlockedAchievements = achievements.stream()
                .filter(achievement -> achievement.isUnlocked(gameState))
                .map(AchievementMapper::toDto)
                .toArray(AchievementDto[]::new);

        AchievementDto[] achievementFromDao =
                achievementDao.loadAchievements();

        List<AchievementDto> achievementDtoList = new ArrayList<>(
                achievementFromDao == null
                        ? List.of()
                        : Arrays.asList(achievementFromDao)
        );

        for (var unlockedAchievement : unlockedAchievements)
            if (!achievementDtoList.contains(unlockedAchievement))
                achievementDtoList.add(unlockedAchievement);

        AchievementDto[] achievementsDto = achievementDtoList.toArray(AchievementDto[]::new);

        achievementDao.save(achievementsDto);
    }

    public List<AchievementDto> loadAchievementsDto() {
        AchievementDto[] achievementsFromSave =
                achievementDao.loadAchievements();

        return Arrays.stream(achievementsFromSave).toList();
    }

    private List<Achievement> loadAchievements() {

        AchievementUnlockStrategy final3Strategy = gameState ->
                gameState.isGameCompleted() &&
                gameState.currentChapterId().equals("10");

        AchievementUnlockStrategy final2Strategy = gameState ->
                gameState.isGameCompleted() &&
                gameState.currentChapterId().equals("9");

        AchievementUnlockStrategy final1Strategy = gameState ->
                gameState.isGameCompleted() &&
                gameState.currentChapterId().equals("8");

        AchievementUnlockStrategy failedStrategy = gameState -> gameState.player().sanity() <= 0;

        AchievementUnlockStrategy bestFriendsStrategy = gameState -> gameState.relationships()
                .stream()
                .allMatch(npc -> npc.trustLevelTier().equals("ALIADO PLENO"));

        Achievement unlockedFinal3 = new Achievement(
                "1",
                "FINAL 3: A ASCENSÃO DO MEGA BRAIN",
                "Você concluiu o Final 3: o MegaBrain virtualizou todo o mundo",
                final3Strategy

        );

        Achievement unlockedFinal2 = new Achievement(
                "2",
                "FINAL 2: A ERA DO SILÊNCIO",
                "Você concluiu a campanha do Estudante  e impediu os avanços do MegaBrain",
                final2Strategy
        );

        Achievement unlockedFinal1 = new Achievement(
                "3",
                "FINAL 1: A NOVA ORDEM MONDIAL",
                "Você concluiu a campanha do Arquiteto e estabeleceu uma nova Ordem Mundial movida pelo MegaBrain",
                final1Strategy

        );

        Achievement failedMission = new Achievement(
                "5",
                "!!!GAME OVER!!!",
                "Você chegou a 0 de sanidade mental e falhou na sua missão",
                failedStrategy
        );

        Achievement bestFriends = new Achievement(
                "4",
                "AMIGO DA VIZINHANÇA",
                "Você obteve o nível máximo de amizade com todos os NPCs",
                bestFriendsStrategy

        );

        List<Achievement> achievementsOriginal = List.of(
                unlockedFinal1,
                unlockedFinal2,
                unlockedFinal3,
                failedMission,
                bestFriends
        );

        return new ArrayList<>(achievementsOriginal);
    }
}
