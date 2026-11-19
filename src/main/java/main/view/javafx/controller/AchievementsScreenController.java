package main.view.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.controller.GameController;
import main.view.javafx.controller.dependency.GameControllerAware;
import main.view.javafx.controller.dependency.ScreenController;
import main.view.javafx.controller.dependency.ScreenInitializer;
import main.view.javafx.manager.ScreenManager;

import java.net.URL;
import java.util.List;

public class AchievementsScreenController implements
        ScreenController,
        GameControllerAware,
        ScreenInitializer {

    private ScreenManager screenManager;

    private GameController gameController;

    private Image achievemtImage;

    @FXML
    private VBox achievementsContainer;

    @FXML
    private ScrollPane achievementsScrollPane;

    @FXML
    private StackPane contentContainer;

    @FXML
    private Label emptyStateLabel;

    @FXML
    private void initialize() {
        URL imageURL = getClass().getResource(
                        "/javafx/images/icons/achievement.png"
                );

        if (imageURL == null) {
            throw new IllegalStateException(
                    "Imagem das conquistas não encontrada!"
            );
        }

        achievemtImage = new Image(imageURL.toExternalForm());
    }

    @FXML
    void handleBackToMainMenu(ActionEvent event) {
        screenManager.showMainMenu();
    }

    private HBox createAchievementItem(String achievementText) {

        ImageView imageView = new ImageView(achievemtImage);

        imageView.setFitHeight(64);
        imageView.setFitWidth(64);
        imageView.setPreserveRatio(true);

        Label achievementLabel = new Label(achievementText);

        achievementLabel.setWrapText(true);

        achievementLabel.setMaxWidth(Double.MAX_VALUE);

        achievementLabel.setFont(Font.font("Arial", 25));

        HBox.setHgrow(achievementLabel, Priority.ALWAYS);

        HBox item = new HBox(18, imageView, achievementLabel);

        item.setAlignment(Pos.CENTER_LEFT);

        item.setPadding(new Insets(14));

        item.setMaxWidth(Double.MAX_VALUE);

        return item;
    }

    private void displayAchievements(List<String> achievements) {

        achievementsContainer.getChildren().clear();

        boolean hasAchievements = achievements != null && !achievements.isEmpty();

        achievementsScrollPane.setVisible(hasAchievements);
        achievementsScrollPane.setManaged(hasAchievements);

        emptyStateLabel.setVisible(!hasAchievements);
        emptyStateLabel.setManaged(!hasAchievements);

        if (!hasAchievements) {
            return;
        }

        for (String achievement :achievements) {

            HBox item = createAchievementItem(achievement);

            achievementsContainer.getChildren().add(item);
        }
    }

    @Override
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void initScreen() {

        List<String> achievements = gameController.loadAchievements()
                .stream()
                .map(achievement -> achievement.name() + " - " + achievement.description())
                .toList();

        displayAchievements(achievements);
    }
}
