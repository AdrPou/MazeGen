package com.example.program.view.Campaign;

import com.example.program.control.MainProgram;
import com.example.program.model.TimeThread;
import com.example.program.view.AudioPlayer;
import com.example.program.view.Menu.RightPanel;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Keyboard3Template extends KeyBoardCampaign {

    /**
     * @author Filip Örnling
     */

    private Image ghost;
    private int squareSize;
    private ImageView imageView = new ImageView();
    private PathTransition animation;
    private PathTransition animation2;
    private PathTransition animation3;
    private PathTransition animation4;
    private PathTransition animation5;
    private PathTransition animation6;
    private Thread platfrom;
    private int currentLevel;
    private RightPanel panel;
    private TimeThread time;
    private AudioPlayer audioPlayer;
    ImageView ghost1V;
    ImageView ghost2V;
    ImageView ghost3V;
    ImageView ghost4V;
    ImageView ghost5V;
    private List<ImageView> ghosts;
    private static final String BASE_PATH = "/com/example/program/files/";

    public Keyboard3Template(int[][] level, int currentLevel, int heartCrystals, MainProgram mainProgram,
            RightPanel rightPanel, int worldImage, AudioPlayer audioPlayer) throws FileNotFoundException {
        super(level, currentLevel, heartCrystals, mainProgram, rightPanel, worldImage, audioPlayer, 60);
        squareSize = 600 / (level.length + 2);
        this.currentLevel = currentLevel;
        this.panel = rightPanel;
        this.audioPlayer = audioPlayer;
        rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
        rightPanel.setSTARTTIME(60);
        rightPanel.resetTimerLabel();
        setupGhost();

    }

    // Konstruktorn ska kunna ta emot int-arrayer och representera dem i GUIt
    public void setupGhost() throws FileNotFoundException {
        ghost = new Image(getClass().getResource(BASE_PATH + "ghost.png").toString(), squareSize, squareSize, false,
                false);

        imageView.setImage(ghost);

        imageView.setX(1);
        imageView.setY(1);
        imageView.setFitHeight(squareSize);
        imageView.setFitWidth(squareSize);


        initialize();
    }

    /**
     * Metoden initialize instansierar olika antal ImageView objekt beroende på
     * vilken bana som spelas
     * Metoden initialize instansierar även olika antal rectanglar i olika former
     * beroende på bana
     * Metoden kopplar sedan samman ImageView objekt och rectanglar för att skapa
     * animationer
     * Animationerna kan gå i olika hastigheter
     */

    public void initialize() {
        if (currentLevel == 2) {
            ghost3V = new ImageView();
            ghost1V = new ImageView();
            ghost3V.setImage(ghost);
            ghost1V.setImage(ghost);
            add(ghost3V, 5, 5);
            add(ghost1V, 14, 5);

            Rectangle rectangle = new Rectangle(125, 251);
            rectangle.setY(65);
            rectangle.setX(-147);

            Rectangle rectangle2 = new Rectangle(84, 123);
            rectangle2.setY(65);
            rectangle2.setX(-147);

            animation = new PathTransition();
            animation.setNode(ghost3V);
            animation.setDuration(Duration.seconds(4));
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setPath(rectangle);
            animation.play();

            animation2 = new PathTransition();
            animation2.setNode(ghost1V);
            animation2.setDuration(Duration.seconds(4));
            animation2.setCycleCount(Animation.INDEFINITE);
            animation2.setPath(rectangle2);
            animation2.play();
            ghosts = Arrays.asList(ghost1V, ghost3V);
            GhostThread ghostThread = new GhostThread(this, ghosts, playerLabel);
            ghostThread.start();



        }

        else if (currentLevel == 3) {
            ghost1V = new ImageView();
            ghost2V = new ImageView();

            ghost1V.setImage(ghost);
            ghost2V.setImage(ghost);

            // add(ghost1V,5,8);
            add(ghost2V, 9, 3);

            Rectangle rectangle = new Rectangle(125, 120);
            rectangle.setY(65);
            rectangle.setX(-147);

            Rectangle rectangle1 = new Rectangle(210, 125);
            rectangle1.setY(190);
            rectangle1.setX(-231);

            animation = new PathTransition();
            animation.setNode(ghost1V);
            animation.setDuration(Duration.seconds(2));
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setPath(rectangle);
            animation.play();

            animation2 = new PathTransition();
            animation2.setNode(ghost2V);
            animation2.setDuration(Duration.seconds(2));
            animation2.setCycleCount(Animation.INDEFINITE);
            animation2.setPath(rectangle1);
            animation2.play();
            ghosts = Arrays.asList(ghost1V, ghost2V);
            GhostThread ghostThread = new GhostThread(this, ghosts, playerLabel);
            ghostThread.start();



        } else if (currentLevel == 4) {
             ghost4V = new ImageView();
             ghost2V = new ImageView();
             ghost1V = new ImageView();
             ghost3V = new ImageView();
             ghost5V = new ImageView();

            ghost1V.setImage(ghost);
            ghost2V.setImage(ghost);
            ghost3V.setImage(ghost);
            ghost4V.setImage(ghost);
            ghost5V.setImage(ghost);

            add(ghost2V, 15, 4);
            add(ghost4V, 10, 4);
            add(ghost1V, 10, 1);
            add(ghost3V, 15, 1);
            add(ghost5V, 12, 1);

            Rectangle rectangle = new Rectangle(125, 83);
            rectangle.setY(190);
            rectangle.setX(-230);

            Rectangle rectangle1 = new Rectangle(125, 83);
            rectangle1.setY(190);
            rectangle1.setX(-230);

            Rectangle rectangle2 = new Rectangle(0, 83);
            rectangle2.setY(105);
            rectangle2.setX(-232);

            Rectangle rectangle3 = new Rectangle(0, 83);
            rectangle3.setY(105);
            rectangle3.setX(-232);

            Rectangle rectangle4 = new Rectangle(0, 83);
            rectangle4.setY(105);
            rectangle4.setX(-232);

            // Övre spöken

            animation3 = new PathTransition();
            animation3.setNode(ghost1V);
            animation3.setDuration(Duration.seconds(2));
            animation3.setCycleCount(Animation.INDEFINITE);
            animation3.setPath(rectangle2);
            animation3.setAutoReverse(true);
            animation3.play();

            animation4 = new PathTransition();
            animation4.setNode(ghost3V);
            animation4.setDuration(Duration.seconds(2));
            animation4.setCycleCount(Animation.INDEFINITE);
            animation4.setPath(rectangle3);
            animation4.setAutoReverse(true);
            animation4.play();

            animation5 = new PathTransition();
            animation5.setNode(ghost5V);
            animation5.setDuration(Duration.seconds(2));
            animation5.setCycleCount(Animation.INDEFINITE);
            animation5.setPath(rectangle4);
            animation5.setAutoReverse(true);
            animation5.play();

            // Undre spöken

            animation = new PathTransition();
            animation.setNode(ghost4V);
            animation.setDuration(Duration.seconds(2));
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setPath(rectangle);
            animation.setAutoReverse(true);
            animation.play();

            animation2 = new PathTransition();
            animation2.setNode(ghost2V);
            animation2.setDuration(Duration.seconds(3));
            animation2.setCycleCount(Animation.INDEFINITE);
            animation2.setPath(rectangle1);
            animation2.setAutoReverse(true);
            animation2.play();
            ghosts = Arrays.asList(ghost1V, ghost2V, ghost3V, ghost4V, ghost5V);
            GhostThread ghostThread = new GhostThread(this, ghosts, playerLabel);
            ghostThread.start();



        } else if (currentLevel == 5) {
            ghost1V = new ImageView();
            ghost2V = new ImageView();
            ghost3V = new ImageView();
            ghost4V = new ImageView();

            ghost4V.setImage(ghost);
            ghost3V.setImage(ghost);
            ghost2V.setImage(ghost);
            ghost1V.setImage(ghost);


            add(ghost4V, 8, 11);
            add(ghost1V, 8, 3);
            add(ghost2V, 8, 8);
            add(ghost3V, 8, 12);

            Rectangle rectangle = new Rectangle(83, 125);
            rectangle.setY(65);
            rectangle.setX(-150);

            Rectangle rectangle1 = new Rectangle(0, 125);
            rectangle1.setY(65);
            rectangle1.setX(-150);

            Rectangle rectangle2 = new Rectangle(400, 0);
            rectangle2.setY(20);
            rectangle2.setX(-150);

            Rectangle rectangle3 = new Rectangle(400, 0);
            rectangle3.setY(20);
            rectangle3.setX(-150);

            animation4 = new PathTransition();
            animation4.setNode(ghost4V);
            animation4.setDuration(Duration.seconds(3));
            animation4.setAutoReverse(true);
            animation4.setCycleCount(Animation.INDEFINITE);
            animation4.setPath(rectangle3);
            animation4.play();

            animation3 = new PathTransition();
            animation3.setNode(ghost3V);
            animation3.setDuration(Duration.seconds(2.5));
            animation3.setAutoReverse(true);
            animation3.setCycleCount(Animation.INDEFINITE);
            animation3.setPath(rectangle2);
            animation3.play();

            animation2 = new PathTransition();
            animation2.setNode(ghost2V);
            animation2.setDuration(Duration.seconds(1.5));
            animation2.setAutoReverse(true);
            animation2.setCycleCount(Animation.INDEFINITE);
            animation2.setPath(rectangle1);
            animation2.play();

            animation = new PathTransition();
            animation.setNode(ghost1V);
            animation.setDuration(Duration.seconds(2));
            animation.setAutoReverse(true);
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setPath(rectangle);
            animation.play();
            ghosts = Arrays.asList(ghost1V, ghost2V, ghost3V, ghost4V);
            GhostThread ghostThread = new GhostThread(this, ghosts, playerLabel);
            ghostThread.start();

        }

        else if (currentLevel == 6) {
            ghost1V = new ImageView();
            ghost2V = new ImageView();
            ghost3V = new ImageView();
            ghost4V = new ImageView();

            ghost4V.setImage(ghost);
            ghost3V.setImage(ghost);
            ghost2V.setImage(ghost);
            ghost1V.setImage(ghost);

            add(ghost4V, 8, 10);
            add(ghost3V, 8, 6);

            add(ghost1V, 7, 6);
            add(ghost2V, 8, 2);

            Rectangle rectangle = new Rectangle(83, 125);
            rectangle.setY(65);
            rectangle.setX(-150);

            Rectangle rectangle1 = new Rectangle(0, 40);
            rectangle1.setY(65);
            rectangle1.setX(-150);

            Rectangle rectangle2 = new Rectangle(170, 87);
            rectangle2.setY(20);
            rectangle2.setX(20);

            Rectangle rectangle3 = new Rectangle(170, 87);
            rectangle3.setY(20);
            rectangle3.setX(20);

            animation4 = new PathTransition();
            animation4.setNode(ghost4V);
            animation4.setDuration(Duration.seconds(3));
            animation4.setAutoReverse(true);
            animation4.setCycleCount(Animation.INDEFINITE);
            animation4.setPath(rectangle3);
            animation4.play();

            animation3 = new PathTransition();
            animation3.setNode(ghost3V);
            animation3.setDuration(Duration.seconds(2.5));
            animation3.setAutoReverse(true);
            animation3.setCycleCount(Animation.INDEFINITE);
            animation3.setPath(rectangle2);
            animation3.play();

            animation2 = new PathTransition();
            animation2.setNode(ghost2V);
            animation2.setDuration(Duration.seconds(1.5));
            animation2.setAutoReverse(true);
            animation2.setCycleCount(Animation.INDEFINITE);
            animation2.setPath(rectangle1);
            animation2.play();

            animation = new PathTransition();
            animation.setNode(ghost1V);
            animation.setDuration(Duration.seconds(2));
            animation.setAutoReverse(false);
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setPath(rectangle);
            animation.play();
            ghosts = Arrays.asList(ghost1V, ghost2V, ghost3V, ghost4V);
            GhostThread ghostThread = new GhostThread(this, ghosts, playerLabel);
            ghostThread.start();


        }

    }

}// Class
