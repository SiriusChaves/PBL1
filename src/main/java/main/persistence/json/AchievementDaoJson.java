package main.persistence.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dto.AchievementDto;
import main.exception.AchievementsNotLoadException;
import main.exception.AchievementsNotSaveException;
import main.persistence.StoragePaths;
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

        Path path = StoragePaths.getAchievementsFile();

        if (Files.notExists(path)) {
            return new AchievementDto[0];
        }

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

        Path path = StoragePaths.getAchievementsFile();

        try {
            Files.createDirectories(path.getParent());

            try (Writer writer = Files.newBufferedWriter(path)) {
                GSON.toJson(achievements, writer);
            }

        } catch (IOException exception) {

            throw new AchievementsNotSaveException(
                    "Não foi possível salvar as conquistas do usuário"
            );
        }
    }


}
