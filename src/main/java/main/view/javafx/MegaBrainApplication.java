package main.view.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MegaBrainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Label label = new Label("MEGABRAIN");
        Button button = new Button("mega click");

        button.setOnAction(event -> {
            new Alert(Alert.AlertType.INFORMATION, "Megabrain com UI")
                    .showAndWait();
        });

        VBox vBox = new VBox();
        vBox.getChildren().add(label);
        vBox.getChildren().add(button);

        Scene scene = new Scene(vBox, 900, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MegaBrainApllication");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
