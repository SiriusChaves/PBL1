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
        primaryStage.setTitle("MegaBrainApplication");
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

            if (loader.getController() instanceof ScreenInitializer screenInitializer) {
                screenInitializer.initScreen();
            }


            Scene scene = new Scene(root);

            mainStage.setScene(scene);

        } catch (IOException ioException) {
            System.out.println("Erro ao carregar arquivo " + fxmlfilename);
        }
    }

    public void showMainMenu() {
        String mainMenuFxmlFilename = "main_menu.fxml";

        loadSceneFromFxmlFile(mainMenuFxmlFilename);

        mainStage.show();
    }

    public void showPreferences() {
        String preferencesFxmlFilename = "preferences_screen.fxml";
        loadSceneFromFxmlFile(preferencesFxmlFilename);
    }

    public void showSaveScreen() {
        String saveScreenFxmlFilename = "save_screen.fxml";
        loadSceneFromFxmlFile(saveScreenFxmlFilename);
    }

    public void showCreditsScreen() {
        String creditsScreenFxmlFilename = "credits_screen.fxml";
        loadSceneFromFxmlFile(creditsScreenFxmlFilename);
    }

    public void showInstructionsScreen() {
        String instructionsScreenFxmlFilename = "instructions_screen.fxml";
        loadSceneFromFxmlFile(instructionsScreenFxmlFilename);
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

            if (loader.getController() instanceof ScreenInitializer screenInitializer) {
                screenInitializer.initScreen();
            }

            Scene scene = new Scene(root);

            mainStage.setScene(scene);

        } catch (IOException ioException) {
            System.out.println("Erro ao carregar arquivo " + gameScreenFxmlFilename);
        }
    }
}
