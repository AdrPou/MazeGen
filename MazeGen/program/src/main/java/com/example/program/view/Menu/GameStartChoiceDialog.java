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
        this.setSpacing(10);

        // Set the background of the VBox (optional)
        setBackground(new Background(new BackgroundImage(
                new Image(getClass().getResource("/com/example/program/files/MenuBackground.jpg").toString(), 800, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        // Create ImageView for buttons
        ImageView newGameView = new ImageView(newGameImage);
        ImageView checkpointView = new ImageView(checkpointImage);

        newGameButton = new Button("", newGameView); // Use ImageView as the label
        startFromCheckpointButton = new Button("", checkpointView); // Use ImageView as the label

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
