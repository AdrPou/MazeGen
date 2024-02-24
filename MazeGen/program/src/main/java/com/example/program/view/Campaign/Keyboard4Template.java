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

/**
 * @author Filip Örnling
 */

public class Keyboard4Template extends World1Template {


    private Image ghost;
    private Image largeGhost;

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
    private static final String BASE_PATH = "/com/example/program/files/";


    public Keyboard4Template(int[][] level, int currentLevel, int heartCrystals, MainProgram mainProgram, RightPanel rightPanel, int worldImage, AudioPlayer audioPlayer) throws FileNotFoundException {
        super(level, currentLevel, heartCrystals, mainProgram, rightPanel, worldImage, audioPlayer, 80);
        rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
        this.currentLevel = currentLevel;
        squareSize = 600/(level.length+2);

        rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
        rightPanel.resetTimerLabel();
        setupGhost();
    }

    public void setupGhost() throws FileNotFoundException {
        ghost = new Image(getClass().getResource(BASE_PATH + "god_mob2.png").toString(), squareSize, squareSize, false, false);

        imageView.setImage(ghost);

        imageView.setX(1);
        imageView.setY(1);
        imageView.setFitHeight(squareSize);
        imageView.setFitWidth(squareSize);

        imageView.setOnMouseEntered(e -> enteredWall(e));

        initialize();
    }

    /**
     * Metoden initialize instansierar olika antal ImageView objekt beroende på vilken bana som spelas
     * Metoden initialize instansierar även olika antal rectanglar i olika former beroende på bana
     * Metoden kopplar sedan samman ImageView objekt och rectanglar för att skapa animationer
     * Animationerna kan gå i olika hastigheter
     */

    public void initialize() {
        if (currentLevel==2){
            ImageView ghost3V = new ImageView();
            ImageView ghost1V = new ImageView();
            ImageView ghost2V = new ImageView();

            ghost2V.setImage(ghost);
            ghost3V.setImage(ghost);
            ghost1V.setImage(ghost);
            add(ghost3V,15,9);
            add(ghost1V,14,5);
            add(ghost2V,0,10);
            Rectangle rectangle = new Rectangle(110,150);
            rectangle.setY(57);
            rectangle.setX(-130);

            Rectangle rectangle2 = new Rectangle(110,75);
            rectangle2.setY(57);
            rectangle2.setX(-130);

            Rectangle rectangle1 = new Rectangle(75,75);
            rectangle1.setX(57);
            rectangle1.setY(-130);

            animation3 = new PathTransition();
            animation3.setNode(ghost2V);
            animation3.setPath(rectangle1);
            animation3.setDuration(Duration.seconds(2.5));
            animation3.setCycleCount(Animation.INDEFINITE);
            animation3.setAutoReverse(true);
            animation3.play();

            animation = new PathTransition();
            animation.setNode(ghost3V);
            animation.setDuration(Duration.seconds(3.5));
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setPath(rectangle);
            animation.play();

            animation2 = new PathTransition();
            animation2.setNode(ghost1V);
            animation2.setDuration(Duration.seconds(2));
            animation2.setCycleCount(Animation.INDEFINITE);
            animation2.setPath(rectangle2);
            animation2.play();

            ghost1V.setOnMouseEntered(e -> enteredGhost(e));
            ghost3V.setOnMouseEntered(e -> enteredGhost(e));

        }

        else if (currentLevel ==3){

            ImageView ghost1V = new ImageView();
            ImageView ghost2V = new ImageView();

            ghost1V.setImage(ghost);
            ghost2V.setImage(ghost);

            add(ghost1V,1,4);
            add(ghost2V,5,9);

            Rectangle rectangle = new Rectangle(115,115);
            rectangle.setY(18);
            rectangle.setX(18);

            Rectangle rectangle1 = new Rectangle(190,190);
            rectangle1.setY(18);
            rectangle1.setX(18);

            animation = new PathTransition();
            animation.setNode(ghost1V);
            animation.setDuration(Duration.seconds(2));
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setPath(rectangle);
            animation.setAutoReverse(true);
            animation.play();

            animation2 = new PathTransition();
            animation2.setNode(ghost2V);
            animation2.setDuration(Duration.seconds(2));
            animation2.setCycleCount(Animation.INDEFINITE);
            animation2.setPath(rectangle1);
            animation2.play();

            ghost1V.setOnMouseEntered(e -> enteredGhost(e));
            ghost2V.setOnMouseEntered(e -> enteredGhost(e));

        }
        else if (currentLevel ==4){
            ImageView ghost4V = new ImageView();
            ImageView ghost2V = new ImageView();
            ImageView ghost1V = new ImageView();
            ImageView ghost3V = new ImageView();
            ImageView ghost5V = new ImageView();
            ImageView ghost6V = new ImageView();
            ImageView ghost7V = new ImageView();

            ghost1V.setImage(ghost);
            ghost2V.setImage(ghost);
            ghost3V.setImage(ghost);
            ghost4V.setImage(ghost);
            ghost5V.setImage(ghost);
            ghost6V.setImage(ghost);
            ghost7V.setImage(ghost);

            add(ghost2V,0,2);
            add(ghost4V,0,4);
            add(ghost1V,0,6);
            add(ghost3V,0,8);
            add(ghost5V,0,10);
            add(ghost6V,0,12);
            add(ghost7V,0,14);

            Rectangle rectangle = new Rectangle(570,0);
            rectangle.setY(20);
            rectangle.setX(20);

            Rectangle rectangle1 = new Rectangle(570,0);
            rectangle1.setY(20);
            rectangle1.setX(20);

            Rectangle rectangle2 = new Rectangle(570,0);
            rectangle2.setY(20);
            rectangle2.setX(20);

            Rectangle rectangle3 = new Rectangle(570,0);
            rectangle3.setY(20);
            rectangle3.setX(20);

            Rectangle rectangle4 = new Rectangle(570,0);
            rectangle4.setY(20);
            rectangle4.setX(20);

            //Övre spöken

            animation3 = new PathTransition();
            animation3.setNode(ghost1V);
            animation3.setDuration(Duration.seconds(4));
            animation3.setCycleCount(Animation.INDEFINITE);
            animation3.setPath(rectangle2);
            animation3.setAutoReverse(true);
            animation3.play();

            animation4 = new PathTransition();
            animation4.setNode(ghost3V);
            animation4.setDuration(Duration.seconds(4));
            animation4.setCycleCount(Animation.INDEFINITE);
            animation4.setPath(rectangle3);
            animation4.setAutoReverse(true);
            animation4.play();

            animation5 = new PathTransition();
            animation5.setNode(ghost5V);
            animation5.setDuration(Duration.seconds(4));
            animation5.setCycleCount(Animation.INDEFINITE);
            animation5.setPath(rectangle4);
            animation5.setAutoReverse(true);
            animation5.play();

            //Undre spöken

            animation = new PathTransition();
            animation.setNode(ghost4V);
            animation.setDuration(Duration.seconds(4));
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setPath(rectangle);
            animation.setAutoReverse(true);
            animation.play();

            animation2 = new PathTransition();
            animation2.setNode(ghost2V);
            animation2.setDuration(Duration.seconds(4));
            animation2.setCycleCount(Animation.INDEFINITE);
            animation2.setPath(rectangle1);
            animation2.setAutoReverse(true);
            animation2.play();

            PathTransition animation6 = new PathTransition();
            animation6.setNode(ghost6V);
            animation6.setDuration(Duration.seconds(4));
            animation6.setCycleCount(Animation.INDEFINITE);
            animation6.setPath(rectangle1);
            animation6.setAutoReverse(true);
            animation6.play();

            PathTransition animation7 = new PathTransition();
            animation7.setNode(ghost7V);
            animation7.setDuration(Duration.seconds(4));
            animation7.setCycleCount(Animation.INDEFINITE);
            animation7.setPath(rectangle1);
            animation7.setAutoReverse(true);
            animation7.play();

            ghost1V.setOnMouseEntered(e -> enteredGhost(e));
            ghost2V.setOnMouseEntered(e -> enteredGhost(e));
            ghost3V.setOnMouseEntered(e -> enteredGhost(e));
            ghost4V.setOnMouseEntered(e -> enteredGhost(e));
            ghost5V.setOnMouseEntered(e -> enteredGhost(e));
            ghost6V.setOnMouseEntered(e -> enteredGhost(e));
            ghost7V.setOnMouseEntered(e -> enteredGhost(e));

        }
        else if(currentLevel==5) {
            ImageView ghost1V = new ImageView();
            ImageView ghost2V = new ImageView();
            ImageView ghost3V = new ImageView();
            ImageView ghost4V = new ImageView();

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

            ghost1V.setOnMouseEntered(e -> enteredGhost(e));
            ghost2V.setOnMouseEntered(e -> enteredGhost(e));
            ghost3V.setOnMouseEntered(e -> enteredGhost(e));
            ghost4V.setOnMouseEntered(e -> enteredGhost(e));

        }

        else if (currentLevel==6){
            ImageView ghost1V = new ImageView();
            ImageView ghost2V = new ImageView();
            ImageView ghost3V = new ImageView();
            ImageView ghost4V = new ImageView();

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

            ghost1V.setOnMouseEntered(e -> enteredGhost(e));
            ghost2V.setOnMouseEntered(e -> enteredGhost(e));
            ghost3V.setOnMouseEntered(e -> enteredGhost(e));
            ghost4V.setOnMouseEntered(e -> enteredGhost(e));

        }
    }
}
