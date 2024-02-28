package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import com.example.program.view.AudioPlayer;

/**
 * @author Viktor Näslund
 */

public class Help extends VBox {

    private final MainProgram mainProgram;
    private final Image pressMouse;
    private final AudioPlayer audioPlayer;

    private static final String BASE_PATH = "/com/example/program/files/";

    /**
     * Konstruktor som skapar pressMouse-objektet för animation och tar emot mainProgram och audioPlayer
     *
     * @param mainProgram tas emot och instansvariabeln sätts
     * @param audioPlayer tas emot och instansvariabeln sätts
     */
    public Help(MainProgram mainProgram, AudioPlayer audioPlayer) {
        pressMouse = new Image(getClass().getResource(BASE_PATH + "menuImages/helppicmouse.png").toString());
        this.mainProgram = mainProgram;
        this.audioPlayer = audioPlayer;
        setBackground();
        pressMouseAnimation();
        addListener();
    }

    /**
     * Metod som sätter bakgrundsbilden
     */
    public void setBackground() {
        BackgroundImage myBI = new BackgroundImage(new Image(getClass().getResource(BASE_PATH + "menuImages/helppicnew.png").toString(), 800, 600, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(myBI));
    }

    /**
     * Metod som får pressMouseView-objektet att blinka genom en FadeTransition
     */
    public void pressMouseAnimation() {
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


    /**
     * Lägger till listener för knapptryck och skiftar scen till menyn
     */
    public void addListener() {
        this.setOnMouseClicked(mouseEvent -> {
            mainProgram.changeToMenu();
            audioPlayer.playButtonSound();
        });
    }

}
