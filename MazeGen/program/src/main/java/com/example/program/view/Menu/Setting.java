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

import java.awt.*;

public class Setting extends VBox {
    private MainProgram mainProgram;
    private Image pressMouse;
    private AudioPlayer audioPlayer;


    private static final String BASE_PATH = "/com/example/program/files/";

    public Setting (MainProgram mainProgram, AudioPlayer audioPlayer) {
        this.mainProgram = mainProgram;
        this.audioPlayer = audioPlayer;
        pressMouse = new javafx.scene.image.Image(getClass().getResource(BASE_PATH + "menuImages/helppicmouse.png").toString());
        setBackground();
        pressMouseAnimation();
        addListener();
    }

    public void setBackground() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource(BASE_PATH + "MenuBackground.jpg").toString(), 800, 600, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(backgroundImage));

    }

    public void pressMouseAnimation() {
        ImageView pressMouseView = new ImageView(pressMouse);
        pressMouseView.setStyle("fx-background-color: transparent;");
        pressMouseView.toFront();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), pressMouseView);
        fadeTransition.setCycleCount(Transition.INDEFINITE);
        pressMouseView.setOpacity(0);
        getChildren().add(pressMouseView);
    }

    public void addListener() {
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainProgram.changeToMenu();
                audioPlayer.playButtonSound();
            }
        });
    }

}
