package main.view.javafx.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controller.GameController;
import main.facade.GameFacade;
import main.view.javafx.controller.dependency.*;

import java.io.IOException;
import java.util.function.Consumer;

public class ScreenManager {

    private static final String VIEW_PATH = "/javafx/fxml/";

    private final Stage mainStage;
    private final GameController gameController;
    private final GameFacade gameFacade;
    private final BackgroundMusicManager musicManager;

    public ScreenManager(
            Stage primaryStage,
            GameController gameController,
            BackgroundMusicManager musicManager
    ) {

        this.mainStage = primaryStage;
        this.gameController = gameController;
        this.gameFacade = new GameFacade(gameController);
        this.musicManager = musicManager;

        mainStage.setTitle("MegaBrain");

        mainStage.setFullScreenExitHint("");

    }

    private void loadSceneFromFxmlFile(String fxmlFilename) {
        loadSceneFromFxmlFile(fxmlFilename, controller -> {});
    }

    private void loadSceneFromFxmlFile(String fxmlFilename, Consumer<Object> controllerSetup) {

        try {

            String fullpath = VIEW_PATH + fxmlFilename;
            var resource = getClass().getResource(fullpath);

            if (resource == null) {
                System.out.println("Erro ao carregar arquivo no caminho:\n" + fullpath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            var loaderController = loader.getController();

            if (loaderController instanceof GameControllerAware gameControllerAware) {
                gameControllerAware.setGameController(this.gameController);
            }

            if (loaderController instanceof ScreenController screenController) {
                screenController.setScreenManager(this);
            }

            if (loaderController instanceof GameFacadeAware gameFacadeAware) {
                gameFacadeAware.setGameFacade(this.gameFacade);
            }

            if (loaderController instanceof MusicManagerAware musicManagerAware) {
                musicManagerAware.setMusicManager(this.musicManager);
            }

            controllerSetup.accept(loaderController);

            Scene scene = new Scene(root);

            mainStage.setScene(scene);

            if (loaderController instanceof ScreenInitializer screenInitializer) {
                screenInitializer.initScreen();
            }

        } catch (IOException ioException) {
            System.out.println("Erro ao carregar arquivo " + fxmlFilename);
        }
    }

    public void showMainMenu() {
        loadSceneFromFxmlFile("main_menu.fxml");

        mainStage.show();

        mainStage.setFullScreen(true);
    }

    public void showPreferences() {
        loadSceneFromFxmlFile("preferences_screen.fxml");
    }

    public void showSaveScreen() {
        loadSceneFromFxmlFile("save_screen.fxml");
    }

    public void showCreditsScreen() {
        loadSceneFromFxmlFile("credits_screen.fxml");
    }

    public void showInstructionsScreen() {
        loadSceneFromFxmlFile("instructions_screen.fxml");
    }

    public void showGameOverScreen() {
        loadSceneFromFxmlFile("game_over_screen.fxml");
    }

    public void showEndingScreen() {
        loadSceneFromFxmlFile("final_game_screen.fxml");
    }

    public void showAchievementScreen() {
        loadSceneFromFxmlFile("achievements_screen.fxml");
    }

    public void showGameScreen(String slotNumber) {
        loadSceneFromFxmlFile("game_screen.fxml", controller -> {

            if (controller instanceof SlotAware slotAware) {
                slotAware.setSlotNumber(slotNumber);

            } else {
                throw new IllegalStateException(
                        "O controller da game_screen.fxml deve implementar SlotAware"
                );
            }
        });
    }
}
