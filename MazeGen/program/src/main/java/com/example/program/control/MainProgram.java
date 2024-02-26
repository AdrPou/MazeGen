package com.example.program.control;

import com.example.program.model.KeyboardPlayer;
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
    private MapTemplate mapTemplate;
    private Scene menuScene;
    private Scene introScene;
    private Scene helpScene;
    private Scene settingsScene;
    private Scene chooseDimensionScene;
    private Intro intro;
    private Menu menu;
    private Help help;
    private Setting setting;
    private ChooseDimension chooseDimension;
    private Scene randomScene;
    private Scene campaignScene;
    private RightPanel rightPanel;
    private RightPanel rightPnlRndm;
    private MazeGenerator mazeGenerator;
    private GenerateNextLevel generateNextLevel;
    private World1Template world1Template;
    private World1Maps world1Maps;
    private WorldIntroAnimation introAnimation;
    private AudioPlayer audioPlayer;
    private GameOverScreen gameOverScreen;
    private Image cursorImage;
    private KeyboardPlayer player;
    private KeyBoardCampaign keyboardCampaign;
    private static final String BASE_PATH = "/com/example/program/files/";

    /**
     * En metod som startar programmet.
     * Metoden instanierar även de olika komponenterna.
     * @param primaryStage JavaFX top Container, huvudkomponenten till programmet.
     * @throws Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        audioPlayer = new AudioPlayer();
        audioPlayer.playIntroMusic();

        rightPanel = new RightPanel(this, "11", audioPlayer, null);
        rightPanel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        menu = new Menu(this, audioPlayer, rightPanel);
        intro = new Intro(this, audioPlayer);
        help = new Help(this, audioPlayer);
        setting = new Setting(this, audioPlayer);
        chooseDimension = new ChooseDimension(this, audioPlayer);
        introScene = new Scene(intro, 800, 600);
        menuScene = new Scene(menu, 800, 600);
        helpScene = new Scene(help, 800, 600);
        settingsScene = new Scene(setting, 800, 600);
        chooseDimensionScene = new Scene(chooseDimension, 800, 600);

        mainPaneRandomMaze = new BorderPane();
        mainPaneCampaign = new BorderPane();
        introAnimation = new WorldIntroAnimation();

        mainWindow = primaryStage;
        cursorImage = new Image(getClass().getResource(BASE_PATH + "imagecursor.png").toString());

        mainWindow.setTitle("Mazegen");
        mainWindow.setResizable(false);
        mainWindow.setOnCloseRequest(windowEvent -> System.exit(0));
        world1Maps = new World1Maps();
        mainPaneCampaign.setRight(rightPanel);

        rightPnlRndm = new RightPanel(this, "Random", audioPlayer, null);
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
     * @param dimension Storleken på labyrinten som ska genereras.
     * @throws FileNotFoundException
     */
    public void changeToRandomize(int dimension) throws FileNotFoundException {
        mazeGenerator = new MazeGenerator(dimension, true);
        generateNextLevel = new GenerateNextLevel(this, mainPaneRandomMaze, mazeGenerator, rightPanel, dimension);
        mapTemplate = new MapTemplate(mazeGenerator.getMaze(), this, generateNextLevel);
        mainPaneRandomMaze.setCenter(mapTemplate);
        mainWindow.setScene(randomScene);
        audioPlayer.playWorldIntroSound();
        audioPlayer.stopMusic();
        audioPlayer.playLevelMusic("forest"); //TODO fixa anpassad musik ?
    }

    /**
     * Byter scen till kampanjläget.
     * @throws FileNotFoundException
     */
    public void changeToCampaign() throws FileNotFoundException {

        world1Template = new World1Template(world1Maps.getLevel11(), 1, 3, this, rightPanel, 0, audioPlayer, 25, 5);


        audioPlayer.stopMusic();
        mainPaneCampaign.setCenter(world1Template);
        mainWindow.setScene(campaignScene);
        introAnimation = new WorldIntroAnimation("1");
        mainPaneCampaign.getChildren().add(introAnimation);
        introAnimation.setDisable(true);


        world1Template.updatePlayerImage(1, 8); // Sätter spelaren på pos 1,8 på spelplanen
        //TODO: samma här för både vanlig och keyboard
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
     * Vid gameOver körs denna metod.
     * Kör en enkel animation med texten "Game Over".
     */
    public void gameOver() {
        gameOverScreen = new GameOverScreen(this);
        mainPaneCampaign.getChildren().add(gameOverScreen);
    }

    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     * @param level Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public void nextWorld1Level(int level, int heartCrystals) throws FileNotFoundException, InterruptedException {

        if (level == 1) {
            System.out.println("hello");
            rightPanel.changeLevelCounter("12");
            mainPaneCampaign.setCenter(new World1Template(world1Maps.getLevel12(), 2, heartCrystals, this, rightPanel, 0, audioPlayer, 25));

        }
        else if (level == 2) {
            rightPanel.changeLevelCounter("13");
            mainPaneCampaign.setCenter(new World1Template(world1Maps.getLevel13(), 3, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
        }
        else if (level == 3) {
            rightPanel.changeLevelCounter("14");
            mainPaneCampaign.setCenter(new World1Template(world1Maps.getLevel14(), 4, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
        }
        else if (level == 4) {
            rightPanel.changeLevelCounter("15");
            mainPaneCampaign.setCenter(new World1Template(world1Maps.getLevel15(), 5, heartCrystals, this, rightPanel, 0, audioPlayer, 25));
        }
        else if (level == 5) {
            nextWorld2Level(1, heartCrystals);
        }

    }

    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     * @param level Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */

    public void nextWorld2Level(int level, int heartCrystals) throws FileNotFoundException, InterruptedException {

        World2Maps world2Maps = new World2Maps();

        if (level == 1) {
            rightPanel.changeLevelCounter("21");
            mainPaneCampaign.setCenter(new World2Template(world2Maps.getLevel21(), 2, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
            introAnimation = new WorldIntroAnimation("2");
            mainPaneCampaign.getChildren().add(introAnimation);
            introAnimation.setDisable(true);
            audioPlayer.playWorldIntroSound();
        }
        else if (level == 2) {
            rightPanel.changeLevelCounter("22");
            mainPaneCampaign.setCenter(new World2Template(world2Maps.getLevel22(), 3, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
        }
        else if (level == 3) {
            rightPanel.changeLevelCounter("23");
            mainPaneCampaign.setCenter(new World2Template(world2Maps.getLevel23(), 4, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
        }
        else if (level == 4) {
            rightPanel.changeLevelCounter("24");
            mainPaneCampaign.setCenter(new World2Template(world2Maps.getLevel24(), 5, heartCrystals, this, rightPanel, 1, audioPlayer, false, rightPanel));
        }
        else if (level == 5) {
            rightPanel.changeLevelCounter("25");
            mainPaneCampaign.setCenter(new World2Template(world2Maps.getLevel25(), 6, heartCrystals, this, rightPanel, 1, audioPlayer, true, rightPanel));
        }
        else if (level == 6) {
            nextWorld3Level(1, heartCrystals);
        }
    }

    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     * @param level Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public void nextWorld3Level(int level, int heartCrystals) throws FileNotFoundException, InterruptedException {

        World3Maps world3Maps = new World3Maps();

        if (level == 1) {
            rightPanel.changeLevelCounter("31");
            mainPaneCampaign.setCenter(new World3Template(world3Maps.getLevel31(), 2, heartCrystals, this, rightPanel, 2, audioPlayer));
            introAnimation = new WorldIntroAnimation("3");
            mainPaneCampaign.getChildren().add(introAnimation);
            introAnimation.setDisable(true);
            audioPlayer.playWorldIntroSound();
            audioPlayer.stopMusic();
            audioPlayer.playLevelMusic("lava");
        }
        else if (level == 2) {
            rightPanel.changeLevelCounter("32");
            mainPaneCampaign.setCenter(new World3Template(world3Maps.getLevel32(), 3, heartCrystals, this, rightPanel, 2, audioPlayer));
        }
        else if (level == 3) {
            rightPanel.changeLevelCounter("33");
            mainPaneCampaign.setCenter(new World3Template(world3Maps.getLevel33(), 4, heartCrystals, this, rightPanel, 2, audioPlayer));
        }
        else if (level == 4) {
            rightPanel.changeLevelCounter("34");
            mainPaneCampaign.setCenter(new World3Template(world3Maps.getLevel34(), 5, heartCrystals, this, rightPanel, 2, audioPlayer));
        }
        else if (level == 5) {
            rightPanel.changeLevelCounter("35");
            mainPaneCampaign.setCenter(new World3Template(world3Maps.getLevel35(), 6, heartCrystals, this, rightPanel, 2, audioPlayer));
        }
        else if (level == 6) {
            nextWorld4Level(1, heartCrystals);
        }
    }
    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     * @param level Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public void nextWorld4Level(int level, int heartCrystals) throws FileNotFoundException, InterruptedException {

        World4Maps world4Maps = new World4Maps();

        if (level == 1) {
            rightPanel.changeLevelCounter("41");
            mainPaneCampaign.setCenter(new World4Template(world4Maps.getLevel41(), 2, heartCrystals, this, rightPanel, 3, audioPlayer));
            introAnimation = new WorldIntroAnimation("4");
            mainPaneCampaign.getChildren().add(introAnimation);
            introAnimation.setDisable(true);
            audioPlayer.playWorldIntroSound();
            audioPlayer.stopMusic();
            audioPlayer.playLevelMusic("heaven");
        }
        else if (level == 2) {
            rightPanel.changeLevelCounter("42");
            mainPaneCampaign.setCenter(new World4Template(world4Maps.getLevel42(), 3, heartCrystals, this, rightPanel, 3, audioPlayer));
        }
        else if (level == 3) {
            rightPanel.changeLevelCounter("43");
            mainPaneCampaign.setCenter(new World4Template(world4Maps.getLevel43(), 4, heartCrystals, this, rightPanel, 3, audioPlayer));
        }
        else if (level == 4) {
            rightPanel.changeLevelCounter("44");
            mainPaneCampaign.setCenter(new World4Template(world4Maps.getLevel44(), 5, heartCrystals, this, rightPanel, 3, audioPlayer));
        }
        else if (level == 5) {
            rightPanel.changeLevelCounter("45");
            mainPaneCampaign.setCenter(new World4Template(world4Maps.getLevel45(), 6, heartCrystals, this, rightPanel, 3, audioPlayer));
        }
        else if (level == 6) {
            nextWorld5Level(1, heartCrystals);
        }
    }
    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     * @param level Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public void nextWorld5Level(int level, int heartCrystals) throws FileNotFoundException, InterruptedException {

        World5Maps world5Maps = new World5Maps();

        if (level == 1) {
            rightPanel.changeLevelCounter("51");
            mainPaneCampaign.setCenter(new World5Template(world5Maps.getLevel51(), 2, heartCrystals, this, rightPanel, 4, audioPlayer));
            introAnimation = new WorldIntroAnimation("5");
            mainPaneCampaign.getChildren().add(introAnimation);
            introAnimation.setDisable(true);
            audioPlayer.playWorldIntroSound();
            audioPlayer.stopMusic();
            audioPlayer.playLevelMusic("egypt");
        }
        else if (level == 2) {
            rightPanel.changeLevelCounter("52");
            mainPaneCampaign.setCenter(new World5Template(world5Maps.getLevel52(), 3, heartCrystals, this, rightPanel, 4, audioPlayer));
        }
        else if (level == 3) {
            rightPanel.changeLevelCounter("53");
            mainPaneCampaign.setCenter(new World5Template(world5Maps.getLevel53(), 4, heartCrystals, this, rightPanel, 4, audioPlayer));
        }
        else if (level == 4) {
            rightPanel.changeLevelCounter("54");
            mainPaneCampaign.setCenter(new World5Template(world5Maps.getLevel54(), 5, heartCrystals, this, rightPanel, 4, audioPlayer));
        }
        else if (level == 5) {
            rightPanel.changeLevelCounter("55");
            mainPaneCampaign.setCenter(new World5Template(world5Maps.getLevel55(), 6, heartCrystals, this, rightPanel, 4, audioPlayer));
        }
        else if (level == 6) {
            nextWorld6Level(1, heartCrystals);
        }
    }
    /**
     * Byter scen till en ny nivå i kampanjläget baserad på givna parametrar.
     * @param level Den aktuella nivån.
     * @param heartCrystals Spelarens aktuella liv.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public void nextWorld6Level(int level, int heartCrystals) throws FileNotFoundException, InterruptedException {

        World6Maps world6Maps = new World6Maps();

        if (level == 1) {
            rightPanel.changeLevelCounter("61");
            mainPaneCampaign.setCenter(new World6Template(world6Maps.getLevel61(), 2, heartCrystals, this, rightPanel, 5, audioPlayer));
            introAnimation = new WorldIntroAnimation("6");
            mainPaneCampaign.getChildren().add(introAnimation);
            introAnimation.setDisable(true);
            audioPlayer.playWorldIntroSound();
        }
        else if (level == 2) {
            rightPanel.changeLevelCounter("62");
            mainPaneCampaign.setCenter(new World6Template(world6Maps.getLevel62(), 3, heartCrystals, this, rightPanel, 5, audioPlayer));
        }
        else if (level == 3) {
            rightPanel.changeLevelCounter("63");
            mainPaneCampaign.setCenter(new World6Template(world6Maps.getLevel63(), 4, heartCrystals, this, rightPanel, 5, audioPlayer));
        }
        else if (level == 4) {
            rightPanel.changeLevelCounter("64");
            mainPaneCampaign.setCenter(new World6Template(world6Maps.getLevel64(), 5, heartCrystals, this, rightPanel, 5, audioPlayer));
        }
        else if (level == 5) {
            rightPanel.changeLevelCounter("65");
            mainPaneCampaign.setCenter(new World6Template(world6Maps.getLevel65(), 5, heartCrystals, this, rightPanel, 5, audioPlayer));
        }
    }

    public void setMainPaneCampaign(BorderPane mainPaneCampaign) {
        this.mainPaneCampaign = mainPaneCampaign;
    }

    /**
     * Main startar programmet.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
