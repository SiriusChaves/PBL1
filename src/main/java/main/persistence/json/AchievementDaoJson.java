package main.persistence.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dto.AchievementDto;
import main.exception.AchievementsNotLoadException;
import main.exception.AchievementsNotSaveException;
import main.persistence.dao.AchievementDao;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class AchievementDaoJson implements AchievementDao {

    private static final Gson GSON =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    @Override
    public AchievementDto[] loadAchievements() throws AchievementsNotLoadException {

        Path path = Path.of(
                "data", "achievements", "achievements.json"
        );

        try (Reader reader = Files.newBufferedReader(path)) {

            return GSON.fromJson(reader, AchievementDto[].class);

        } catch (IOException ioException) {

            throw new AchievementsNotLoadException(
                    "Não foi possível carregar as conquitas do jogo"
            );
        }
    }

    @Override
    public void save(AchievementDto[] achievements) throws AchievementsNotSaveException {

        Path path = Path.of(
                "data", "achievements", "achievements.json"
        );

        try (Writer writer = Files.newBufferedWriter(path)) {

            GSON.toJson(achievements, writer);

        } catch (Exception exception) {

            throw new AchievementsNotSaveException(
                    "Não foi possivel salvar as conquistas do usuário"
            );
        }
    }


}
