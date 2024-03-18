package com.example.program.view.Menu;

import com.example.program.model.HighScore;
import javafx.application.Platform;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class HighScoreTest {

    private HighScore highScore;

    @BeforeAll
    public static void initJfxRuntime() {
        Platform.startup(() -> {
        });
    }

    @BeforeEach
    public void setup() {
        highScore = new HighScore();
    }

    @Test
    public void testReadList() {
        String[] actual = highScore.readList();
        String[] expected = new String[]{"1 Adam 100", "2 Adam 100", "3 Adam 100",
                "4 Adam 100", "5 Adam 100", "6 Adam 100",
                "7 Adam 100", "8 Adam 100", "9 Adam 100", "10 Adam 100"};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testWriteToList() {
        highScore.writeToList(new String[]{"Adam", "Adam", "Adam", "Adam", "Adam", "Adam", "Adam", "Adam", "Adam", "Adam"},
                new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 200});
        String[] actual = highScore.readList();
        String[] expected = new String[]{"1 Adam 100", "2 Adam 100", "3 Adam 100",
                "4 Adam 100", "5 Adam 100", "6 Adam 100",
                "7 Adam 100", "8 Adam 100", "9 Adam 100", "10 Adam 200"};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testCheckNewScoreNeg() {
        highScore.readList();
        boolean result = highScore.checkNewScore(200);
        assertFalse(result);
    }

    @Test
    public void testCheckNewScorePos() {
        highScore.readList();
        boolean result = highScore.checkNewScore(50);
        assertTrue(result);
    }

    @Test
    public void testSortList() {
        String[] expected = new String[]{"1 Adam 100", "2 Adam 100", "3 Adam 100", "4 Adam 100", "5 Adam 100",
        "6 Adam 100", "7 Adam 100", "8 Adam 100", "9 Adam 100", "10 Erik 200"};
        String[] names = {"Adam", "Erik", "Adam", "Adam", "Adam", "Adam", "Adam", "Adam", "Adam", "Adam"};
        int[] scores = {100, 200, 100, 100, 100, 100, 100, 100, 100, 100};
        highScore.setName(names);
        highScore.setScore(scores);
        highScore.sortList();
        highScore.writeToList(highScore.getName(), highScore.getScore());
        String[] actual = highScore.readList();

        assertArrayEquals(expected, actual);
    }

}
