package main.view.javafx.manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Objects;

public class BackgroundMusicManager {

    private final MediaPlayer player;
    private boolean enabled;

    public BackgroundMusicManager(String musicPath) {
        URL resource = Objects.requireNonNull(
                getClass().getResource(musicPath),
                "Arquivo de música não encontrado " + musicPath
        );

        Media media = new Media(resource.toExternalForm());

        player = new MediaPlayer(media);

        player.setCycleCount(MediaPlayer.INDEFINITE);

        player.setVolume(0.25);

        player.setOnReady(() -> {
            if (enabled) {
                player.play();
            }
        });

        player.setOnError(() -> System.err.println("Erro ao reproduzir música " + player.getError()));
    }

    public void setEnabled(boolean enabled) {

        if (this.enabled == enabled) {
            return;
        }

        this.enabled = enabled;

        if (enabled) {

            player.play();
        } else if (
                player.getStatus() == MediaPlayer.Status.PLAYING ||
                player.getStatus() == MediaPlayer.Status.STALLED
        ) {

            player.pause();
        }
    }

    public void close() {
        player.stop();
        player.dispose();
    }
}
