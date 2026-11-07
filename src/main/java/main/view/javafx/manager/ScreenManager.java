package main.view.javafx.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.view.javafx.controller.ScreenController;

import java.io.IOException;

public class ScreenManager {
    private static final String VIEW_PATH = "/javafx/fxml/";

    private Stage mainStage;

    public ScreenManager(Stage primaryStage) {
        this.mainStage = primaryStage;
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

            if (loader.getController() instanceof ScreenController screenController) {
                screenController.setScreenManager(this);
            }

            Scene scene = new Scene(root);

            mainStage.setScene(scene);

        } catch (IOException ioException) {
            System.out.println("Erro ao carregar arquivo " + fxmlfilename);
            return;
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

    public void showGameScreen() {
        String gameScreenFxmlFilename = "game_screen.fxml";
        loadSceneFromFxmlFile(gameScreenFxmlFilename);
    }
}
