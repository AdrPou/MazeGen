package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import com.example.program.view.AudioPlayer;

/**
 * @author Viktor Näslund
 */

public class Intro extends Pane {

    private MainProgram mainProgram;
    private Image spaceImage;
    private Image mazeGen;
    private Image mazeGenGlow;
    private Image pressMouse;
    private AudioPlayer audioPlayer;

    private static final String INTRO_PICS_PATH = "/com/example/program/files/intropics/";


    /**
     * Konstruktor som tar emot mainProgram och audioPlayer
     * Kör sedan metoder för bakgrundsbild, bilder i övrigt och listener
     * @param mainProgram tas emot och sätts
     * @param audioPlayer tas emot och sätts
     */
    public Intro(MainProgram mainProgram, AudioPlayer audioPlayer){
        this.mainProgram = mainProgram;
        this.audioPlayer = audioPlayer;
        setBackground();
        addListener();
        setupImages();
        introAnimation();

    }

    /**
     * Metod som sätter bakgrundsbilden
     */
    public void setBackground(){
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource(INTRO_PICS_PATH + "1.png").toString(), 800, 600, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(backgroundImage));
    }

    /**
     * Metod som länkar Image-objekten till png-filer
     */
    public void setupImages(){
        spaceImage = new Image(getClass().getResource(INTRO_PICS_PATH + "2.png").toString(), 800, 600, false, false);
        mazeGen = new Image(getClass().getResource(INTRO_PICS_PATH + "3.png").toString(), 800, 600, false, false);
        mazeGenGlow = new Image(getClass().getResource(INTRO_PICS_PATH + "4.png").toString(), 800, 600, false, false);
        pressMouse = new Image(getClass().getResource(INTRO_PICS_PATH + "5.png").toString(), 800, 600, false, false);
    }


    /**
     * Lägger till listener för att stänga av ljudet och byta scen
     */
    public void addListener(){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                audioPlayer.stopMusic();
                audioPlayer.playButtonSound();
                mainProgram.changeToMenu();
            }
        });
    }


    /**
     * Metod som animerar in bilder med olika delays och durations
     * med FadeTransitions
     */
    public void introAnimation() {
        ImageView introView = new ImageView(spaceImage);
        introView.setStyle("fx-background-color: transparent;");
        FadeTransition ft = new FadeTransition(Duration.millis(3000), introView);

        getChildren().add(introView);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();


        ImageView mazeGenView = new ImageView(mazeGen);
        mazeGenView.setStyle("fx-background-color: transparent;");
        mazeGenView.toFront();
        FadeTransition ft2 = new FadeTransition(Duration.millis(2000), mazeGenView);
        mazeGenView.setOpacity(0);
        getChildren().add(mazeGenView);
        ft2.setDelay(Duration.millis(2000));

        ft2.setFromValue(0);
        ft2.setToValue(1);
        ft2.play();

        ImageView mazeGenGlowView = new ImageView(mazeGenGlow);
        mazeGenGlowView.setStyle("fx-background-color: transparent;");
        mazeGenGlowView.toFront();
        FadeTransition ft3 = new FadeTransition(Duration.millis(1500), mazeGenGlowView);
        mazeGenGlowView.setOpacity(0);
        getChildren().add(mazeGenGlowView);
        ft3.setDelay(Duration.millis(3500));

        ft3.setFromValue(0);
        ft3.setToValue(1);
        ft3.play();

        ImageView pressMouseView = new ImageView(pressMouse);
        pressMouseView.setStyle("fx-background-color: transparent;");
        pressMouseView.toFront();
        FadeTransition ft4 = new FadeTransition(Duration.millis(1500), pressMouseView);
        ft4.setCycleCount(Transition.INDEFINITE);
        pressMouseView.setOpacity(0);
        getChildren().add(pressMouseView);
        ft4.setDelay(Duration.millis(5500));

        ft4.setFromValue(0);
        ft4.setToValue(1);
        ft4.play();

    }

}
