package com.example.program.view.Campaign;

import com.example.program.control.MainProgram;
import com.example.program.model.Maps.World1Maps;
import com.example.program.view.AudioPlayer;
import com.example.program.view.Menu.RightPanel;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class KeyBoardCampaignTest {

    private KeyBoardCampaign keyBoardCampaign;
    private MainProgram mainProgram;
    private World1Maps world1Maps;
    private RightPanel rightPanel;
    private AudioPlayer audioPlayer;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        mainProgram = new MainProgram();
        mainProgram.setMainPaneCampaign(new BorderPane());
        world1Maps = new World1Maps();
        rightPanel = mock(RightPanel.class);
        audioPlayer = mock(AudioPlayer.class);
        keyBoardCampaign = new KeyBoardCampaign(world1Maps.getLevel11(), 1, 3, mainProgram, rightPanel, 0, audioPlayer, 25);
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
    public void testMovingInAllDirections() throws FileNotFoundException, InterruptedException {
        // Initialize a level with a clear path
        keyBoardCampaign = new KeyBoardCampaign(world1Maps.getLevel11(), 1, 3, mainProgram, rightPanel, 0, audioPlayer, 25);

        // Move player in all directions
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false));
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false));
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false));
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false));
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false));

        // Assert that player's position is as expected after the movements
        assertEquals(2, keyBoardCampaign.getPlayer().getX());
    }

    @Test
    void goOutsideZone() throws FileNotFoundException, InterruptedException {
        // Save player's position before trying to go outside the zone
        int oldX = keyBoardCampaign.getPlayer().getX();

        // Move player outside the zone
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false));

        // Assert that player's position hasn't changed
        // Assert that heart crystals have been decremented
        assertEquals(oldX, keyBoardCampaign.getPlayer().getX());
        assertEquals(2, keyBoardCampaign.getHeartCrystals());
    }

    @Test
    void hitWall() throws FileNotFoundException, InterruptedException {
        // Move player to a position that has a wall upside
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);
        keyBoardCampaign.handleKeyPressed(keyEvent);

        // Save player's position before hitting a wall
        int oldY = keyBoardCampaign.getPlayer().getY();

        // Hit the wall
        keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);
        keyBoardCampaign.handleKeyPressed(keyEvent);

        // Assert that player's position hasn't changed
        // Assert that heart crystals have been decremented
        assertEquals(oldY, keyBoardCampaign.getPlayer().getY());
        assertEquals(2, keyBoardCampaign.getHeartCrystals());
    }


    @Test
    void hitGhost() throws FileNotFoundException, InterruptedException {
        // Initialize a level with a ghost, with a default value of 3 hearts for the player
        keyBoardCampaign = new Keyboard2Template(world1Maps.getLevel11(), 5, 3, mainProgram, rightPanel, 0, audioPlayer, true, rightPanel);

        // Move player to a position that has a ghost and wait for the ghost to hit the player
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false));
        Thread.sleep(1000);

        // Assert that heart crystals have been decremented
        assertNotEquals(3, keyBoardCampaign.getHeartCrystals());
    }

    @Test
    void pickupCollectible() throws FileNotFoundException, InterruptedException {
        // Move a player to a position that has a collectible
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false));
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false));

        // Assert that collectibles obtained has been incremented
        assertEquals(1, keyBoardCampaign.getCollectiblesObtained());
    }

    @Test
    void pickupHeartCollectible() throws FileNotFoundException, InterruptedException {
        // Initialize a level with a heart collectible and give the player only 2 hearts
        keyBoardCampaign = new KeyBoardCampaign(world1Maps.getLevel15(), 5, 2, mainProgram, rightPanel, 0, audioPlayer, 25);

        // Move a player to a position that has a heart collectible
        keyBoardCampaign.getPlayer().move(1,7);
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false));

        // Assert that heart collectible is obtained / heart crystals has been incremented
        assertEquals(3, keyBoardCampaign.getHeartCrystals());
    }


    @Test
    void pickupPickaxeCollectible() throws FileNotFoundException, InterruptedException {
        // Initialize a level with an axe collectible
        keyBoardCampaign = new KeyBoardCampaign(world1Maps.getLevel15(), 5, 3, mainProgram, rightPanel, 0, audioPlayer, 25);

        // Move a player to a position that has an axe collectible
        keyBoardCampaign.getPlayer().move(3,2);
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false));

        // Assert that axe collectible is obtained / axe has been incremented
        assertTrue(keyBoardCampaign.isPickaxeObtained());
    }

/*
    @Test
    void checkGoal() throws FileNotFoundException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        mainProgram.setRightPanel(rightPanel);
        // Initialize a level with a goal
        keyBoardCampaign = new KeyBoardCampaign(world1Maps.getLevel11(), 1, 3, mainProgram, rightPanel, 0, audioPlayer, 25);

        Field field = KeyBoardCampaign.class.getDeclaredField("allCollectiblesObtained");
        field.setAccessible(true);
        field.set(keyBoardCampaign, true);

        // Move a player to a position that has a goal
        keyBoardCampaign.getPlayer().move(7,2);
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false));
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false));


        // Assert that the level is completed
        assertEquals(2, keyBoardCampaign.getCurrentLevel());
    }
 */



    @Test
    void hitBreakableWall() throws FileNotFoundException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        // Initialize a level with a breakable wall
        keyBoardCampaign = new KeyBoardCampaign(world1Maps.getLevel15(), 5, 3, mainProgram, rightPanel, 0, audioPlayer, 25);


        // Set the pickaxeObtained to true
        Field field = KeyBoardCampaign.class.getDeclaredField("pickaxeObtained");
        field.setAccessible(true);
        field.set(keyBoardCampaign, true);


        // Move a player to a position that has a breakable wall and break it
        keyBoardCampaign.getPlayer().move(2,5);
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false));

        // Assert that the breakable wall has been hit by asserting that the pickaxeObtained is false
        assertFalse(keyBoardCampaign.isPickaxeObtained());
    }

    @Test
    void die() throws FileNotFoundException, InterruptedException {
        // Initialize a level with a default value of 1 heart for the player
        keyBoardCampaign = new KeyBoardCampaign(world1Maps.getLevel11(), 1, 1, mainProgram, rightPanel, 0, audioPlayer, 25);

        // Hit a wall or go outside the zone
        keyBoardCampaign.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false));

        // Assert that the player has died
        assertTrue(keyBoardCampaign.isGameOver());
    }

}