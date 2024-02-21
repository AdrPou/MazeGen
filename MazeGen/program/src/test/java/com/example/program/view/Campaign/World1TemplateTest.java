package com.example.program.view.Campaign;

import com.example.program.control.MainProgram;
import com.example.program.model.Maps.World1Maps;
import com.example.program.view.AudioPlayer;
import com.example.program.view.Menu.RightPanel;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class World1TemplateTest {

    private MainProgram mainProgram;
    private AudioPlayer audioPlayer;
    private World1Maps world1Maps;
    private RightPanel rightPanel;
    private javafx.scene.input.MouseEvent mockEvent;

    @BeforeEach
    public void setup() {
        mainProgram = new MainProgram();
        mainProgram.setMainPaneCampaign(new BorderPane());
        audioPlayer = mock(AudioPlayer.class);
        world1Maps = new World1Maps();
        rightPanel = mock(RightPanel.class);
        mockEvent = mock(javafx.scene.input.MouseEvent.class);
    }
    @BeforeAll
    public static void initJfxRuntime() {
        Platform.startup(() -> {
        });
    }
    @AfterAll
    public static void closeJfxRuntime() {
        Platform.exit();
    }

    @Test
    public void loseLivesDead() throws FileNotFoundException {
        setup();
        World1Template world1Template = new World1Template(world1Maps.getLevel12(), 2, 1, mainProgram, rightPanel, 0, audioPlayer, 25);
        assertEquals("dead", world1Template.enteredWall(mockEvent));

    }

    @Test
    public void loseLivesAlive() throws FileNotFoundException {
        setup();
        World1Template world1Template = new World1Template(world1Maps.getLevel12(), 2, 3, mainProgram, rightPanel, 0, audioPlayer, 25);
        assertEquals("alive", world1Template.enteredWall(mockEvent));
    }

}

