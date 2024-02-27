package com.example.program.view.Randomize;

import com.example.program.control.MainProgram;
import com.example.program.model.Maps.World1Maps;
import com.example.program.model.MazeGeneration.GenerateNextLevel;
import com.example.program.model.MazeGeneration.MazeGenerator;
import com.example.program.view.AudioPlayer;
import com.example.program.view.Menu.RightPanel;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GenerateNextLevelTest {


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
    public void chooseDimension() throws FileNotFoundException {
        setup();
        GenerateNextLevel generateNextLevel = new GenerateNextLevel(mainProgram, new BorderPane(), new MazeGenerator(10, false), rightPanel, 10);
        assertEquals(10, generateNextLevel.generateNewMaze());
    }

    @Test
    public void generateObjectInRandomize() throws FileNotFoundException {
        setup();
        MazeGenerator mazeGenerator = new MazeGenerator(10, true);
        GenerateNextLevel generateNextLevel = new GenerateNextLevel(mainProgram, new BorderPane(), mazeGenerator, rightPanel, 10);
        MapTemplate mapTemplate = new MapTemplate(mazeGenerator.getMaze(), mainProgram, generateNextLevel);

        assertEquals(4, mapTemplate.setupLevel());
    }




}
