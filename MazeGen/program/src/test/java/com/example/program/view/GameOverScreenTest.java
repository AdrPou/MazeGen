package com.example.program.view;

import com.example.program.control.MainProgram;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameOverScreenTest {

    private GameOverScreen gameOverScreen;
    private MainProgram mainProgram;

    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setup() {
        mainProgram = new MainProgram();
        gameOverScreen = new GameOverScreen(mainProgram);
    }

    @Test
    public void setUpImages() {
        gameOverScreen.setupImages();
        Image gameOverImage = gameOverScreen.getGameOver();
        assertNotNull(gameOverImage);
    }

    @Test
    public void gameOverAnimation() {

        gameOverScreen.setupImages();
        gameOverScreen.gameOverAnimation();

        // Vänta på att animationen ska slutföras
        try {
            Thread.sleep(5000); // Sätt tråden på sleeping mode i 5 sek zzz
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(gameOverScreen.getChildren().size() > 0); // Kolla att imageView lagts till i scenen
        assertTrue(gameOverScreen.getChildren().get(0) instanceof ImageView); // Kanske är överflödigt, kolla att det som lagts till är ImageVIew
        assertEquals(1, gameOverScreen.getChildren().get(0).getOpacity()); // Kolla att opacity av ImageView är 1 efteråt
    }

}
