package main.service;

import main.dao.ChapterDao;
import main.dao.ItemDao;
import main.dao.SaveDao;
import main.dto.*;
import main.exception.SaveSlotsFullException;
import main.exception.SlotOfSaveNotFoundException;
import main.mapper.ItemMapper;
import main.mapper.NpcMapper;
import main.model.Flag;
import main.model.Item;
import main.model.Npc;
import main.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveService {
    private SaveDao saveDao;
    private ItemDao itemDao;

    public SaveService(SaveDao saveDao, ItemDao itemDao) {
        this.saveDao = saveDao;
        this.itemDao = itemDao;
    }

    public boolean hasSaveDataAndSaveExists(String slotIndex) {
        return saveDao.saveFileExists(slotIndex) && saveDao.hasSaveData(slotIndex);
    }

    public GameSession restoreGameSessionFromPersistence(ChapterDao chapterDao, String slotIndex) {

        // Carregando dados persistidos
        SavePersistenceDto saveDto =  saveDao.findBySlot(slotIndex);

        GameStateDto gameState = saveDto.gameStateDto();

        // Carregando player
        Map<String, Npc> relationshipsOfPlayer = new HashMap<>();
        for (NpcDto npcDto : gameState.relationships()) {

            Npc npc = NpcMapper.toEntity(npcDto);

            relationshipsOfPlayer.put(npc.getName(), npc);
        }

        PlayerDto playerDto = gameState.player();

        Player player = new Player(
                playerDto.name(),
                playerDto.sanity(),
                playerDto.knowledge(),
                relationshipsOfPlayer);

        // Carregando GameFlagsActive

        Flag gameFlag = new Flag();
        for (String flag : gameState.activeGameFlags()) {
            gameFlag.addFlag(flag);
        }

        // Carregando Itens

        ItemPersistenceDto[] items =  itemDao.loadItems();
        List<String> itemIdOfPlayer = gameState.itemsId();

        for (ItemPersistenceDto itemDto : items) {

            if (itemIdOfPlayer.contains(itemDto.id())) {

                Item item = ItemMapper.toEntity(itemDto);

                player.storeItemInInventory(item);
            }
        }

        // Criando GameSession

        ChapterService chapterService = new ChapterService(chapterDao);

        StoryService storyService = new StoryService(player, gameFlag, chapterService, gameState.currentChapterId());

        return new GameSession(
                player,
                gameFlag,
                slotIndex,
                chapterService,
                storyService);
    }

    public void saveGame(SavePersistenceDto saveDto, String slotIndex) {
        saveDao.save(saveDto, slotIndex);
    }

    public void createNewSlotOfGameSession(String slotIndex, boolean overrideSave) throws SaveSlotsFullException {

        if (overrideSave) {

            deleteSlotSave(slotIndex);

            saveDao.createSlotOfSave(slotIndex);
        }

        if (saveDao.findAllSlots()) {

            throw new SaveSlotsFullException("Todos os saves estão preenchidos!");

        } else {

            saveDao.createSlotOfSave(slotIndex);
        }
    }

    public void deleteSlotSave(String slotIndex) throws SlotOfSaveNotFoundException {
        saveDao.delete(slotIndex);
    }

    public List<SavePersistenceDto> getDataOfAllSaves() {

        List<SavePersistenceDto> savesOfGameSession = new ArrayList<>();

        List<String> indexOfAllSlots = List.of("1", "2", "3");

        for (String index : indexOfAllSlots)
            savesOfGameSession.add(saveDao.findBySlot(index));

        return savesOfGameSession;

    }
}
