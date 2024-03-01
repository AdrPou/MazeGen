package com.example.program.view;

import com.example.program.control.MainProgram;
import com.example.program.model.TimeThread;
import com.example.program.view.Menu.RightPanel;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class TimeThreadTest {

    private MainProgram mainProgram;
    private AudioPlayer audioPlayer;
    private RightPanel rightPanel;


    @BeforeEach
    public void setup(){
        mainProgram = new MainProgram();
        mainProgram.setMainPaneCampaign(new BorderPane());
        rightPanel = mock(RightPanel.class);
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
    public void testTimeThreadDecrementsSeconds() throws InterruptedException, FileNotFoundException {

        int seconds = 5;
        RightPanel panel = mock(RightPanel.class);
        TimeThread timeThread = new TimeThread(seconds, panel);

        timeThread.start();
        Thread.sleep(6000);


        assertEquals(0, timeThread.getSeconds());
    }

    @Test
    public void testTimeThreadTriggersGameOver() throws InterruptedException, FileNotFoundException {

        int seconds = 2;
        RightPanel panel = mock(RightPanel.class);
        TimeThread timeThread = new TimeThread(seconds, panel);

        timeThread.start();
        Thread.sleep(3000);

        assertTrue(timeThread.isGameOver());
    }

}
