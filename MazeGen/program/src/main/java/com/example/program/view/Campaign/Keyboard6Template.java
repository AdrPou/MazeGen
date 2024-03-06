package com.example.program.view.Campaign;

import com.example.program.control.MainProgram;
import com.example.program.view.AudioPlayer;
import com.example.program.view.Menu.RightPanel;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Filip Örnling
 */

public class Keyboard6Template extends KeyBoardCampaign {

    private Image ghost;

    private int squareSize;
    private PathTransition animation;
    private PathTransition animation2;
    private PathTransition animation3;
    private PathTransition animation4;
    private PathTransition animation5;
    private PathTransition animation6;
    private PathTransition animation7;
    private PathTransition animation8;
    private PathTransition animation9;
    private PathTransition animation10;
    private int currentLevel;
    private Rectangle rectangle;
    private ImageView ghost4V;
    private ImageView ghost3V;
    private ImageView ghost1V;
    private ImageView ghost2V;
    private ImageView ghost5V;
    private ImageView ghost6V;
    private ImageView ghost7V;
    private ImageView ghost8V;
    private ImageView ghost9V;
    private ImageView ghost10V;
    private List<ImageView> ghosts;

    private static final String BASE_PATH = "/com/example/program/files/";

    public Keyboard6Template(int[][] level, int currentLevel, int heartCrystals, MainProgram mainProgram,
            RightPanel rightPanel, int worldImage, AudioPlayer audioPlayer) throws FileNotFoundException {
        super(level, currentLevel, heartCrystals, mainProgram, rightPanel, worldImage, audioPlayer, 99);
        squareSize = 600 / (level.length + 2);
        this.currentLevel = currentLevel;
        rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
        rightPanel.resetTimerLabel();
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

        ghost = new Image(getClass().getResource(BASE_PATH + "space_mob3.png").toString(), squareSize, squareSize,
                false, false);

        if (currentLevel == 2) {

            ImageView ghost4V = new ImageView();
            ImageView ghost3V = new ImageView();
            ImageView ghost1V = new ImageView();
            ImageView ghost2V = new ImageView();
            ImageView ghost5V = new ImageView();
            ghost5V.setImage(ghost);
            ghost4V.setImage(ghost);
            ghost3V.setImage(ghost);
            ghost2V.setImage(ghost);
            ghost1V.setImage(ghost);
            add(ghost5V, 16, 4);
            add(ghost3V, 9, 4);
            add(ghost1V, 5, 10);
            add(ghost2V, 4, 5);
            add(ghost4V, 10, 13);
            Rectangle rectangle = new Rectangle(120, 93);
            rectangle.setY(72);
            rectangle.setX(-45);

            Rectangle rectangle2 = new Rectangle(120, 93);
            rectangle2.setY(72);
            rectangle2.setX(-45);

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

            animation3 = new PathTransition();
            animation3.setNode(ghost2V);
            animation3.setDuration(Duration.seconds(4));
            animation3.setCycleCount(Animation.INDEFINITE);
            animation3.setPath(rectangle2);
            animation3.play();

            animation4 = new PathTransition();
            animation4.setNode(ghost4V);
            animation4.setDuration(Duration.seconds(4));
            animation4.setCycleCount(Animation.INDEFINITE);
            animation4.setPath(rectangle);
            animation4.play();

            animation5 = new PathTransition();
            animation5.setNode(ghost5V);
            animation5.setDuration(Duration.seconds(4));
            animation5.setCycleCount(Animation.INDEFINITE);
            animation5.setPath(rectangle);
            animation5.play();
            ghosts = Arrays.asList(ghost1V, ghost2V, ghost3V, ghost4V,ghost5V);
            KeyBoardCampaign.GhostThread ghostThread = new KeyBoardCampaign.GhostThread(this, ghosts, playerLabel);
            ghostThread.start();
        }

        else if (currentLevel == 3) {
            ghost4V = new ImageView();
            ghost3V = new ImageView();
            ghost1V = new ImageView();
            ghost2V = new ImageView();
            ghost5V = new ImageView();
            ghost5V.setImage(ghost);
            ghost4V.setImage(ghost);
            ghost3V.setImage(ghost);
            ghost2V.setImage(ghost);
            ghost1V.setImage(ghost);
            add(ghost5V, 16, 4);
            add(ghost3V, 9, 4);
            add(ghost1V, 5, 10);
            add(ghost2V, 4, 5);
            add(ghost4V, 10, 13);
            rectangle = new Rectangle(120, 93);
            rectangle.setY(72);
            rectangle.setX(-45);

            Rectangle rectangle2 = new Rectangle(120, 93);
            rectangle2.setY(72);
            rectangle2.setX(-45);

            animation = new PathTransition();
            animation.setNode(ghost3V);
            animation.setDuration(Duration.seconds(5));
            animation.setCycleCount(1);
            animation.setPath(rectangle);
            animation.play();
            ghosts = Arrays.asList(ghost1V, ghost2V, ghost3V, ghost4V,ghost5V);
            KeyBoardCampaign.GhostThread ghostThread = new KeyBoardCampaign.GhostThread(this, ghosts, playerLabel);
            ghostThread.start();

            animation.setOnFinished(actionEvent -> {

                animation.setDuration(Duration.seconds(2));
                animation.setCycleCount(Animation.INDEFINITE);
                animation.setAutoReverse(true);

                animation.play();
            });

            animation2 = new PathTransition();
            animation2.setNode(ghost1V);
            animation2.setDuration(Duration.seconds(5));
            animation2.setCycleCount(1);
            animation2.setPath(rectangle2);
            animation2.play();

            animation2.setOnFinished(actionEvent -> {
                animation2.setDuration(Duration.seconds(2));
                animation2.setCycleCount(Animation.INDEFINITE);
                animation2.setAutoReverse(true);
                animation2.play();
            });

            animation3 = new PathTransition();
            animation3.setNode(ghost2V);
            animation3.setDuration(Duration.seconds(5));
            animation3.setCycleCount(1);
            animation3.setPath(rectangle2);
            animation3.play();

            animation3.setOnFinished(actionEvent -> {
                animation3.setDuration(Duration.seconds(2));
                animation3.setCycleCount(Animation.INDEFINITE);
                animation3.setAutoReverse(true);
                animation3.play();
            });

            animation4 = new PathTransition();
            animation4.setNode(ghost4V);
            animation4.setDuration(Duration.seconds(5));
            animation4.setCycleCount(1);
            animation4.setPath(rectangle);
            animation4.play();

            animation4.setOnFinished(actionEvent -> {
                animation4.setDuration(Duration.seconds(2));
                animation4.setCycleCount(Animation.INDEFINITE);
                animation4.setAutoReverse(true);
                animation4.play();
            });

            animation5 = new PathTransition();
            animation5.setNode(ghost5V);
            animation5.setDuration(Duration.seconds(5));
            animation5.setCycleCount(1);
            animation5.setPath(rectangle);
            animation5.play();

            animation5.setOnFinished(actionEvent -> {
                animation5.setDuration(Duration.seconds(2));
                animation5.setCycleCount(Animation.INDEFINITE);
                animation5.setAutoReverse(true);
                animation5.play();
            });

        } else if (currentLevel == 4) {
            ghost4V = new ImageView();
            ghost3V = new ImageView();
            ghost1V = new ImageView();
            ghost2V = new ImageView();
            ghost5V = new ImageView();
            ghost6V = new ImageView();
            ghost7V = new ImageView();
            ghost8V = new ImageView();
            ghost9V = new ImageView();
            ghost10V = new ImageView();
            ghost10V.setImage(ghost);
            ghost9V.setImage(ghost);
            ghost8V.setImage(ghost);
            ghost7V.setImage(ghost);
            ghost6V.setImage(ghost);
            ghost5V.setImage(ghost);
            ghost4V.setImage(ghost);
            ghost3V.setImage(ghost);
            ghost2V.setImage(ghost);
            ghost1V.setImage(ghost);
            add(ghost5V, 3, 1);
            add(ghost3V, 3, 3);
            add(ghost1V, 3, 5);
            add(ghost2V, 3, 7);
            add(ghost4V, 3, 9);
            add(ghost6V, 3, 11);
            add(ghost7V, 3, 13);
            add(ghost8V, 3, 15);
            add(ghost9V, 3, 17);
            rectangle = new Rectangle(525, 0);
            rectangle.setY(72);
            rectangle.setX(-45);

            Rectangle rectangle2 = new Rectangle(525, 0);
            rectangle2.setY(105);
            rectangle2.setX(-45);

            animation = new PathTransition();
            animation.setNode(ghost3V);
            animation.setDuration(Duration.seconds(5));
            animation.setCycleCount(3);
            animation.setPath(rectangle);
            animation.play();

            animation.setOnFinished(actionEvent -> {
                animation.setCycleCount(1);
                animation.setAutoReverse(true);
                animation.setPath(rectangle2);
                animation.play();
                animation.setOnFinished(actionEvent1 -> {
                    animation.setCycleCount(Animation.INDEFINITE);
                    animation.setAutoReverse(true);
                    animation.setPath(rectangle);
                    animation.play();
                });
            });

            animation2 = new PathTransition();
            animation2.setNode(ghost1V);
            animation2.setDuration(Duration.seconds(5));
            animation2.setCycleCount(3);
            animation2.setPath(rectangle);
            animation2.play();
            animation2.setOnFinished(actionEvent -> {
                animation2.setCycleCount(1);
                animation2.setAutoReverse(true);
                animation2.setPath(rectangle2);
                animation2.play();
                animation2.setOnFinished(actionEvent1 -> {
                    animation2.setCycleCount(Animation.INDEFINITE);
                    animation2.setAutoReverse(true);
                    animation2.setPath(rectangle);
                    animation2.play();
                });
            });

            animation3 = new PathTransition();
            animation3.setNode(ghost2V);
            animation3.setDuration(Duration.seconds(5));
            animation3.setCycleCount(3);
            animation3.setPath(rectangle);
            animation3.play();

            animation3.setOnFinished(actionEvent -> {
                animation3.setPath(rectangle2);
                animation3.setCycleCount(1);
                animation3.setAutoReverse(true);
                animation3.play();
                animation3.setOnFinished(actionEvent1 -> {
                    animation3.setCycleCount(Animation.INDEFINITE);
                    animation3.setPath(rectangle);
                    animation3.play();
                });
            });

            animation4 = new PathTransition();
            animation4.setNode(ghost4V);
            animation4.setDuration(Duration.seconds(5));
            animation4.setCycleCount(3);
            animation4.setPath(rectangle);
            animation4.play();

            animation4.setOnFinished(actionEvent -> {
                animation4.setPath(rectangle2);
                animation4.setCycleCount(1);
                animation4.setAutoReverse(true);
                animation4.play();
                animation4.setOnFinished(actionEvent1 -> {
                    animation4.setCycleCount(Animation.INDEFINITE);
                    animation4.setPath(rectangle);
                    animation4.play();
                });
            });

            animation5 = new PathTransition();
            animation5.setNode(ghost5V);
            animation5.setDuration(Duration.seconds(5));
            animation5.setCycleCount(3);
            animation5.setPath(rectangle);
            animation5.play();
            ghosts = Arrays.asList(ghost1V, ghost2V, ghost3V, ghost4V,ghost5V, ghost6V, ghost7V, ghost8V,ghost9V ,ghost10V);
            KeyBoardCampaign.GhostThread ghostThread = new KeyBoardCampaign.GhostThread(this, ghosts, playerLabel);
            ghostThread.start();

            animation5.setOnFinished(actionEvent -> {
                animation5.setPath(rectangle2);
                animation5.setCycleCount(1);
                animation5.setAutoReverse(true);
                animation5.play();
                animation5.setOnFinished(actionEvent1 -> {
                    animation5.setCycleCount(Animation.INDEFINITE);
                    animation5.setPath(rectangle);
                    animation5.play();
                });
            });
            animation6 = new PathTransition();
            animation6.setNode(ghost6V);
            animation6.setDuration(Duration.seconds(5));
            animation6.setCycleCount(3);
            animation6.setPath(rectangle);
            animation6.play();

            animation6.setOnFinished(actionEvent -> {
                animation6.setPath(rectangle2);
                animation6.setCycleCount(1);
                animation6.setAutoReverse(true);
                animation6.play();
                animation6.setOnFinished(actionEvent1 -> {
                    animation6.setCycleCount(Animation.INDEFINITE);
                    animation6.setPath(rectangle);
                    animation6.play();
                });
            });

            animation7 = new PathTransition();
            animation7.setNode(ghost7V);
            animation7.setDuration(Duration.seconds(5));
            animation7.setCycleCount(3);
            animation7.setPath(rectangle);
            animation7.play();

            animation7.setOnFinished(actionEvent -> {
                animation7.setPath(rectangle2);
                animation7.setCycleCount(1);
                animation7.setAutoReverse(true);
                animation7.play();
                animation7.setOnFinished(actionEvent1 -> {
                    animation7.setCycleCount(Animation.INDEFINITE);
                    animation7.setPath(rectangle);
                    animation7.play();
                });
            });

            animation8 = new PathTransition();
            animation8.setNode(ghost8V);
            animation8.setDuration(Duration.seconds(5));
            animation8.setCycleCount(3);
            animation8.setPath(rectangle);
            animation8.play();

            animation8.setOnFinished(actionEvent -> {
                animation8.setPath(rectangle2);
                animation8.setCycleCount(1);
                animation8.setAutoReverse(true);
                animation8.play();
                animation8.setOnFinished(actionEvent1 -> {
                    animation8.setCycleCount(Animation.INDEFINITE);
                    animation8.setPath(rectangle);
                    animation8.play();
                });
            });

            animation9 = new PathTransition();
            animation9.setNode(ghost9V);
            animation9.setDuration(Duration.seconds(5));
            animation9.setCycleCount(3);
            animation9.setPath(rectangle);
            animation9.play();
            animation9.setOnFinished(actionEvent -> {
                animation9.setPath(rectangle2);
                animation9.setCycleCount(1);
                animation9.setAutoReverse(true);
                animation9.play();
                animation9.setOnFinished(actionEvent1 -> {
                    animation9.setCycleCount(Animation.INDEFINITE);
                    animation9.setPath(rectangle);
                    animation9.play();
                });
            });

        } else if (currentLevel == 5) {
            ImageView ghost1V = new ImageView();
            ImageView ghost2V = new ImageView();
            ImageView ghost3V = new ImageView();

            ghost3V.setImage(ghost);
            ghost2V.setImage(ghost);
            ghost1V.setImage(ghost);

            add(ghost1V, 12, 8);
            add(ghost2V, 14, 0);
            // add(ghost3V, 8, 12);

            Rectangle rectangle = new Rectangle(132, 200);
            rectangle.setY(80);
            rectangle.setX(-150);

            Rectangle rectangle1 = new Rectangle(0, 135);
            rectangle1.setY(80);
            rectangle1.setX(-150);

            Rectangle rectangle2 = new Rectangle(400, 0);
            rectangle2.setY(20);
            rectangle2.setX(-150);

            animation = new PathTransition();
            animation.setNode(ghost1V);
            animation.setDuration(Duration.seconds(2));
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setPath(rectangle);
            animation.play();

            animation2 = new PathTransition();
            animation2.setNode(ghost2V);
            animation2.setDuration(Duration.seconds(3));
            animation2.setAutoReverse(true);
            animation2.setCycleCount(Animation.INDEFINITE);
            animation2.setPath(rectangle1);
            animation2.play();

            animation3 = new PathTransition();
            animation3.setNode(ghost3V);
            animation3.setDuration(Duration.seconds(2.5));
            animation3.setCycleCount(Animation.INDEFINITE);
            animation3.setPath(rectangle2);
            animation3.play();
            ghosts = Arrays.asList(ghost1V, ghost2V, ghost3V);
            KeyBoardCampaign.GhostThread ghostThread = new KeyBoardCampaign.GhostThread(this, ghosts, playerLabel);
            ghostThread.start();

        }

        else if (currentLevel == 6) {
            ImageView ghost1V = new ImageView();
            ImageView ghost2V = new ImageView();
            ImageView ghost3V = new ImageView();
            ImageView ghost4V = new ImageView();

            ghost4V.setImage(ghost);
            ghost3V.setImage(ghost);
            ghost2V.setImage(ghost);
            ghost1V.setImage(ghost);

            add(ghost1V, 7, 6);
            add(ghost2V, 6, 3);
            add(ghost3V, 16, 0);
            add(ghost4V, 17, 10);

            Rectangle rectangle = new Rectangle(100, 100);
            rectangle.setY(80);
            rectangle.setX(-150);

            Rectangle rectangle1 = new Rectangle(100, 0);
            rectangle1.setY(80);
            rectangle1.setX(-150);

            Rectangle rectangle2 = new Rectangle(0, 450);
            rectangle2.setY(80);
            rectangle2.setX(17);

            Rectangle rectangle3 = new Rectangle(70, 130);
            rectangle3.setY(80);
            rectangle3.setX(-150);

            animation = new PathTransition();
            animation.setNode(ghost1V);
            animation.setDuration(Duration.seconds(2));
            animation.setAutoReverse(false);
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setPath(rectangle);
            animation.play();

            animation2 = new PathTransition();
            animation2.setNode(ghost2V);
            animation2.setDuration(Duration.seconds(2.5));
            animation2.setAutoReverse(true);
            animation2.setCycleCount(Animation.INDEFINITE);
            animation2.setPath(rectangle1);
            animation2.play();

            animation3 = new PathTransition();
            animation3.setNode(ghost3V);
            animation3.setDuration(Duration.seconds(2));
            animation3.setAutoReverse(true);
            animation3.setCycleCount(Animation.INDEFINITE);
            animation3.setPath(rectangle2);
            animation3.play();

            animation4 = new PathTransition();
            animation4.setNode(ghost4V);
            animation4.setDuration(Duration.seconds(3));
            animation4.setCycleCount(Animation.INDEFINITE);
            animation4.setPath(rectangle3);
            animation4.play();
            ghosts = Arrays.asList(ghost1V, ghost2V, ghost3V, ghost4V);
            KeyBoardCampaign.GhostThread ghostThread = new KeyBoardCampaign.GhostThread(this, ghosts, playerLabel);
            ghostThread.start();
        }
    }

}// Class
