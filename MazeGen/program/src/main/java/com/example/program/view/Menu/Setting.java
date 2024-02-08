package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import com.example.program.view.AudioPlayer;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.awt.*;

public class Setting extends VBox {
    private MainProgram mainProgram;
    private Image pressMouse;
    private Image sound;
    private ImageView soundView;
    private Slider volumeSlider;
    private AudioPlayer audioPlayer;


    private static final String BASE_PATH = "/com/example/program/files/";

    public Setting (MainProgram mainProgram, AudioPlayer audioPlayer) {
        this.mainProgram = mainProgram;
        this.audioPlayer = audioPlayer;
        this.volumeSlider = new Slider();
        pressMouse = new javafx.scene.image.Image(getClass().getResource(BASE_PATH + "menuImages/helppicmouse.png").toString());
        setBackground();
        setImages();
        setVolumeSlider();
        addAllToChildren();
        pressMouseAnimation();
        addListener();
        this.getStyleClass().add("setting");
        this.getStylesheets().add(getClass().getResource(BASE_PATH + "SettingsStyle.css").toExternalForm());
    }

    public void setBackground() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource(BASE_PATH + "MenuBackground.jpg").toString(), 800, 600, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(backgroundImage));

    }

    public void setImages(){
        sound = new Image(getClass().getResource(BASE_PATH + "texts/Help.png").toString(), 200, 80, false, false);
        soundView = new ImageView(sound);
        soundView.getStyleClass().add("soundView");
    }

    public void setVolumeSlider() {
        volumeSlider.getStyleClass().add("volumeSlider");

        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(30);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(50);
        volumeSlider.setMinorTickCount(5);
        volumeSlider.setBlockIncrement(10);

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int volumeLevel = (int) volumeSlider.getValue();
            setSystemVolume(volumeLevel / 100.0f);
        });

    }

    public void setSystemVolume(double volume){
        try {
            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
            for (Mixer.Info info : mixerInfo){
                Mixer mixer = AudioSystem.getMixer(info);
                if (mixer.isLineSupported(Port.Info.SPEAKER)){
                    Port port = (Port) mixer.getLine(Port.Info.SPEAKER);
                    port.open();
                    if (port.isControlSupported(FloatControl.Type.VOLUME)){
                        FloatControl volumeControl = (FloatControl) port.getControl(FloatControl.Type.VOLUME);
                        volumeControl.setValue((float) volume);
                    }
                    port.close();
                }
            }

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void pressMouseAnimation() {
        ImageView pressMouseView = new ImageView(pressMouse);
        pressMouseView.setStyle("fx-background-color: transparent;");
        pressMouseView.toFront();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), pressMouseView);
        fadeTransition.setCycleCount(Transition.INDEFINITE);
        pressMouseView.setOpacity(0);

    }

    public void addListener() {
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //mainProgram.changeToMenu();
                audioPlayer.playButtonSound();
            }
        });
    }

    public void addAllToChildren(){
        this.getChildren().addAll(soundView, volumeSlider);
    }

}
