package com.example.program.view.Campaign;

import com.example.program.control.MainProgram;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import com.example.program.model.TimeThread;
import com.example.program.view.AudioPlayer;
import com.example.program.view.Menu.RightPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Sebastian Helin & Filip Örnling
 */


public class World2Template extends World1Template {
    
    private int squareSize;
    private int duration=10;
    private PathTransition animation;
    private PathTransition animation2;
    private PathTransition animation3;
    private PathTransition animation4;
    private PathTransition animation5;
    private PathTransition animation6;
    private Image ghost;
    private ImageView imageView = new ImageView();
    private TimeThread time;
    private RightPanel panel;
    private AudioPlayer audioPlayer;
    private int seconds;

    private static final String BASE_PATH = "/com/example/program/files/";

    public World2Template(int[][] level, int currentLevel, int heartCrystals, MainProgram mainProgram, RightPanel rightPanel, int world, AudioPlayer audioPlayer, boolean bossMap, RightPanel panel) throws FileNotFoundException {
        super(level, currentLevel, heartCrystals, mainProgram, rightPanel, world, audioPlayer, 35);
        squareSize = 600/(level.length+2);
        this.panel = panel;
        this.audioPlayer = audioPlayer;
        rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
        if (bossMap) {
            setupGhost();
            initialize();
        }
        time = new TimeThread(35, rightPanel);
        rightPanel.setSTARTTIME(35);
        rightPanel.resetTimerLabel();
    }
    public void setupGhost() {
        ghost = new Image(getClass().getResource(BASE_PATH + "ghost.png").toString(), squareSize, squareSize, false, false);
    }

    /**
     * Metoden initialize instansierar olika antal ImageView objekt beroende på vilken bana som spelas
     * Metoden initialize instansierar även olika antal rectanglar i olika former beroende på bana
     * Metoden kopplar sedan samman ImageView objekt och rectanglar för att skapa animationer
     * Animationerna kan gå i olika hastigheter
     */

    public void initialize() {

        ImageView ghost1V = new ImageView(ghost);
        ImageView ghost2V = new ImageView(ghost);
        ImageView ghost3V = new ImageView(ghost);
        ImageView ghost4V = new ImageView(ghost);
        ImageView ghost5V = new ImageView(ghost);
        ImageView ghost6V = new ImageView(ghost);

        Label ghost1VLabel = new Label();
        Label ghost2VLabel = new Label();
        Label ghost3VLabel = new Label();
        Label ghost4VLabel = new Label();
        Label ghost5VLabel = new Label();
        Label ghost6VLabel = new Label();

        ghost1VLabel.setGraphic(ghost1V);
        ghost2VLabel.setGraphic(ghost2V);
        ghost3VLabel.setGraphic(ghost3V);
        ghost4VLabel.setGraphic(ghost4V);
        ghost5VLabel.setGraphic(ghost5V);
        ghost6VLabel.setGraphic(ghost6V);

        ghost1V.setOnMouseEntered(e -> enteredGhost(e));
        ghost2V.setOnMouseEntered(e -> enteredGhost(e));
        ghost3V.setOnMouseEntered(e -> enteredGhost(e));
        ghost4V.setOnMouseEntered(e -> enteredGhost(e));
        ghost5V.setOnMouseEntered(e -> enteredGhost(e));
        ghost6V.setOnMouseEntered(e -> enteredGhost(e));

        add(ghost1V,10,0);
        add(ghost2V,9,0);
        add(ghost3V,8,0);
        add(ghost4V, 1, 0);
        add(ghost5V, 2, 0);
        add(ghost6V, 3, 0);

        Polyline line1 = new Polyline();
        Polyline line2 = new Polyline();
        Polyline line3 = new Polyline();
        Polyline line4 = new Polyline();
        Polyline line5 = new Polyline();
        Polyline line6 = new Polyline();
        
        line1.getPoints().addAll(
                16.0, -10.5,
                10.5, 650.0);
        line2.getPoints().addAll(
                15.0,-10.5,
                10.5,650.0);
        line3.getPoints().addAll(
                14.0,-10.5,
                10.5,650.0);
        line4.getPoints().addAll(
                16.0, -10.5,
                10.5, 650.0);
        line5.getPoints().addAll(
                15.0,-10.5,
                10.5,650.0);
        line6.getPoints().addAll(
                14.0,-10.5,
                10.5,650.0);


        animation3 = new PathTransition();
        animation3.setNode(ghost3V);
        animation3.setDuration(Duration.seconds(4));
        animation3.setCycleCount(Animation.INDEFINITE);
        animation3.setPath(line3);
        animation3.play();

        animation2 = new PathTransition();
        animation2.setNode(ghost2V);
        animation2.setDuration(Duration.seconds(3));
        animation2.setCycleCount(Animation.INDEFINITE);
        animation2.setPath(line2);
        animation2.play();

        animation = new PathTransition();
        animation.setNode(ghost1V);
        animation.setDuration(Duration.seconds(3.5));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setPath(line1);
        animation.play();

        animation4 = new PathTransition();
        animation4.setNode(ghost4V);
        animation4.setDuration(Duration.seconds(3.5));
        animation4.setCycleCount(Animation.INDEFINITE);
        animation4.setPath(line4);
        animation4.play();

        animation5 = new PathTransition();
        animation5.setNode(ghost5V);
        animation5.setDuration(Duration.seconds(3));
        animation5.setCycleCount(Animation.INDEFINITE);
        animation5.setPath(line5);
        animation5.play();

        animation6 = new PathTransition();
        animation6.setNode(ghost6V);
        animation6.setDuration(Duration.seconds(4));
        animation6.setCycleCount(Animation.INDEFINITE);
        animation6.setPath(line6);
        animation6.play();

        Polyline line = new Polyline();
        line.getPoints().addAll(
                -100.0, -50.0,
                -50.0, 100.0,
                100.0, 200.0,
                200.0, -150.0);
        animation = new PathTransition();
        animation.setNode(imageView);
        animation.setDuration(Duration.seconds(duration));
        animation.setPath(line);
        animation.setCycleCount(PathTransition.INDEFINITE);
        animation.play();

        ghost1V.setOnMouseEntered(e -> enteredGhost(e));
        ghost2V.setOnMouseEntered(e -> enteredGhost(e));
        ghost3V.setOnMouseEntered(e -> enteredGhost(e));
        ghost4V.setOnMouseEntered(e -> enteredGhost(e));
        ghost5V.setOnMouseEntered(e -> enteredGhost(e));
        ghost6V.setOnMouseEntered(e -> enteredGhost(e));



    }
}
