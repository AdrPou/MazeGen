package com.example.program.view;

import com.example.program.control.MainProgram;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorldIntroAnimationTest {


    private WorldIntroAnimation worldIntroAnimation;
    private MainProgram mainProgram;

    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setup() {
        mainProgram = new MainProgram();
        worldIntroAnimation = new WorldIntroAnimation("1"); // ettan är för vilken värld
    }


    @Test
    public void setUpImages() {
        worldIntroAnimation.setupImages();
        Image worldIntroImage = worldIntroAnimation.getWorldIntro();
        assertNotNull(worldIntroImage);
    }

    @Test
    public void introAnimation() {

        worldIntroAnimation.setupImages();
        worldIntroAnimation.introAnimation();

        try {
            Thread.sleep(5000); // Sätt tråden på sleeping mode i 5 sek zzz
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(worldIntroAnimation.getChildren().size() > 0); // Kolla att imageView lagts till i scenen
        assertTrue(worldIntroAnimation.getChildren().get(0) instanceof ImageView); // Kanske är överflödigt, kolla att det som lagts till är ImageVIew
        assertEquals(0, worldIntroAnimation.getChildren().get(0).getOpacity()); // Kolla att opacity av ImageView är 1 efteråt

        }
    }
