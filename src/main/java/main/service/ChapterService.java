package main.service;

import main.dao.ChapterDao;
import main.dto.GameStateDto;
import main.dto.PlayerDto;
import main.exception.ChapterNotFoundException;
import main.model.*;

import java.io.IOException;
import java.util.List;

public class ChapterService {

    private final ChapterDao chapterDao;

    public ChapterService(ChapterDao chapterDao) {
        this.chapterDao = chapterDao;
    }

    public Chapter loadNextChapter(GameStateDto gameState) throws ChapterNotFoundException {

        final boolean playerIsArchitect = gameState.player().name().equals("O Arquiteto");
        final boolean playerIsStudent = gameState.player().name().equals("O Estudante");

        return switch (gameState.nextChapterId()) {

            case "-1" -> buildIntroduceChapter();

            case "0" -> buildChapterZero();

            case "1" -> buildChapterOne(gameState.player().name());

            case String id when id.equals("2") && playerIsArchitect -> buildChapter2A();

            case String id when id.equals("3") && playerIsArchitect -> buildChapter3A();

            case String id when id.equals("4") && playerIsArchitect -> buildChapter4A(gameState.activeGameFlags());

            case String id when id.equals("2") && playerIsStudent -> buildChapter2B();

            case String id when id.equals("3") && playerIsStudent -> buildChapter3B();

            case String id when id.equals("4") && playerIsStudent -> buildChapter4B();

            case "5" -> buildChapter5(gameState.player(), gameState.activeGameFlags());

            case "6" -> buildChapter6(gameState.player());

            case "7" -> buildChapter7(gameState.player(), gameState.activeGameFlags(), gameState.itemsId());

            case "8" -> buildArchitectEndingChapter();

            case "9" -> buildStudentEndingChapter();

            case "10" -> buildFinalMegaBrainChapter();

            default -> throw new ChapterNotFoundException("Não foi possível carregar o próximo capitulo!");
        };
    }

    private Chapter buildIntroduceChapter() throws ChapterNotFoundException {
        try {
            return chapterDao.searchById("-1");
        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar capitulo introdutório");
        }
    }

    private Chapter buildChapterZero() throws ChapterNotFoundException {
       try {
           return chapterDao.searchById("0");
       } catch (ChapterNotFoundException chapterNotFoundException) {
           throw new ChapterNotFoundException("Erro ao carregar capitulo 0");
       }
    }

    private Chapter buildChapterOne(String playerName) throws ChapterNotFoundException {
        try {
            Chapter chapterOne = chapterDao.searchById("1");
            Chapter chapterOneModified = new Chapter(chapterOne);

            for (Scene scene : chapterOneModified.getScenes()) {
                for (Dialogue dialogue : scene.getDialogues()) {
                    if (dialogue.getText().equals("Isso ainda funciona?")) dialogue.setSpeaker(playerName);
                    if (dialogue.getText().equals("E o que você quer em troca?")) dialogue.setSpeaker(playerName);
                }
            }
            return chapterOneModified;

        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar o capitulo 1");
        }
    }

    // Trilha A - O arquiteto
    private Chapter buildChapter2A() throws ChapterNotFoundException {
        try {
            return chapterDao.searchById("2A");
        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar capitulo 2A");
        }
    }

    private Chapter buildChapter3A() throws ChapterNotFoundException {
        try {
            return chapterDao.searchById("3A");
        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar capitulo 3A");
        }
    }

    private Chapter buildChapter4A(List<String> activeGameFlags) throws ChapterNotFoundException {
        try {
            Chapter originalChapter = chapterDao.searchById("4A");
            Chapter copyChapter = new Chapter(originalChapter);

            String requireFlag1 = "Aceitou ajuda do Estudante";
            String requireFlag2 = "Não colaborou no cap 3A";

            if (!activeGameFlags.contains(requireFlag1) && !activeGameFlags.contains(requireFlag2))
                copyChapter.getScenes().remove(1);

            return copyChapter;

        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar o capitulo 4A");
        }
    }

    // Trilha B - O Estudante
    private Chapter buildChapter2B() throws ChapterNotFoundException {
        try {
            return chapterDao.searchById("2B");
        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar capitulo 2B");
        }
    }

    private Chapter buildChapter3B() throws ChapterNotFoundException {
        try {
            return chapterDao.searchById("3B");
        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar capitulo 3B");
        }
    }

    private Chapter buildChapter4B() throws ChapterNotFoundException {
        try {
            return chapterDao.searchById("4B");
        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar capitulo 4B");
        }
    }

    // Capitulos compartilhados
    private Chapter buildChapter5(PlayerDto player, List<String> activeGameFlags) throws ChapterNotFoundException {
       try {
           Chapter originalChapter = chapterDao.searchById("5");

           Chapter copyChapter = new Chapter(originalChapter);

           if (player.name().equals("O Estudante")) {
               copyChapter.getScenes().remove(3);
           } else if (player.name().equals("O Arquiteto") && activeGameFlags.contains("Spyware desbloqueado")) {
               copyChapter.getScenes().remove(2);
           } else {
               copyChapter.getScenes().remove(3);
               copyChapter.getScenes().remove(2);
           }

           return copyChapter;

       } catch (ChapterNotFoundException chapterNotFoundExceptionption) {
           throw new ChapterNotFoundException("Erro ao carregar o capitulo 5");
       }
    }

    private Chapter buildChapter6(PlayerDto player) throws ChapterNotFoundException {
        try {
            Chapter originalChapter = chapterDao.searchById("6");

            Chapter copyChapter = new Chapter(originalChapter);

            if (player.name().equals("O Arquiteto")) {
                copyChapter.getScenes().remove(1);
            } else if (player.name().equals("O Estudante")) {
                copyChapter.getScenes().removeFirst();
            } else {
                copyChapter.getScenes().remove(1);
                copyChapter.getScenes().remove(0);
            }

            return copyChapter;

        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar o capitulo 6");
        }
    }

    private Chapter buildChapter7(PlayerDto player, List<String> activeGameFlags, List<String> items)
            throws ChapterNotFoundException {
        try {
            Chapter originalChapter = chapterDao.searchById("7");

            Chapter copyChapter = new Chapter(originalChapter);

            String idChapterFinal1 = "8";
            String idChapterFinal2 = "9";
            String idChapterFinal3 = "10";

            String nextChapterId;

            if (player.name().equals("O Arquiteto") && items.contains("7")) {
                nextChapterId = idChapterFinal1;
            } else if (player.name().equals("O Estudante") && items.contains("13B")) {
                nextChapterId = idChapterFinal2;
            } else {
                nextChapterId = idChapterFinal3;
            }

            copyChapter.setIdNextChapter(nextChapterId);

            return copyChapter;

        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar chapter 7");
        }
    }

    // Capitulos finais
    private Chapter buildArchitectEndingChapter() throws ChapterNotFoundException {
        try {
            return chapterDao.searchById("8");
        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar capitulo final do arquiteto");
        }
    }

    private Chapter buildStudentEndingChapter() throws ChapterNotFoundException {
        try {
            return chapterDao.searchById("9");
        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar capitulo final do estudante");
        }
    }

    private Chapter buildFinalMegaBrainChapter() throws ChapterNotFoundException {
        try {
            return chapterDao.searchById("10");
        } catch (ChapterNotFoundException chapterNotFoundException) {
            throw new ChapterNotFoundException("Erro ao carregar capitulo final mega brain");
        }
    }
}