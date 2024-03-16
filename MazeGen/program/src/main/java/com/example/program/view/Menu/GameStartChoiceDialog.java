package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameStartChoiceDialog extends VBox {
    private Label newGameLabel;
    private Label startFromCheckpointLabel;
    private MainProgram mainProgram;

    public GameStartChoiceDialog(MainProgram mainProgram) {
        this.mainProgram = mainProgram;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);

        // Set the background of the VBox (optional)
        setBackground(new Background(new BackgroundImage(
                new Image(getClass().getResource("/com/example/program/files/MenuBackground.jpg").toString(), 800, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        // Create Labels
        newGameLabel = new Label("New Game");
        startFromCheckpointLabel = new Label("Start from Checkpoint");

        // Style the labels to make them look clickable
        styleLabel(newGameLabel);
        styleLabel(startFromCheckpointLabel);

        // Add click event handlers
        newGameLabel.setOnMouseClicked(event -> handleNewGameAction());
        startFromCheckpointLabel.setOnMouseClicked(event -> handleStartFromCheckpointAction());

        this.getChildren().addAll(newGameLabel, startFromCheckpointLabel);
    }

    private void styleLabel(Label label) {
        // Load the "Press Start 2P" font
        Font pressStart2P = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 24);

        if (pressStart2P != null) {
            label.setFont(pressStart2P);
        } else {
            System.out.println("Font not loaded. Using default font.");
            label.setFont(new Font(24)); // Fallback font in case of loading failure
        }

        // Set the font color to #0000D6
        label.setTextFill(Color.web("#0000D6"));

        // Hover effect (adjust as needed)
        label.setOnMouseEntered(e -> label.setScaleX(1.2));
        label.setOnMouseExited(e -> label.setScaleX(1));
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
