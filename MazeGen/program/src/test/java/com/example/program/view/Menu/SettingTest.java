package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import com.example.program.view.AudioPlayer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SettingTest {

    private Setting setting;

    @BeforeAll
    public static void initJfxRuntime() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setup() {
        setting = new Setting(new MainProgram(), new AudioPlayer());
    }

    @Test
    public void testToggleButtonKeyboardDisabled() {
        boolean keyboardButton = setting.getToggleButtonKeyboard();
        assertFalse(keyboardButton);
    }
    @Test
    public void testToggleButtonMouseEnabled() {
        boolean mouseButton = setting.getToggleButtonMouseIsEnabled();
        assertTrue(mouseButton);
    }
    @Test
    public void testSystemVolume() {
        setting.setSystemVolume(100);
        double systemVolume = setting.getVolume();
        assertEquals(100, systemVolume);
    }
    @Test
    public void testSoundImage() {
        Image sound = setting.getSoundView().getImage();
        assertNotNull(sound);
    }

}
