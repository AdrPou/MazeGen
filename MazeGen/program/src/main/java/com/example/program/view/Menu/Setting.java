package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import com.example.program.view.AudioPlayer;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class Setting extends VBox {
    private final MainProgram mainProgram;
    private final Image pressMouse;
    private ImageView soundView;
    private ImageView keyboardView;
    private ImageView backView;
    private final Slider volumeSlider;
    private final ToggleButton toggleButtonKeyboard;
    private final ToggleButton toggleButtonMouse;
    private final AudioPlayer audioPlayer;


    private static final String BASE_PATH = "/com/example/program/files/";

    public Setting(MainProgram mainProgram, AudioPlayer audioPlayer) {
        this.mainProgram = mainProgram;
        this.audioPlayer = audioPlayer;
        this.volumeSlider = new Slider();
        this.toggleButtonKeyboard = new ToggleButton();
        this.toggleButtonMouse = new ToggleButton();
        this.toggleButtonMouse.setDisable(true);
        pressMouse = new javafx.scene.image.Image(getClass().getResource(BASE_PATH + "menuImages/helppicmouse.png").toString());
        setBackground();
        setImages();
        setVolumeSlider();
        setToggleButton();
        addAllToChildren();
        pressMouseAnimation();
        //addListener();
        this.getStyleClass().add("setting");
        this.getStylesheets().add(getClass().getResource(BASE_PATH + "SettingsStyle.css").toExternalForm());
    }

    public void setBackground() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource(BASE_PATH + "MenuBackground.jpg").toString(), 800, 600, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(backgroundImage));

    }

    public void setImages() {
        Image sound = new Image(getClass().getResource(BASE_PATH + "texts/volume.png").toString(), 200, 80, false, false);
        soundView = new ImageView(sound);
        soundView.getStyleClass().add("soundView");

        Image keyboard = new Image(getClass().getResource(BASE_PATH + "texts/keyboard.png").toString(), 200, 80, false, false);
        keyboardView = new ImageView(keyboard);
        keyboardView.getStyleClass().add("keyboardView");

        Image back = new Image(getClass().getResource(BASE_PATH + "texts/back.png").toString(), 200, 80, false, false);
        backView = new ImageView(back);
        backView.getStyleClass().add("backView");
        backView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> mainProgram.changeToMenu());
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
            double volumeLevel = volumeSlider.getValue();
            setSystemVolume(volumeLevel / 100);

        });

    }

    public void setToggleButton() {
        toggleButtonKeyboard.getStyleClass().add("toggleButton");
        toggleButtonKeyboard.setText("Keyboard Control");

        toggleButtonKeyboard.setOnAction(event -> {
            System.out.println("Keyboard toggle button is activated!");
            toggleButtonKeyboard.setDisable(true); // True when keyboard is activated
            toggleButtonMouse.setDisable(false);
            mainProgram.setKeyboardControl(true);
        });

        toggleButtonMouse.getStyleClass().add("toggleButton");
        toggleButtonMouse.setText("Mouse Control");
        toggleButtonMouse.setOnAction(event -> {
            System.out.println("Mouse toggle button is activated!");
            toggleButtonKeyboard.setDisable(false);
            toggleButtonMouse.setDisable(true);
            mainProgram.setKeyboardControl(false);
        });
    }

    public void setSystemVolume(double volume) {
        audioPlayer.setVolume(volume);
    }

    public void pressMouseAnimation() {
        ImageView pressMouseView = new ImageView(pressMouse);
        pressMouseView.setStyle("fx-background-color: transparent;");
        pressMouseView.toFront();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), pressMouseView);
        fadeTransition.setCycleCount(Transition.INDEFINITE);
        pressMouseView.setOpacity(0);

    }

    public void addAllToChildren() {
        this.getChildren().addAll(soundView, volumeSlider, keyboardView, toggleButtonKeyboard, toggleButtonMouse, backView);
    }

    public boolean getToggleButtonKeyboard() {
        return toggleButtonKeyboard.isDisabled();
    }

}
