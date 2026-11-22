package main.persistence.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import main.exception.UserProfileErrorException;
import main.model.profile.UserProfile;
import main.persistence.StoragePaths;
import main.persistence.dao.UserProfileDao;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class UserProfileDaoJson implements UserProfileDao {

    private static final Gson GSON =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    @Override
    public void save(UserProfile userProfile) throws UserProfileErrorException {

        Path path = StoragePaths.getProfileFile();

        try {

            Files.createDirectories(path.getParent());

            try (Writer writer = Files.newBufferedWriter(path)) {

                GSON.toJson(userProfile, writer);

            }

        } catch (IOException ioException) {

            throw new UserProfileErrorException(
                    "Erro ao salvar o UserProfile no json"
            );
        }
    }

    @Override
    public UserProfile loadUserProfile() {

        Path path = StoragePaths.getProfileFile();

        try {
            Files.createDirectories(path.getParent());

            if (Files.notExists(path)) {
                UserProfile defaultProfile = new UserProfile();
                save(defaultProfile);
                return defaultProfile;
            }

            try (Reader reader = Files.newBufferedReader(path)) {
                UserProfile profile = GSON.fromJson(reader, UserProfile.class);

                if (profile == null) {
                    throw new IOException("Arquivo de perfil vazio.");
                }

                return profile;
            }

        } catch (IOException | JsonParseException exception) {
            throw new UserProfileErrorException(
                    "Erro ao carregar o perfil do usuário."
            );
        }
    }
}
