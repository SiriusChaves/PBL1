package main.view.javafx.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.view.javafx.controller.SceneController;

import java.io.IOException;

public class SceneManager {
    private static final String VIEW_PATH = "/javafx/fxml/";

    private Stage mainStage;

    public SceneManager(Stage primaryStage) {
        this.mainStage = primaryStage;
        primaryStage.setTitle("MegaBrainApplication");
    }

    private void loadScene(String fxmlfilename) {
        try {
            String fullpath = VIEW_PATH + fxmlfilename;
            var resource = getClass().getResource(fullpath);

            if (resource == null) {
                System.out.println("Erro ao carregar arquivo no caminho:\n" + fullpath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            if (loader.getController() instanceof SceneController sceneController) {
                sceneController.setSceneManager(this);
            }

            Scene scene = new Scene(root);

            mainStage.setScene(scene);

        } catch (IOException ioException) {
            System.out.println("Erro ao carregar arquivo " + fxmlfilename);
            return;
        }
    }

    public void showMainMenu() {
        String mainMenuFxmlfilename = "main_menu.fxml";

        loadScene(mainMenuFxmlfilename);

        mainStage.show();
    }

    public void showPreferences() {
        String preferencesFxmlfilename = "preferences_screen.fxml";
        loadScene(preferencesFxmlfilename);
    }
}
