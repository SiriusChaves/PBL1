
package main.view.javafx.util;

import javafx.scene.image.Image;
import main.dto.DialogueDto;
import main.dto.SceneDto;

import java.net.URL;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CharacterSpriteResolver {

    private static final String RESOURCE_PATH =
            "/javafx/images/characters/";

    private static final String UNKNOWN_SPRITE = "unknow";

    private static final Map<String, String> SPRITES_BY_NAME = Map.ofEntries(
            Map.entry("o estudante", "student"),
            Map.entry("estudante", "student"),
            Map.entry("student", "student"),

            Map.entry("o arquiteto", "architect"),
            Map.entry("arquiteto", "architect"),
            Map.entry("architect", "architect"),

            Map.entry("gabriel", "gabriel"),
            Map.entry("lia", "lia"),
            Map.entry("rebeca", "rebeca"),
            Map.entry("silas", "silas"),
            Map.entry("eng. silas", "silas")
    );


    private final Map<String, Image> imageCache = new HashMap<>();

    public record ResolvedCharacter(String name, Image image) {}

    public ResolvedCharacter resolveProtagonist(String playerName) {

        if (playerName == null || playerName.isBlank()) {
            return new ResolvedCharacter(
                    "Desconhecido",
                    loadImage(UNKNOWN_SPRITE)
            );
        }

        String spriteName = getSpriteName(playerName);

        return new ResolvedCharacter(
                playerName,
                loadImage(spriteName)
        );
    }

    public ResolvedCharacter resolveSceneCharacter(
            SceneDto scene,
            String protagonistName
    ) {

        if (scene == null || scene.dialogues() == null) {
            return unknownCharacter();
        }

        String normalizedProtagonist = normalize(protagonistName);
        String protagonistSprite = getSpriteName(protagonistName);

        boolean narratorFound = false;

        for (DialogueDto dialogue : scene.dialogues()) {

            if (dialogue == null ||
                    dialogue.speaker() == null ||
                    dialogue.speaker().isBlank()) {
                continue;
            }

            String speaker = dialogue.speaker();
            String normalizedSpeaker = normalize(speaker);

            if (normalizedSpeaker.equals("narrator") ||
                    normalizedSpeaker.equals("narrador")) {

                narratorFound = true;
                continue;
            }

            String speakerSprite = getSpriteName(speaker);

            boolean isProtagonist =
                    normalizedSpeaker.equals(normalizedProtagonist)
                            || speakerSprite.equals(protagonistSprite);

            if (isProtagonist) {
                continue;
            }

            return new ResolvedCharacter(
                    speaker,
                    loadImage(speakerSprite)
            );
        }

        if (narratorFound) {
            return new ResolvedCharacter(
                    "Narrador",
                    loadImage(UNKNOWN_SPRITE)
            );
        }

        return unknownCharacter();
    }

    private String getSpriteName(String characterName) {

        String normalizedName = normalize(characterName);

        return SPRITES_BY_NAME.getOrDefault(
                normalizedName,
                UNKNOWN_SPRITE
        );
    }

    private String normalize(String value) {

        if (value == null) {
            return "";
        }

        return Normalizer.normalize(
                        value.strip().toLowerCase(Locale.ROOT),
                        Normalizer.Form.NFD
                )
                .replaceAll("\\p{M}+", "")
                .replaceAll("\\s+", " ");
    }

    private ResolvedCharacter unknownCharacter() {

        return new ResolvedCharacter(
                "Desconhecido",
                loadImage(UNKNOWN_SPRITE)
        );
    }

    private Image loadImage(String spriteName) {

        return imageCache.computeIfAbsent(spriteName, name -> {

            String path = RESOURCE_PATH + name + ".png";

            URL resource = getClass().getResource(path);

            if (resource == null) {
                throw new IllegalStateException(
                        "Sprite não encontrado: " + path
                );
            }

            Image image = new Image(
                    resource.toExternalForm(),
                    false
            );

            if (image.isError()) {
                throw new IllegalStateException(
                        "Erro ao carregar sprite: " + path,
                        image.getException()
                );
            }

            return image;
        });
    }
}
