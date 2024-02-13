package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import com.example.program.view.AudioPlayer;
import de.saxsys.javafx.test.JfxRunner;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import javafx.application.Platform;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MenuTest {

    private Image settings;
    private Image campaign;
    private Image randomize;



    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }


    //ANV5.3
    @Test
    public void testSettingsImageLoaded() {
        Menu menu = new Menu(new MainProgram(), new AudioPlayer(), null);
        menu.setupImages();
        settings = menu.getSettings();
        assertNotNull(settings);
    }

    //ANV5.1
    @Test
    public void testCampaignImageLoaded() {
        Menu menu = new Menu(new MainProgram(), new AudioPlayer(), null);
        menu.setupImages();
        campaign = menu.getCampaign();
        assertNotNull(campaign);
    }


    //ANV5.2
    @Test
    public void testRandomizeImageLoaded() {
        Menu menu = new Menu(new MainProgram(), new AudioPlayer(), null);
        menu.setupImages();
        randomize = menu.getRandomize();
        assertNotNull(randomize);
    }
}