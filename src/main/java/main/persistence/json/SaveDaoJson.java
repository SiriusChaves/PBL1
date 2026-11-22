package main.persistence.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.persistence.StoragePaths;
import main.persistence.dto.SavePersistenceDto;
import main.exception.FileOfSaveNotCreateException;
import main.exception.GameNotSaveException;
import main.exception.SlotOfSaveNotFoundException;
import main.persistence.dao.SaveDao;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SaveDaoJson implements SaveDao {

    private static final Gson GSON =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    @Override
    public void createSlotOfSave(String slotIndex) throws FileOfSaveNotCreateException {

        Path path = StoragePaths.getSaveFile(slotIndex);


        try {
            if (Files.exists(path)) {
                throw new FileOfSaveNotCreateException(
                        "O save com slot de número " + slotIndex + " já existe"
                );
            }

            Files.createDirectories(path.getParent());

            Files.createFile(path);

        } catch (IOException ioException) {

            throw new FileOfSaveNotCreateException(
                    "Não existe save válido com número de slot igual a " + slotIndex
            );
        }
    }

    @Override
    public void save(SavePersistenceDto savePersistenceDto, String slotIndex)
            throws GameNotSaveException {

        Path path = StoragePaths.getSaveFile(slotIndex);
        try {
            Files.createDirectories(path.getParent());

            try (Writer writer = Files.newBufferedWriter(path)) {

                GSON.toJson(savePersistenceDto, writer);
            }

        } catch (IOException ioException) {

            throw new GameNotSaveException(
                    "Erro ao salvar o jogo no slot" + slotIndex
            );
        }
    }

    @Override
    public void delete(String slotIndex) throws SlotOfSaveNotFoundException {

        Path path = StoragePaths.getSaveFile(slotIndex);


       try {
           Files.deleteIfExists(path);

       } catch (IOException ioException) {

           throw new SlotOfSaveNotFoundException(
                   "Não encontrou o slot" + slotIndex + " para deletar"
           );
       }
    }


    @Override
    public SavePersistenceDto findBySlot(String slotIndex) throws SlotOfSaveNotFoundException {

        if (!saveFileExists(slotIndex))
            return null;

        Path path = StoragePaths.getSaveFile(slotIndex);


        try (Reader reader = Files.newBufferedReader(path)) {

            return GSON.fromJson(reader, SavePersistenceDto.class);

        } catch (IOException ioException) {

            throw new SlotOfSaveNotFoundException(
                    "Erro ao buscar o slot" + slotIndex + " para carregamento"
            );
        }
    }

    @Override
    public boolean saveFileExists(String slotIndex) {

        Path path = StoragePaths.getSaveFile(slotIndex);


            return (Files.exists(path));
    }

    @Override
    public boolean hasSaveData(String slotIndex) {

        if (!saveFileExists(slotIndex)) {

            return false;

        } else {

            try {

                Path path = StoragePaths.getSaveFile(slotIndex);


                return Files.size(path) > 0;

            } catch (IOException ioException) {

                throw new RuntimeException(
                        "Erro ao tentar verificar o tamanho do path do slot " + slotIndex
                );
            }
        }
    }

}
