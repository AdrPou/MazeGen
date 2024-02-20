package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import com.example.program.view.AudioPlayer;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.scene.control.Label;


import java.io.*;
import java.util.Scanner;

public class HighScoreList extends VBox {
    private MainProgram mainProgram;
    private Image pressMouse;
    private AudioPlayer audioPlayer;
    private final String[] name = new String[10];
    private final int[] score = new int[10];
    private final String[] list = new String[10];
    private int place = 1;
    private static final String BASE_PATH = "/com/example/program/files/";



    public HighScoreList(MainProgram mainProgram, AudioPlayer audioPlayer) {
        this.getStyleClass().add("highScoreList");
        this.getStylesheets().add(getClass().getResource(BASE_PATH + "HighScoreStyle.css").toExternalForm());
        this.mainProgram = mainProgram;
        this.audioPlayer = audioPlayer;
        pressMouse = new Image(getClass().getResource(BASE_PATH + "menuImages/helppicmouse.png").toString());
        setBackground();
        pressMouseAnimation();
        addListener();
        displayHighScores();
    }

    public void displayHighScores() {
        // Läs in highscore-listan
        String[] highScores = readList();

        // Skapa en label för varje highscore
        for (String score : highScores) {
            Label label = new Label(score);
            label.setStyle("-fx-text-fill: white; -fx-font-size: 20px;"); // Styla texten efter behov
            getChildren().add(label); // Lägg till label i HighScoreList
        }
    }

    public String[] readList() {
        int i = 0;
        try {
            //File myFile = new File(BASE_PATH + "HighScoreList.txt");
            //com/example/program/files/HighScoreList.txt
            File myFile = new File("com/example/program/files/HighScoreList.txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                name[i] = myReader.nextLine();
                score[i] = Integer.parseInt(myReader.nextLine());
                if (name[i] != null) {
                    list[i] = String.format(place + " " + name[i] + " " + score[i]);
                    i++;
                    place++;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return list;
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
