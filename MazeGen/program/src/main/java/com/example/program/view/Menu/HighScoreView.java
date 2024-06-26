package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import com.example.program.model.HighScore;
import com.example.program.view.AudioPlayer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class HighScoreView extends VBox {
    private final MainProgram mainProgram;
    private final AudioPlayer audioPlayer;
    private String[] highScores;
    private static final String BASE_PATH = "/com/example/program/files/";
    HighScore highscore = new HighScore();


    public HighScoreView(MainProgram mainProgram, AudioPlayer audioPlayer, String[] scores) {
        this.getStyleClass().add("highScoreList");
        this.getStylesheets().add(getClass().getResource(BASE_PATH + "HighScoreStyle.css").toExternalForm());
        this.mainProgram = mainProgram;
        this.audioPlayer = audioPlayer;
        highScores = scores;
        setBackground();
        displayHighScores();
        addListener();
    }

    public void setHighScores(String[] highScores) {
        this.highScores = highScores;
    }

    /**
     * Metod för att visa topplistan
     */
    public void displayHighScores() {
        Label title = new Label("Leaderboard");
        title.getStyleClass().add("title-label");
        VBox.setMargin(title, new Insets(-120, 0, 0, 0));

        getChildren().add(title);
        // Skapa en label för varje highscore
        highScores = highscore.readList();
        for (String score : highScores) {
            Label label = new Label(score);
            label.getStyleClass().add("highscore-label");
            label.toFront();
            getChildren().add(label); // Lägg till label i HighScoreList
        }
    }


    /**
     * Metod som sätter bakgrundsbilden
     */
    public void setBackground() {
        BackgroundImage myBI = new BackgroundImage(new Image(getClass().getResource(BASE_PATH + "MenuBackgroundForHighscore.jpg").toString(), 800, 600, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(myBI));
    }

    /**
     * Lägger till listener för knapptryck och skiftar scen till menyn
     */
    public void addListener() {
        setOnMouseClicked(event -> {
            mainProgram.changeToMenu();
            audioPlayer.playButtonSound();
        });
    }
}
