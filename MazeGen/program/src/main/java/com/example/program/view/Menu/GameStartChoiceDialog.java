package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;

public class GameStartChoiceDialog extends VBox {
    private Button newGameButton;
    private Button startFromCheckpointButton;
    private MainProgram mainProgram;
    private final Image newGameImage = new Image(getClass().getResource("/com/example/program/files/texts/newGame.png").toString());
    private final Image checkpointImage = new Image(getClass().getResource("/com/example/program/files/texts/startFCheck.png").toString());

    public GameStartChoiceDialog(MainProgram mainProgram) {
        this.mainProgram = mainProgram;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        // Set the background of the VBox (optional)
        setBackground(new Background(new BackgroundImage(
                new Image(getClass().getResource("/com/example/program/files/MenuBackground.jpg").toString(), 800, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        // Create ImageView for buttons
        ImageView newGameView = new ImageView(newGameImage);
        ImageView checkpointView = new ImageView(checkpointImage);

        newGameButton = new Button("", newGameView); // Use ImageView as the label
        newGameButton.setStyle("-fx-background-color: transparent;");

        startFromCheckpointButton = new Button("", checkpointView); // Use ImageView as the label
        startFromCheckpointButton.setStyle("-fx-background-color: transparent;");

        newGameView.setPreserveRatio(true);
        checkpointView.setPreserveRatio(true);

// Hover effect for New Game Button
        newGameButton.setOnMouseEntered(e -> newGameView.setScaleX(1.1)); // Scales the image up by 10%
        newGameButton.setOnMouseEntered(e -> newGameView.setScaleY(1.1));
        newGameButton.setOnMouseExited(e -> newGameView.setScaleX(1.0)); // Resets the scale
        newGameButton.setOnMouseExited(e -> newGameView.setScaleY(1.0));

// Hover effect for Start From Checkpoint Button
        startFromCheckpointButton.setOnMouseEntered(e -> checkpointView.setScaleX(1.1)); // Scales the image up by 10%
        startFromCheckpointButton.setOnMouseEntered(e -> checkpointView.setScaleY(1.1));
        startFromCheckpointButton.setOnMouseExited(e -> checkpointView.setScaleX(1.0)); // Resets the scale
        startFromCheckpointButton.setOnMouseExited(e -> checkpointView.setScaleY(1.0));


        newGameButton.setOnAction(event -> handleNewGameAction());
        startFromCheckpointButton.setOnAction(event -> handleStartFromCheckpointAction());

        this.getChildren().addAll(newGameButton, startFromCheckpointButton);
    }

    private void handleNewGameAction() {
        mainProgram.changeCheckPoint(false);
        try {
            mainProgram.changeToCampaign();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleStartFromCheckpointAction() {
        mainProgram.changeCheckPoint(true);
        try {
            mainProgram.changeToCampaign();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}