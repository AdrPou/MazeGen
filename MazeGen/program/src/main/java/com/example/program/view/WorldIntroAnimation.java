package com.example.program.view;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @author André Eklund
 */
public class WorldIntroAnimation extends Pane {

    private String currentWorld;
    private Image worldIntro;

    /**
     * Default konstruktor
     */
    public WorldIntroAnimation() {
    }

    /**
     * Konstruktor som tar emot en sträng
     * Sätter sedan bilderna och animationen
     *
     * @param world tas emot och currentWorld sätts
     */
    public WorldIntroAnimation(String world) {
        this.currentWorld = world;
        setupImages();
        introAnimation();
    }

    /**
     * Metod som länkar worldIntro-objektet till en png-fil för currentWorld.
     */
    public void setupImages() {
        worldIntro = new Image(getClass().getResource("/com/example/program/files/worlds/World" + currentWorld + ".png").toString(), 600, 600, false, false);
    }

    /**
     * En fade transition för animation av bilden
     */
    public void introAnimation() {
        ImageView introView = new ImageView(worldIntro);
        introView.setStyle("fx-background-color: transparent;");
        FadeTransition ft = new FadeTransition(Duration.millis(4000), introView);
        getChildren().add(introView);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();

    }

    public Image getWorldIntro() {
        return worldIntro;
    }
}
