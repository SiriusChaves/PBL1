package main.persistence.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    @Override
    public void createSlotOfSave(String slotIndex) throws FileOfSaveNotCreateException {

        Path path = Path.of(
                "data",
                "saves",
                "slot" + slotIndex,
                "slot" + slotIndex + ".json"
        );

        try {
            if (Files.exists(path)) {
                throw new FileOfSaveNotCreateException(
                        "O save com slot de número " + slotIndex + " já existe"
                );
            }

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

        Path path = Path.of(
                "data",
                "saves",
                "slot" + slotIndex,
                "slot" + slotIndex + ".json"
        );

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (Writer writer = Files.newBufferedWriter(path)) {

            gson.toJson(savePersistenceDto, writer);

        } catch (IOException ioException) {

            throw new GameNotSaveException(
                    "Erro ao salvar o jogo no slot" + slotIndex
            );
        }
    }

    @Override
    public void delete(String slotIndex) throws SlotOfSaveNotFoundException {

        Path path = Path.of(
                "data",
                "saves",
                "slot" + slotIndex,
                "slot" + slotIndex + ".json"
        );

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

        Path path = Path.of(
                "data",
                "saves",
                "slot" + slotIndex,
                "slot" + slotIndex + ".json"
        );

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (Reader reader = Files.newBufferedReader(path)) {

            return gson.fromJson(reader, SavePersistenceDto.class);

        } catch (IOException ioException) {

            throw new SlotOfSaveNotFoundException(
                    "Erro ao buscar o slot" + slotIndex + " para carregamento"
            );
        }
    }

    @Override
    public boolean saveFileExists(String slotIndex) {

        Path path = Path.of(
                "data",
                "saves",
                "slot" + slotIndex,
                "slot" + slotIndex + ".json"
        );

            return (Files.exists(path));
    }

    @Override
    public boolean hasSaveData(String slotIndex) {

        if (!saveFileExists(slotIndex)) {

            return false;

        } else {

            try {

                Path path = Path.of(
                        "data",
                        "saves",
                        "slot" + slotIndex,
                        "slot" + slotIndex + ".json"
                );

                return Files.size(path) > 0;

            } catch (IOException ioException) {

                throw new RuntimeException(
                        "Erro ao tentar verificar o tamanho do path do slot " + slotIndex
                );
            }
        }
    }

}
