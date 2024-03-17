package com.example.program.view.Randomize;

import com.example.program.control.MainProgram;
import com.example.program.model.Maps.World1Maps;
import com.example.program.view.AudioPlayer;
import com.example.program.view.Campaign.KeyBoardCampaign;
import com.example.program.view.Menu.HighScoreView;
import com.example.program.view.Menu.RightPanel;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class KeyboardRandomizeTest {

    private KeyBoardCampaign keyBoardCampaign;
    private MainProgram mainProgram;
    private World1Maps world1Maps;
    private RightPanel rightPanel;
    private AudioPlayer audioPlayer;
    private HighScoreView highScoreView;

    private Stage mainWindow;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        mainProgram = new MainProgram();
        mainProgram.setMainPaneRandomMaze(new BorderPane());
        mainWindow = mock(Stage.class);
        mainProgram.setMainWindow(mainWindow);
        mainProgram.initializeComponents();

        world1Maps = new World1Maps();
        rightPanel = mock(RightPanel.class);
        highScoreView = mock(HighScoreView.class);
        audioPlayer = mock(AudioPlayer.class);

    }

    @BeforeAll
    public static void initJfxRuntime() {
        Platform.startup(() -> {
        });
    }

    @AfterAll
    public static void shutdownJfxRuntime() {
        Platform.exit();
    }

    @Test
    public void testKeyboardRandomizeWithDimension10() {
        try {
            assertEquals(10, mainProgram.changeToRandomize(10));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testKeyboardRandomizeWithDimension14() {
        try {
            assertEquals(14, mainProgram.changeToRandomize(14));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testKeyboardRandomizeWithDimension18() {
        try {
            assertEquals(18, mainProgram.changeToRandomize(18));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testKeyboardRandomizeWithDimension28() {
        try {
            assertEquals(28, mainProgram.changeToRandomize(28));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }






}
