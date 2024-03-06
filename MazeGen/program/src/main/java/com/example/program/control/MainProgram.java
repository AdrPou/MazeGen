package com.example.program.control;


import com.example.program.model.HighScore;
import com.example.program.model.KeyboardPlayer;
import com.example.program.view.Randomize.TemplateKeyboard;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.example.program.model.Maps.*;
import com.example.program.model.MazeGeneration.GenerateNextLevel;
import com.example.program.view.AudioPlayer;
import com.example.program.view.Campaign.*;
import com.example.program.view.GameOverScreen;
import com.example.program.view.Randomize.MapTemplate;
import com.example.program.model.MazeGeneration.MazeGenerator;
import com.example.program.view.Menu.*;
import com.example.program.view.WorldIntroAnimation;

import java.io.FileNotFoundException;

/**
 * @author André Eklund
 * @edit Filip Örnling, Viktor Näslund, Sebastian Helin
 */

public class MainProgram extends Application {

    private Stage mainWindow;
    private BorderPane mainPaneRandomMaze;
    private BorderPane mainPaneCampaign;
    private Scene menuScene;
    private Scene helpScene;
    private Scene settingsScene;
    private Scene highScoreScene;
    private Scene chooseDimensionScene;
    private Setting setting;
    private Scene randomScene;
    private Scene campaignScene;
    private RightPanel rightPanel;
    private World1Maps world1Maps;
    private WorldIntroAnimation introAnimation;
    private AudioPlayer audioPlayer;
    private final HighScore highScore = new HighScore();
    private int totalScore = 0;
    private KeyboardPlayer player;
    private TemplateKeyboard templateKeyboard;
    private static final String BASE_PATH = "/com/example/program/files/";
    private boolean keyboardIsOn; // To know if keyboard is on or not so that nextLevel is created for KeyboardTemplate
    private String[] scores;
    private HighScoreView highScoreView;


    /**
     * En metod som startar programmet.
     * Metoden instanierar även de olika komponenterna.
     *
     * @param primaryStage JavaFX top Container, huvudkomponenten till programmet.
     * @throws Exception if something goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        audioPlayer = new AudioPlayer();
        audioPlayer.playIntroMusic();


        rightPanel = new RightPanel(this, "11", audioPlayer, null);
        rightPanel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));


        scores = highScore.readList();

        Menu menu = new Menu(this, audioPlayer, rightPanel);
        Intro intro = new Intro(this, audioPlayer);
        Help help = new Help(this, audioPlayer);
        setting = new Setting(this, audioPlayer);

        ChooseDimension chooseDimension = new ChooseDimension(this, audioPlayer);
        Scene introScene = new Scene(intro, 800, 600);
        menuScene = new Scene(menu, 800, 600);
        helpScene = new Scene(help, 800, 600);
        settingsScene = new Scene(setting, 800, 600);
        highScoreView = new HighScoreView(this, audioPlayer, scores);
        highScoreScene = new Scene(highScoreView, 800, 600);
        chooseDimensionScene = new Scene(chooseDimension, 800, 600);

        mainPaneRandomMaze = new BorderPane();
        mainPaneCampaign = new BorderPane();
        introAnimation = new WorldIntroAnimation();

        mainWindow = primaryStage;
        Image cursorImage = new Image(getClass().getResource(BASE_PATH + "imagecursor.png").toString());

        mainWindow.setTitle("Mazegen");
        mainWindow.setResizable(false);
        mainWindow.setOnCloseRequest(windowEvent -> System.exit(0));
        world1Maps = new World1Maps();
        mainPaneCampaign.setRight(rightPanel);

        RightPanel rightPnlRndm = new RightPanel(this, "Random", audioPlayer, null);
        rightPnlRndm.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        mainPaneRandomMaze.setRight(rightPnlRndm);

        campaignScene = new Scene(mainPaneCampaign, 800, 600);
        randomScene = new Scene(mainPaneRandomMaze, 800, 600);

        mainWindow.setScene(introScene);
        mainWindow.show();

        introScene.setCursor(new ImageCursor(cursorImage));
        menuScene.setCursor(new ImageCursor(cursorImage));
        campaignScene.setCursor(new ImageCursor(cursorImage));
        chooseDimensionScene.setCursor(new ImageCursor(cursorImage));
        helpScene.setCursor(new ImageCursor(cursorImage));
        settingsScene.setCursor(new ImageCursor(cursorImage));
        highScoreScene.setCursor(new ImageCursor(cursorImage));
        randomScene.setCursor(new ImageCursor(cursorImage));
    }


    /**
     * Byter scen till huvudmenyn.
     */
    public void changeToMenu() {
        mainWindow.setScene(menuScene);
    }


    /**
     * Byter scen till Randomize.
     *
     * @param dimension Storleken på labyrinten som ska genereras.
     * @throws FileNotFoundException if file not found.
     */
    public void changeToRandomize(int dimension) throws FileNotFoundException {
        MazeGenerator mazeGenerator = new MazeGenerator(dimension, true);
        GenerateNextLevel generateNextLevel = new GenerateNextLevel(this, mainPaneRandomMaze, mazeGenerator, rightPanel, dimension);
        MapTemplate mapTemplate = new MapTemplate(mazeGenerator.getMaze(), this, generateNextLevel);
        //templateKeyboard = new TemplateKeyboard(mazeGenerator.getMaze(), this, generateNextLevel);
        mainPaneRandomMaze.setCenter(mapTemplate);
        //mainPaneRandomMaze.setCenter(templateKeyboard);
        mainWindow.setScene(randomScene);
        audioPlayer.playWorldIntroSound();
        audioPlayer.stopMusic();
        audioPlayer.playLevelMusic("forest"); //TODO fixa anpassad musik för banorna?
    }


    /**
     * Byter scen till kampanjläget.
     *
     * @throws FileNotFoundException if file not found.
     */
    public void changeToCampaign() throws FileNotFoundException {
        if (setting.getToggleButtonKeyboard()) {
            KeyBoardCampaign keyboardCampaign = new KeyBoardCampaign(world1Maps.getLevel11(), 1, 3, this, rightPanel, 0, audioPlayer, 25); // TODO changed level for testing purposes
            mainPaneCampaign.setCenter(keyboardCampaign);
            setupCampaignAfterInitializationOfTemplate();
            keyboardIsOn = true;


            // for Testing purposes


            //nextWorld1Level(3, 3);
            //nextWorld6Level(4, 3);


        } else {
            World1Template world1Template = new World1Template(world1Maps.getLevel11(), 1, 3, this, rightPanel, 0, audioPlayer, 25);
            mainPaneCampaign.setCenter(world1Template);
            setupCampaignAfterInitializationOfTemplate();


            // for Testing purposes
            /*
            try {
                nextWorld2Level(5, 3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

             */




        }


        // TODO: lägg in check här för world1Template eller KeyBoardCampaign!
        //keyboardCampaign = new KeyBoardCampaign(world1Maps.getLevel11(), 1, 3, this, rightPanel, 0, audioPlayer, 25);
        //TODO: samma här för både vanlig och keyboard
    }


    /**
     * This method sets up the campaign scene after the template has been initialized. It stops the audio
     * and sets the center of the campaign scene to the template, sets the scene to the campaign scene,
     * adds the intro animation to the campaign scene, and sets the disable property of the animation to
     * true.
     */
    private void setupCampaignAfterInitializationOfTemplate() {
        rightPanel.setSoundImage(true);
        rightPanel.setMusicImage(true);
        audioPlayer.stopMusic();
        mainWindow.setScene(campaignScene);
        introAnimation = new WorldIntroAnimation("1");
        mainPaneCampaign.getChildren().add(introAnimation);
        introAnimation.setDisable(true);
    }


    /**
     * Byter scen till den del av menyn där användaren får välja dimension på labyrinten.
     */
    public void chooseDimension() {
        mainWindow.setScene(chooseDimensionScene);
    }


    /**
     * Byter scen till hjälpfönstret.
     */
    public void changeToHelp() {
        mainWindow.setScene(helpScene);
    }


    public void changeToSettings() {
        mainWindow.setScene(settingsScene);
    }


    /**
     * Byter scen till highscorefönstret.
     */
    public void changeToHighScore() {
        scores = highScore.readList();
        highScoreView = new HighScoreView(this, audioPlayer, scores);
        highScoreScene = new Scene(highScoreView, 800, 600);
        mainWindow.setScene(highScoreScene);
    }

    /**
     * Vid gameOver körs denna metod.
     * Kör en enkel animation med texten "Game Over".
     */
    public void gameOver() {
        System.out.println("Game Over");
        highScore.checkNewScore(totalScore);
        highScoreView.displayHighScores();
        GameOverScreen gameOverScreen = new GameOverScreen(this);
        mainPaneCampaign.getChildren().add(gameOverScreen);
    }


    /**
     * Adds the time from the previous level to your total score.
     *
     * @param seconds it took to complete the previous level.
     */
    public void totalScore(int seconds) {
        totalScore += seconds;
    }

    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     *
     * @param level         Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException if file not found.
     * @throws InterruptedException  if thread was interrupted.
     */
    public void nextWorld1Level(int level, int heartCrystals) throws FileNotFoundException, InterruptedException {
        switch (level) {
            case 1:
                rightPanel.changeLevelCounter("12");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new KeyBoardCampaign(world1Maps.getLevel12(), 2, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
                } else {
                    mainPaneCampaign.setCenter(new World1Template(world1Maps.getLevel12(), 2, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
                }
                break;
            case 2:
                rightPanel.changeLevelCounter("13");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new KeyBoardCampaign(world1Maps.getLevel13(), 3, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
                } else {
                    mainPaneCampaign.setCenter(new World1Template(world1Maps.getLevel13(), 3, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
                }
                break;
            case 3:
                rightPanel.changeLevelCounter("14");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new KeyBoardCampaign(world1Maps.getLevel14(), 4, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
                } else {
                    mainPaneCampaign.setCenter(new World1Template(world1Maps.getLevel14(), 4, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
                }
                break;
            case 4:
                rightPanel.changeLevelCounter("15");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new KeyBoardCampaign(world1Maps.getLevel15(), 5, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
                } else {
                    mainPaneCampaign.setCenter(new World1Template(world1Maps.getLevel15(), 5, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
                }
                break;
            case 5:
                nextWorld2Level(1, heartCrystals);
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
    }


    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     *
     * @param level         Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException if file not found.
     * @throws InterruptedException  if thread was interrupted.
     */
    public void nextWorld2Level(int level, int heartCrystals) throws FileNotFoundException, InterruptedException {

        World2Maps world2Maps = new World2Maps();

        switch (level) {
            case 1:
                rightPanel.changeLevelCounter("21");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard2Template(world2Maps.getLevel21(), 2, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
                } else {
                    mainPaneCampaign.setCenter(new World2Template(world2Maps.getLevel21(), 2, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
                }
                introAnimation = new WorldIntroAnimation("2");
                mainPaneCampaign.getChildren().add(introAnimation);
                introAnimation.setDisable(true);
                audioPlayer.playWorldIntroSound();
                break;
            case 2:
                rightPanel.changeLevelCounter("22");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard2Template(world2Maps.getLevel22(), 3, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
                } else {
                    mainPaneCampaign.setCenter(new World2Template(world2Maps.getLevel22(), 3, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
                }
                break;
            case 3:
                rightPanel.changeLevelCounter("23");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard2Template(world2Maps.getLevel23(), 4, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
                } else {
                    mainPaneCampaign.setCenter(new World2Template(world2Maps.getLevel23(), 4, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
                }
                break;
            case 4:
                rightPanel.changeLevelCounter("24");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard2Template(world2Maps.getLevel24(), 5, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
                } else {
                    mainPaneCampaign.setCenter(new World2Template(world2Maps.getLevel24(), 5, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
                }
                break;
            case 5:
                rightPanel.changeLevelCounter("25");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard2Template(world2Maps.getLevel25(), 6, heartCrystals, this, rightPanel, 1, audioPlayer, true, rightPanel));
                } else {
                    mainPaneCampaign.setCenter(new World2Template(world2Maps.getLevel25(), 6, heartCrystals, this, rightPanel, 1, audioPlayer, true, rightPanel));
                }
                break;
            case 6:
                nextWorld3Level(1, heartCrystals);
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
    }


    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     *
     * @param level         Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException if file not found.
     */
    public void nextWorld3Level(int level, int heartCrystals) throws FileNotFoundException {

        World3Maps world3Maps = new World3Maps();

        switch (level) {
            case 1:
                rightPanel.changeLevelCounter("31");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard3Template(world3Maps.getLevel31(), 2, heartCrystals, this, rightPanel, 2, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World3Template(world3Maps.getLevel31(), 2, heartCrystals, this, rightPanel, 2, audioPlayer));
                }
                introAnimation = new WorldIntroAnimation("3");
                mainPaneCampaign.getChildren().add(introAnimation);
                introAnimation.setDisable(true);
                audioPlayer.playWorldIntroSound();
                audioPlayer.stopMusic();
                audioPlayer.playLevelMusic("lava");
                break;
            case 2:
                rightPanel.changeLevelCounter("32");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard3Template(world3Maps.getLevel32(), 3, heartCrystals, this, rightPanel, 2, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World3Template(world3Maps.getLevel32(), 3, heartCrystals, this, rightPanel, 2, audioPlayer));
                }
                break;
            case 3:
                rightPanel.changeLevelCounter("33");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard3Template(world3Maps.getLevel33(), 4, heartCrystals, this, rightPanel, 2, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World3Template(world3Maps.getLevel33(), 4, heartCrystals, this, rightPanel, 2, audioPlayer));
                }
                break;
            case 4:
                rightPanel.changeLevelCounter("34");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard3Template(world3Maps.getLevel34(), 5, heartCrystals, this, rightPanel, 2, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World3Template(world3Maps.getLevel34(), 5, heartCrystals, this, rightPanel, 2, audioPlayer));
                }
                break;
            case 5:
                rightPanel.changeLevelCounter("35");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard3Template(world3Maps.getLevel35(), 6, heartCrystals, this, rightPanel, 2, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World3Template(world3Maps.getLevel35(), 6, heartCrystals, this, rightPanel, 2, audioPlayer));
                }
                break;
            case 6:
                nextWorld4Level(1, heartCrystals);
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
    }



    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     *
     * @param level         Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException if file not found.
     */
    public void nextWorld4Level(int level, int heartCrystals) throws FileNotFoundException {

        World4Maps world4Maps = new World4Maps();

        switch (level) {
            case 1:
                rightPanel.changeLevelCounter("41");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard4Template(world4Maps.getLevel41(), 2, heartCrystals, this, rightPanel, 3, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World4Template(world4Maps.getLevel41(), 2, heartCrystals, this, rightPanel, 3, audioPlayer));
                }
                introAnimation = new WorldIntroAnimation("4");
                mainPaneCampaign.getChildren().add(introAnimation);
                introAnimation.setDisable(true);
                audioPlayer.playWorldIntroSound();
                audioPlayer.stopMusic();
                audioPlayer.playLevelMusic("heaven");
                break;
            case 2:
                rightPanel.changeLevelCounter("42");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard4Template(world4Maps.getLevel42(), 3, heartCrystals, this, rightPanel, 3, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World4Template(world4Maps.getLevel42(), 3, heartCrystals, this, rightPanel, 3, audioPlayer));
                }
                break;
            case 3:
                rightPanel.changeLevelCounter("43");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard4Template(world4Maps.getLevel43(), 4, heartCrystals, this, rightPanel, 3, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World4Template(world4Maps.getLevel43(), 4, heartCrystals, this, rightPanel, 3, audioPlayer));
                }
                break;
            case 4:
                rightPanel.changeLevelCounter("44");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard4Template(world4Maps.getLevel44(), 5, heartCrystals, this, rightPanel, 3, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World4Template(world4Maps.getLevel44(), 5, heartCrystals, this, rightPanel, 3, audioPlayer));
                }
                break;
            case 5:
                rightPanel.changeLevelCounter("45");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard4Template(world4Maps.getLevel45(), 6, heartCrystals, this, rightPanel, 3, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World4Template(world4Maps.getLevel45(), 6, heartCrystals, this, rightPanel, 3, audioPlayer));
                }
                break;
            case 6:
                nextWorld5Level(1, heartCrystals);
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
    }



    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     *
     * @param level         Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException if file not found.
     */
    public void nextWorld5Level(int level, int heartCrystals) throws FileNotFoundException {

        World5Maps world5Maps = new World5Maps();

        switch (level) {
            case 1:
                rightPanel.changeLevelCounter("51");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard5Template(world5Maps.getLevel51(), 2, heartCrystals, this, rightPanel, 4, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World5Template(world5Maps.getLevel51(), 2, heartCrystals, this, rightPanel, 4, audioPlayer));
                }
                introAnimation = new WorldIntroAnimation("5");
                mainPaneCampaign.getChildren().add(introAnimation);
                introAnimation.setDisable(true);
                audioPlayer.playWorldIntroSound();
                audioPlayer.stopMusic();
                audioPlayer.playLevelMusic("egypt");
                break;
            case 2:
                rightPanel.changeLevelCounter("52");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard5Template(world5Maps.getLevel52(), 3, heartCrystals, this, rightPanel, 4, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World5Template(world5Maps.getLevel52(), 3, heartCrystals, this, rightPanel, 4, audioPlayer));
                }
                break;
            case 3:
                rightPanel.changeLevelCounter("53");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard5Template(world5Maps.getLevel53(), 4, heartCrystals, this, rightPanel, 4, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World5Template(world5Maps.getLevel53(), 4, heartCrystals, this, rightPanel, 4, audioPlayer));
                }
                break;
            case 4:
                rightPanel.changeLevelCounter("54");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard5Template(world5Maps.getLevel54(), 5, heartCrystals, this, rightPanel, 4, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World5Template(world5Maps.getLevel54(), 5, heartCrystals, this, rightPanel, 4, audioPlayer));
                }
                break;
            case 5:
                rightPanel.changeLevelCounter("55");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard5Template(world5Maps.getLevel55(), 6, heartCrystals, this, rightPanel, 4, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World5Template(world5Maps.getLevel55(), 6, heartCrystals, this, rightPanel, 4, audioPlayer));
                }
                break;
            case 6:
                nextWorld6Level(1, heartCrystals);
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
    }



    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     *
     * @param level         Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException if file not found.
     */
    public void nextWorld6Level(int level, int heartCrystals) throws FileNotFoundException {

        World6Maps world6Maps = new World6Maps();

        switch (level) {
            case 1:
                rightPanel.changeLevelCounter("61");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard6Template(world6Maps.getLevel61(), 2, heartCrystals, this, rightPanel, 5, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World6Template(world6Maps.getLevel61(), 2, heartCrystals, this, rightPanel, 5, audioPlayer));
                }
                introAnimation = new WorldIntroAnimation("6");
                mainPaneCampaign.getChildren().add(introAnimation);
                introAnimation.setDisable(true);
                audioPlayer.playWorldIntroSound();
                break;
            case 2:
                rightPanel.changeLevelCounter("62");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard6Template(world6Maps.getLevel62(), 3, heartCrystals, this, rightPanel, 5, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World6Template(world6Maps.getLevel62(), 3, heartCrystals, this, rightPanel, 5, audioPlayer));
                }
                break;
            case 3:
                rightPanel.changeLevelCounter("63");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard6Template(world6Maps.getLevel63(), 4, heartCrystals, this, rightPanel, 5, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World6Template(world6Maps.getLevel63(), 4, heartCrystals, this, rightPanel, 5, audioPlayer));
                }
                break;
            case 4:
                rightPanel.changeLevelCounter("64");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard6Template(world6Maps.getLevel64(), 5, heartCrystals, this, rightPanel, 5, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World6Template(world6Maps.getLevel64(), 5, heartCrystals, this, rightPanel, 5, audioPlayer));
                }
                break;
            case 5:
                rightPanel.changeLevelCounter("65");
                if (keyboardIsOn) {
                    mainPaneCampaign.setCenter(new Keyboard6Template(world6Maps.getLevel65(), 5, heartCrystals, this, rightPanel, 5, audioPlayer));
                } else {
                    mainPaneCampaign.setCenter(new World6Template(world6Maps.getLevel65(), 5, heartCrystals, this, rightPanel, 5, audioPlayer));
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
    }



    /**
     * Main startar programmet.
     *
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void setKeyboardControl(boolean b) {
        keyboardIsOn = b;
    }
}
