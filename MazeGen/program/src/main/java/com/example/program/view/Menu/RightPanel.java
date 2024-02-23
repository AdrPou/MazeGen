package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import com.example.program.model.TimeThread;
import com.example.program.model.TotalTime;
import com.example.program.view.AudioPlayer;

import java.io.FileNotFoundException;

/**
 * @author Filip Örnling
 * @edit Viktor Näslund, Sebastian Helin
 */

public class RightPanel extends GridPane {

    private MainProgram mainProgram;
    private String gameMode;
    private int seconds;

    private Image imageMenu;
    private ImageView menuView;

    private Image levelNumber;
    private ImageView currentLevelView;
    private Label levelLabel;
    private Thread timer;

    private Image heart;
    private ImageView currentHeartView;
    private Label heartLabel;

    private Image pickaxe;
    private ImageView pickaxeView;
    private Label pickaxeLabel;

    private Image soundImage;
    private ImageView soundView;
    private Label soundLabel;
    private boolean soundOn;

    private Image musicImage;
    private ImageView musicView;
    private Label musicLabel;
    private boolean musicOn;
    private boolean timerIsStartedOnce = false;

    private Image emptySprite;
    private ImageView emptyView;

    private static Integer STARTTIME = 15;
    private Timeline timeline = new Timeline();
    private Label timerLabel = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    private IntegerProperty stackedSeconds = new SimpleIntegerProperty();
    private Font font = Font.loadFont(getClass().getResource(BASE_PATH + "fonts/PressStart2P.ttf").toString(), 35);

    private static final String TEXTS_PATH = "/com/example/program/files/texts/";
    private static final String ITEMS_PATH = "/com/example/program/files/items/";
    private static final String HEARTS_PATH = "/com/example/program/files/hearts/";
    private static final String LEVEL_COUNTER_PATH = "/com/example/program/files/levelcounter/";
    private static final String SOUNDBUTTONS_PATH = "/com/example/program/files/soundbuttons/";
    private static final String FONT_PATH = "/com/example/program/files/fonts/PressStart2P.ttf";

    private static final String BASE_PATH = "/com/example/program/files/";



    private AudioPlayer audioPlayer;
    private TimeThread time;
    private TotalTime totTime;

    /**
     * Instansierar objekten och lägger till bilder och labels på scenen
     * @param mainProgram Huvudprogrammet
     * @param gameMode Kollar ifall användaren valt randomized eller kampanjläge
     * @param audioPlayer Klass som spelar upp ljud & musik
     * @param time En tråd som räknar hur många sekunder spelaren har på sig
     * @throws FileNotFoundException
     */
    public RightPanel(MainProgram mainProgram, String gameMode, AudioPlayer audioPlayer, TimeThread time) throws FileNotFoundException {
        this.mainProgram = mainProgram;
        this.gameMode = gameMode;
        this.audioPlayer = audioPlayer;
        this.time = time;

        soundOn = true;
        musicOn = true;

        imageMenu = new Image(getClass().getResource(BASE_PATH + "texts/Menu.png").toString(), 90, 30, false, false);
        menuView = new ImageView(imageMenu);

        emptySprite = new Image(getClass().getResource(BASE_PATH + "emptySprite.png").toString(), 30, 30, false, false);
        emptyView = new ImageView(emptySprite);

        pickaxe = new Image(getClass().getResource(BASE_PATH + "items/pickaxe.png").toString(), 30, 30, false, false);
        pickaxeView = new ImageView(pickaxe);
        pickaxeLabel = new Label();
        pickaxeLabel.setGraphic(emptyView);

        levelNumber = new Image(getClass().getResource(BASE_PATH + "levelcounter/"+ gameMode +".png").toString(), 90, 30, false, false);
        currentLevelView = new ImageView(levelNumber);
        levelLabel = new Label();
        levelLabel.setGraphic(currentLevelView);

        soundImage = new Image(getClass().getResource(BASE_PATH + "soundbuttons/soundon.png").toString(), 30,30,false,false);
        soundView = new ImageView(soundImage);
        soundLabel = new Label();
        soundLabel.setTranslateX(30);
        soundLabel.setTranslateY(440);
        soundLabel.setGraphic(soundView);

        musicImage = new Image(getClass().getResource(BASE_PATH + "soundbuttons/musicon.png").toString(), 30,30,false,false);
        musicView = new ImageView(musicImage);
        musicLabel = new Label();
        musicLabel.setTranslateX(60);
        musicLabel.setTranslateY(440);
        musicLabel.setGraphic(musicView);

        //Hearts only in Campaign
        if(gameMode!="Random"){
            heart = new Image(getClass().getResource(BASE_PATH + "hearts/3heart.png").toString(), 90, 30, false, false);
            currentHeartView = new ImageView(heart);
            heartLabel = new Label();
            heartLabel.setGraphic(currentHeartView);
            add(heartLabel,0,2);
        }

        timerLabel.textProperty().bind(timeSeconds.asString());
        timerLabel.setTextFill(Color.WHITE);
        timerLabel.setFont(font);
        timerLabel.setTranslateY(200);
        timerLabel.setTranslateX(8);

        add(timerLabel, 0, 4);
        add(levelLabel,0,1);
        add(pickaxeLabel, 0, 3);

        soundLabel.setOnMouseClicked(e -> soundLabelClicked());
        musicLabel.setOnMouseClicked(e -> musicLabelClicked());

        add(soundLabel,0,4);
        add(musicLabel,0,4);

        menuView.setOnMouseClicked(e -> MainMenuClicked(e));
        add(menuView,0,0);

        totTime = new TotalTime(false);

    }
    /**
     * Slår på/av spelljud
     */
    public void soundLabelClicked(){
        if(soundOn){
            soundImage = new Image(getClass().getResource(BASE_PATH + "soundbuttons/soundoff.png").toString(), 30,30,false,false);
            audioPlayer.muteSound(true);
            soundOn = false;
        } else{
            soundImage = new Image(getClass().getResource(BASE_PATH + "soundbuttons/soundon.png").toString(), 30,30,false,false);
            audioPlayer.muteSound(false);
            soundOn = true;
        }
        soundView.setImage(soundImage);
        soundLabel.setGraphic(soundView);
    }
    /**
     * Slår på/av musik
     */
    public void musicLabelClicked(){
        if(musicOn){
            musicImage = new Image(getClass().getResource(BASE_PATH + "soundbuttons/musicoff.png").toString(),30,30,false,false);
            audioPlayer.muteMusic(true);
            musicOn = false;
        } else{
            musicImage = new Image(getClass().getResource(BASE_PATH + "soundbuttons/musicon.png").toString(),30,30,false,false);
            audioPlayer.muteMusic(false);
            musicOn = true;
        }
        musicView.setImage(musicImage);
        musicLabel.setGraphic(musicView);
    }
    /**
     * Sätter en ny bild beroende på om man plockar upp/tappar ett liv
     * @param number
     */
    public void changeHeartCounter(String number){
        heart = new Image(getClass().getResource(BASE_PATH + "hearts/" + number + "heart.png").toString(), 90, 30, false, false);
        currentHeartView.setImage(heart);
        heartLabel.setGraphic(currentHeartView);
    }
    /**
     * Sätter bilden på yxan i en label
     */
    public void addPickaxe(){
        pickaxeLabel.setGraphic(pickaxeView);
    }
    /**
     * Tar bort yxan när man plcokat upp den
     */
    public void removePickaxe(){
        pickaxeLabel.setGraphic(emptyView);
    }
    /**
     * Byter bild beroende vilken nivå man befinner sig på
     * @param number
     */
    public void changeLevelCounter(String number){
        levelNumber = new Image(getClass().getResource(BASE_PATH + "levelcounter/" + number + ".png").toString(), 90, 30, false, false);
        currentLevelView.setImage(levelNumber);
        levelLabel.setGraphic(currentLevelView);
    }
    /**
     * Byter scen till menyn
     * @param e
     */
    private void MainMenuClicked(MouseEvent e){
        mainProgram.changeToMenu();
        audioPlayer.playButtonSound();
        audioPlayer.stopMusic();
    }
    /**
     * Startar den visuella klockan i GUIt
     */
    public void runClock() {
        timeSeconds.set(STARTTIME);

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
    }
    /**
     * Pausar klockan vid avancemang till ny nivå
     */
    public void pauseClock(){
        seconds = timeSeconds.getValue().intValue();
        timeline.stop();

        timeSeconds.set(seconds);
        timeline = null;
    }
    /**
     * Sätter tiden som visuella klockan ska visa
     * @param timesec
     */
    public void setTheTime(int timesec){
        timeSeconds.set(timesec);
    }
    /**
     * Kör igång klockan när spelaren trycker på startknappen
     */
    public void resumeClock(){
        timeSeconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();

    }
    /**
     * En setter som finns i varje maptemplate för att bestämma antal sekunder spelaren
     * har på sig
     * @param STARTTIME
     */
    public void setSTARTTIME(Integer STARTTIME) {
        RightPanel.STARTTIME = STARTTIME;
    }
    /**
     * Startar en tråd som räknar den totala tiden
     */
    public void startTotalTimer(){
        if (!timerIsStartedOnce)
        totTime.start();
    }
    /**
     * Startar en task för Game Over
     */
    public void startTask(){
        timer = null;

        timer = new Thread(startNewTask());
        timer.start();
    }
    /**
     * Tasks run-metod som sätter den totala tiden det tog att spela
     * Pausar musik & visar Game Over texten
     */
    public void gameIsOver(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                mainProgram.gameOver();
                audioPlayer.playGameOverSound();
                audioPlayer.stopMusic();
                totTime.setGameOver(true);
                removePickaxe();
            }
        });
    }
    /**
     * Skapar en task vid gameOver
     * @return
     */
    public Task startNewTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                gameIsOver();
                return null;
            }
        };
        return task;
    }
    /**
     * Setter för Tråden att veta när det blir GameOver
     * @param b
     */
    public void setGameOver(boolean b) {
        totTime.setGameOver(b);
    }
    /**
     * Setter för att kontrollera om tiden har startat
     * @param timerIsStartedOnce
     */
    public void setTimerIsStarted(boolean timerIsStartedOnce) {
        this.timerIsStartedOnce = timerIsStartedOnce;
    }
    /**
     * Om spelaren har 5 sekunder kvar kallas denna metod från tråden
     */
    public void fiveSecLeft() {
        audioPlayer.playTimeLeftSound();
        timerLabel.setStyle("-fx-text-fill: red;");
    }
    /**
     * Nollställer textens färg till vit och stoppar klockljudet
     */
    public void resetTimerLabel(){
        timerLabel.setStyle("-fx-text-fill: white;");
        audioPlayer.stopClockSound();
    }

    public void timeRemaining(int seconds){
        mainProgram.totalScore(seconds);
    }
}