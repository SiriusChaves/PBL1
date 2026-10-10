package main.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.exception.UserProfileErrorException;
import main.model.UserProfile;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class UserProfileDaoJson implements UserProfileDao {

    @Override
    public void save(UserProfile userProfile) throws UserProfileErrorException {

        Path path = Path.of("data", "profile", "profile.json");

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (Writer writer = Files.newBufferedWriter(path)) {

            gson.toJson(userProfile, writer);

        } catch (IOException ioException) {

            throw new UserProfileErrorException("Erro ao salvar o UserProfile no json");
        }
    }

    @Override
    public UserProfile loadUserProfile() throws UserProfileErrorException {
        Path path = Path.of("data", "profile", "profile.json");

        Gson gson = new Gson();

        try (Reader reader = Files.newBufferedReader(path)) {

            return gson.fromJson(reader, UserProfile.class);

        } catch (IOException ioException) {

            throw new UserProfileErrorException("Erro ao carregar usuário do Json");
        }
    }
}
