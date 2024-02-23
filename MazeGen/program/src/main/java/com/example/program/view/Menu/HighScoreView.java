package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import com.example.program.view.AudioPlayer;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;


import java.io.*;
import java.util.Scanner;

public class HighScoreView extends VBox {
    private MainProgram mainProgram;
    private Image pressMouse;
    private AudioPlayer audioPlayer;
    private String[] highScores;
    private static final String BASE_PATH = "/com/example/program/files/";



    public HighScoreView(MainProgram mainProgram, AudioPlayer audioPlayer, String[] scores) {
        this.getStyleClass().add("highScoreList");
        this.getStylesheets().add(getClass().getResource(BASE_PATH + "HighScoreStyle.css").toExternalForm());
        this.mainProgram = mainProgram;
        this.audioPlayer = audioPlayer;
        pressMouse = new Image(getClass().getResource(BASE_PATH + "menuImages/helppicmouse.png").toString());
        highScores = scores;

        TextArea textArea = new TextArea();
        setBackground();
        pressMouseAnimation();
        displayHighScores(textArea);
        addListener();
    }

    public void displayHighScores(TextArea textArea) {
        // Rensa tidigare innehåll från TextArea
       // textArea.clear();
        //Label label = new Label();

        textArea.setText("Highscores \n");
        // Lägg till texten till TextArea
        for (String score : highScores) {
            textArea.appendText(score + "\n");
            System.out.println(score);
           // label = new Label(score);
        }

        textArea.setStyle("-fx-text-fill: white;");
        textArea.setEditable(false);

        ImageView showText = new ImageView(String.valueOf(textArea));

        getChildren().add(showText); // Lägg till TextArea i HighScoreList
    }


    /**
     * Metod som sätter bakgrundsbilden
     */
    public void setBackground(){
        BackgroundImage myBI= new BackgroundImage(new Image(getClass().getResource(BASE_PATH + "MenuBackground.jpg").toString(),800,600,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(myBI));
    }

    /**
     * Lägger till listener för knapptryck och skiftar scen till menyn
     */
    public void addListener() {
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainProgram.changeToMenu();
                audioPlayer.playButtonSound();
            }
        });
    }

    /**
     * Metod som får pressMouseView-objektet att blinka genom en FadeTransition
     */
    public void pressMouseAnimation(){
        ImageView pressMouseView = new ImageView(pressMouse);
        pressMouseView.setStyle("fx-background-color: transparent;");
        pressMouseView.toFront();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), pressMouseView);
        fadeTransition.setCycleCount(Transition.INDEFINITE);
        pressMouseView.setOpacity(0);
        getChildren().add(pressMouseView);

        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
