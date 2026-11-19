package main.view.javafx.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controller.GameController;
import main.view.javafx.controller.dependency.GameControllerAware;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.controller.dependency.ScreenInitializer;
import main.view.javafx.controller.dependency.SlotAware;

import java.io.IOException;

public class ScreenManager {
    private static final String VIEW_PATH = "/javafx/fxml/";

    private final Stage mainStage;
    private final GameController gameController;

    public ScreenManager(Stage primaryStage, GameController gameController) {

        this.mainStage = primaryStage;
        this.gameController = gameController;

        mainStage.setTitle("MegaBrain");

        mainStage.setFullScreenExitHint("");

    }

    private void loadSceneFromFxmlFile(String fxmlfilename) {

        try {

            String fullpath = VIEW_PATH + fxmlfilename;
            var resource = getClass().getResource(fullpath);

            if (resource == null) {
                System.out.println("Erro ao carregar arquivo no caminho:\n" + fullpath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            if (loader.getController() instanceof GameControllerAware gameControllerAware) {
                gameControllerAware.setGameController(this.gameController);
            }

            if (loader.getController() instanceof ScreenController screenController) {
                screenController.setScreenManager(this);
            }

            Scene scene = new Scene(root);

            mainStage.setScene(scene);

            if (loader.getController() instanceof ScreenInitializer screenInitializer) {
                screenInitializer.initScreen();
            }

        } catch (IOException ioException) {
            System.out.println("Erro ao carregar arquivo " + fxmlfilename);
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

        String gameScreenFxmlFilename = "game_screen.fxml";

        try {

            String fullpath = VIEW_PATH + gameScreenFxmlFilename;
            var resource = getClass().getResource(fullpath);

            if (resource == null) {
                System.out.println("Erro ao carregar arquivo no caminho:\n" + fullpath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            if (loader.getController() instanceof GameControllerAware gameControllerAware) {
                gameControllerAware.setGameController(this.gameController);
            }

            if (loader.getController() instanceof ScreenController screenController) {
                screenController.setScreenManager(this);
            }

            if (loader.getController() instanceof SlotAware slotAware) {
                slotAware.setSlotNumber(slotNumber);
            }

            Scene scene = new Scene(root);

            mainStage.setScene(scene);

            if (loader.getController() instanceof ScreenInitializer screenInitializer) {
                screenInitializer.initScreen();
            }



        } catch (IOException ioException) {

            System.out.println("Erro ao carregar arquivo " + gameScreenFxmlFilename);
        }
    }
}
