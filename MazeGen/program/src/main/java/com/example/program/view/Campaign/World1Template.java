package com.example.program.view.Campaign;


import com.example.program.control.MainProgram;
import com.example.program.model.KeyboardPlayer;
import com.example.program.model.Maps.World1Maps;
import javafx.animation.FadeTransition;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.util.Duration;
import com.example.program.model.TimeThread;
import com.example.program.model.TotalTime;
import com.example.program.view.AudioPlayer;
import com.example.program.view.Menu.RightPanel;

import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author André Eklund
 * @edit Filip Örnling, Sebastian Helin, Viktor Näslund
 */

public class World1Template extends GridPane {


    private MainProgram mainProgram;
    private int[][] level;
    private ArrayList<Label> collectibles = new ArrayList<>();
    private ArrayList<Label> pickaxes = new ArrayList<>();
    private MouseListener mouseListener = new MouseListener();
    private Image wall;
    private Image path;
    private Image border;
    private Image goal;
    private Image diamond;
    private Image start;
    private Image heart;
    private Image breakableWall;
    private Image playerImage;
    private boolean startButtonPressed;
    private boolean allCollectiblesObtained;
    private boolean wallDestroyed;
    private int collectiblesObtained = 0;
    private int squareSize;
    private int currentLevel;
    private int heartCrystals;
    private Image pickAxeImage;
    private boolean pickaxeObtained;
    private boolean gameStarted;
    private boolean startNotClickedOnce = true;
    private boolean totalTimeStarted = false;
    private int world;
    private int seconds = 25;
    private RightPanel rightPanel;
    private AudioPlayer audioPlayer;
    private TimeThread time;
    private TotalTime totTime;
    //private KeyboardPlayer player;
    boolean gameOver = false;
    private ImageView playerView;
    private Label playerLabel;
    private World1Maps world1Maps;

    //private KeyBoardCampaign keyBoardCampaign;
    private KeyBoardCampaign keyBoardCampaign;
    private int numberOfLevels;

    private static final String BASE_PATH = "/com/example/program/files/";

    /**
     * Instansierar objekten.
     * @param level Den array som sedan omvandlas till en nivå inuti spelet.
     * @param currentLevel Den aktuella nivån
     * @param heartCrystals Spelarens liv.
     * @param mainProgram Huvudprogrammet.
     * @param rightPanel Panelen som visar information så som liv, tid, nivå osv.
     * @param world Används för att sätta rätt grafik på världen.
     * @param audioPlayer Används för att spela upp ljud inne i spelet.
     * @param seconds Tidsbegränsningen för varje bana.
     * @throws FileNotFoundException
     */

    //Konstruktorn ska kunna ta emot int-arrayer och representera dem i GUIt
    public World1Template(int[][] level, int currentLevel, int heartCrystals, MainProgram mainProgram, RightPanel rightPanel, int world, AudioPlayer audioPlayer, int seconds) throws FileNotFoundException {
        this.mainProgram = mainProgram;
        this.currentLevel = currentLevel;
        this.level = level;
        this.heartCrystals = heartCrystals;
        this.seconds = seconds;

        rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
        this.rightPanel = rightPanel;
        this.audioPlayer = audioPlayer;
        this.world = world;
        squareSize = 600/(level.length+2);
        setBackground();
        setupImages(world);
        setupBorders();
        setupLevel();
        rightPanel.setSTARTTIME(seconds);
        rightPanel.resetTimerLabel();

        totTime = new TotalTime(false);
        //time = null;
        //startLevelKeyboard(1, 1);
        //keyBoardCampaign = new KeyBoardCampaign(level, currentLevel, heartCrystals, mainProgram, rightPanel, world, audioPlayer, seconds);
/*
        setOnKeyPressed(event -> {
            try {
                handleKeyPressed(event);
            } catch (FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        setOnKeyReleased(this::handleKeyReleased); // flytta till enbart keyboardcampaign sen

        setFocusTraversable(true); //flytta till enbart keyboardcampaign sen?


 */
    }

    public World1Template(int[][] level, int currentLevel, int heartCrystals, MainProgram mainProgram, RightPanel rightPanel, int world, AudioPlayer audioPlayer, int seconds, int numberOfLevels) throws FileNotFoundException {
        this.mainProgram = mainProgram;
        this.currentLevel = currentLevel;
        this.level = level;
        this.heartCrystals = heartCrystals;
        this.seconds = seconds;
        this.numberOfLevels = numberOfLevels;

        rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
        this.rightPanel = rightPanel;
        this.audioPlayer = audioPlayer;
        this.world = world;
        squareSize = 600/(level.length+2);
        setBackground();
        setupImages(world);
        setupBorders();
        setupLevel();
        rightPanel.setSTARTTIME(seconds);
        rightPanel.resetTimerLabel();


        totTime = new TotalTime(false);
        time = null;
    }

    /**
     * Sätter bakgrunden i fönstret.
     */
    public void setBackground(){
        BackgroundImage menuBackground = new BackgroundImage(new Image(getClass().getResource(BASE_PATH + "MenuBackground.jpg").toString(),800,600,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(menuBackground));
    }

    /**
     * Skapar en ram runt spelplanen.
     */
    public void setupBorders() {
        for (int i = 0; i < level.length + 1; i++) {
            add(getBorders(), i, 0);
        }
        for (int i = 0; i < level.length + 1; i++) {
            add(getBorders(), 0, i);
        }
        for (int i = 0; i < level.length + 2; i++) {
            add(getBorders(), i, level.length + 1);
        }
        for (int i = 0; i < level.length + 2; i++) {
            add(getBorders(), level.length + 1, i);
        }
    }

    /**
     * Omvandlar värdena i arrayen av siffror till olika grafiska komponenter baserat på vilken siffra en position har.
     * Exempelvis så representerar 1:or väg, 0:or väggar, och 7:or hjärtan osv.
     */
    public void setupLevel() {
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level.length; j++) {

                if (level[i][j] == 1) {
                    add(getPath(),j + 1,i + 1);
                }
                else if (level[i][j] == 0){
                    add(getWall(),j + 1,i + 1);
                }
                else if (level[i][j] == 2){
                    add(getStart(),j + 1,i + 1);
                    //setPlayerOnStart(j + 1, i + 1);
                }
                else if (level[i][j] == 3){
                    add(getGoal(),j + 1,i + 1);
                }
                else if (level[i][j] == 4){
                    add(getPath(),j + 1,i + 1);
                    add(addCollectible(),j + 1,i + 1);
                }
                else if (level[i][j] == 5){
                    add(getPath(),j + 1,i + 1);
                    add(addPickAxe(),j + 1,i + 1);
                }
                else if (level[i][j] == 6){
                    add(getBreakableWall(),j + 1,i + 1);
                }
                else if (level[i][j] == 7){
                    add(getPath(),j + 1,i + 1);
                    add(addHeartCrystal(),j + 1,i + 1);
                }
            }
        }
    }

    /**
     * Instansierar de olika bilderna som används som grafik inuti spelet.
     * Baserad på value så sätts bilderna till en specifik folder per värld.
     * @param value Den aktuella världen.
     */
    public void setupImages(int value){

        String folder = "";

        if (value == 0) {
            folder = "forest";
        }
        else if (value == 1) {
            folder = "underground";
        }
        else if (value == 2) {
            folder = "lava";
        }
        else if(value == 3) {
            folder = "cloud";
        }
        else if(value == 4) {
            folder = "desert";
        }
        else if(value == 5) {
            folder = "space";
        }

        path = new Image(getClass().getResource(BASE_PATH + "" + folder + "/path.png").toString(), squareSize, squareSize, false, false);
        goal = new Image(getClass().getResource(BASE_PATH + "" + folder + "/goal.png").toString(), squareSize, squareSize, false, false);
        diamond = new Image(getClass().getResource(BASE_PATH + "" + folder + "/collectible.png").toString(), squareSize, squareSize, false, false);
        start = new Image(getClass().getResource(BASE_PATH + "" + folder + "/start.png").toString(), squareSize, squareSize, false, false);
        pickAxeImage = new Image(getClass().getResource(BASE_PATH + "items/pickaxe.png").toString(), squareSize, squareSize, false, false);
        heart = new Image(getClass().getResource(BASE_PATH + "items/heart.png").toString(), squareSize, squareSize, false, false);
        playerImage = new Image(getClass().getResource(BASE_PATH + "playerTest.png").toString(), squareSize, squareSize, false, false); // ta bort sen
        if (value == 3) {
            breakableWall = new Image(getClass().getResource(BASE_PATH + "cloud/breakablewall.png").toString(), squareSize, squareSize, false, false);
        }
        else {
            breakableWall = new Image(getClass().getResource(BASE_PATH + "breakablewall.png").toString(), squareSize, squareSize, false, false);
        }
        if(value!=5){
            border = new Image(getClass().getResource(BASE_PATH + "" + folder + "/border.png").toString(), squareSize, squareSize, false, false);
            wall = new Image(getClass().getResource(BASE_PATH + "" + folder + "/wall.png").toString(), squareSize, squareSize, false, false);
        }
    }

    /**
     * En metod som skapar ett objekt av label som representerar en vägg.
     * @return Returnerar en label.
     */
    public Label getWall() {
        Label label = new Label();
        ImageView wallView = new ImageView(wall);
        wallView.setFitHeight(squareSize);
        wallView.setFitWidth(squareSize);
        label.setGraphic(wallView);
        label.setOnMouseEntered(e -> enteredWall(e));
        label.setOnMouseExited(e -> exitedLabel(e));
        return label;
    }

    /**
     * En metod som skapar ett objekt av label som representerar en väg.
     * @return Returnerar en label.
     */
    private Label getPath() {
        Label label = new Label();
        ImageView pathView = new ImageView(path);
        pathView.setFitHeight(squareSize);
        pathView.setFitWidth(squareSize);
        label.setGraphic(pathView);
        return label;
    }

    /**
     * En metod som skapar ett objekt av label som representerar en border.
     * @return Returnerar en label.
     */
    private Label getBorders() {
        Label label = new Label();
        ImageView borderView = new ImageView(border);
        borderView.setFitHeight(squareSize);
        borderView.setFitWidth(squareSize);
        label.setGraphic(borderView);
        label.setOnMouseEntered(e -> enteredWall(e));
        label.setOnMouseExited(e -> exitedLabel(e));
        return label;
    }

    /**
     * En metod som skapar ett objekt av label som representerar en förstörbar vägg.
     * @return Returnerar en label.
     */
    private Label getBreakableWall() {
        Label label = new Label();
        ImageView borderView = new ImageView(breakableWall);
        borderView.setFitHeight(squareSize);
        borderView.setFitWidth(squareSize);
        label.setGraphic(borderView);
        label.setOnMouseEntered(e -> enteredBreakableWall(e)); //TODO för keyboard
        return label;
    }

    /**
     * En metod som skapar ett objekt av label som representerar ett mål.
     * @return Returnerar en label.
     */
    private Label getGoal() {
        Label label = new Label();
        ImageView borderView = new ImageView(goal);
        borderView.setFitHeight(squareSize);
        borderView.setFitWidth(squareSize);
        label.setGraphic(borderView);
        label.setOnMouseEntered(e -> {
            try {
                enteredGoal();
            } catch (FileNotFoundException | InterruptedException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        return label;
    }

    /**
     * En metod som skapar ett objekt av label som representerar start.
     * @return Returnerar en label.
     */
    private Label getStart() {
        Label label = new Label();
        ImageView borderView = new ImageView(start);
        borderView.setFitHeight(squareSize);
        borderView.setFitWidth(squareSize);
        label.setGraphic(borderView);
        label.setOnMouseClicked(e -> startLevel());
        return label;
    }

    /**
     * En metod som skapar ett objekt av label som representerar en collectible.
     * @return Returnerar en label.
     */
    public Label addCollectible() {
        Label collectible = new Label();
        ImageView borderView = new ImageView(diamond);
        borderView.setFitHeight(squareSize);
        borderView.setFitWidth(squareSize);
        Glow glow = new Glow();
        glow.setLevel(0.7);
        borderView.setEffect(glow);
        collectible.setStyle("fx-background-color: transparent;");
        collectible.setGraphic(borderView);
        collectible.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseListener);
        collectibles.add(collectible);
        return collectible;
    }

    /**
     * En metod som skapar ett objekt av label som representerar ett spelarliv.
     * @return Returnerar en label.
     */
    public Label addHeartCrystal() {
        Label heartCrystal = new Label();
        ImageView borderView = new ImageView(heart);
        borderView.setFitHeight(squareSize);
        borderView.setFitWidth(squareSize);
        Glow glow = new Glow();
        glow.setLevel(0.8);
        borderView.setEffect(glow);
        heartCrystal.setStyle("fx-background-color: transparent;");
        heartCrystal.setGraphic(borderView);
        heartCrystal.setOpacity(0.8);
        heartCrystal.setOnMouseEntered(e -> heartCrystalObtained(e));
        return heartCrystal;
    }

    /**
     * När en användare vidrör en label av typen heartCrystal körs denna metod.
     * Om spelaren har mindre än tre återstående liv inkrementeras variabeln heartCrystals.
     * @param e Används för att hitta rätt label.
     */

    private void heartCrystalObtained(MouseEvent e) { //TODO lägg till metod för spel med keyboard

        Label label = (Label)e.getSource();

        if (startButtonPressed) {
            audioPlayer.playHeartSound();
            label.setVisible(false);
            if(heartCrystals<3){
                heartCrystals++;
                rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
            }
        }
    }

    /**
     * En metod som skapar ett objekt av label som representerar en yxa.
     * @return Returnerar en label.
     */
    public Label addPickAxe() {
        Label pickAxe = new Label();
        ImageView borderView = new ImageView(pickAxeImage);
        borderView.setFitHeight(squareSize);
        borderView.setFitWidth(squareSize);
        Glow glow = new Glow();
        glow.setLevel(0.7);
        borderView.setEffect(glow);
        pickAxe.setStyle("fx-background-color: transparent;");
        pickAxe.setGraphic(borderView);
        pickAxe.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseListener);
        pickaxes.add(pickAxe);
        return pickAxe;
    }

    /**
     * Om en spelare vidrör en vägg med muspekaren körs denna metod.
     * Om spelrundan är aktiverad förlorar spelaren ett liv.
     * Om spelaren endast har ett återstående liv kvar vid kollisionen körs metoden gameOver.
     * @param e Används för att hitta rätt label.
     */

    public String enteredWall(MouseEvent e) { //TODO ska vi ha samma koncept för spel med keyboard?
        Label label = (Label)e.getSource();
        FadeTransition fade = new FadeTransition();
        fade.setNode(label);
        fade.setDuration(Duration.seconds(0.3));
        fade.setFromValue(10);
        fade.setToValue(0.6);
        fade.play();

        if (startButtonPressed) {

            heartCrystals--;
            rightPanel.changeHeartCounter(String.valueOf(heartCrystals));

            if (heartCrystals == 0) {
                gameOver();
                return "dead";
            }
            audioPlayer.playDeathSound();
            startButtonPressed = false;

            return "alive";
        }

        return null;
    }

    /**
     * Om en spelare vidrör ett spöke med muspekaren körs denna metod.
     * Om spelrundan är aktiverad förlorar spelaren ett liv.
     * Om spelaren endast har ett återstående liv kvar vid kollisionen körs metoden gameOver.
     * @param e
     */
    public void enteredGhost(MouseEvent e){
        ImageView view = (ImageView) e.getSource();
        FadeTransition fade = new FadeTransition();
        fade.setNode(view);
        fade.setDuration(Duration.seconds(1));
        fade.setFromValue(10);
        fade.setToValue(0.6);
        fade.setToValue(10);
        fade.play();


        if (startButtonPressed) {
            audioPlayer.playMobSound();
            audioPlayer.playDeathSound();
            heartCrystals--;
            rightPanel.changeHeartCounter(String.valueOf(heartCrystals));

            if (heartCrystals == 0) {
                gameOver();
            }
            startButtonPressed = false;
        }
    }

    /**
     * Avslutar spelrundan och kör metoden gameOver i mainProgram.
     */
    private void gameOver() {
        audioPlayer.playGameOverSound();
        audioPlayer.stopMusic();
        mainProgram.gameOver();
        rightPanel.pauseClock();
        gameStarted = true;
        time.setGameOver(true);
        rightPanel.setGameOver(true); // använd denna boolean för testing.
        time = null;
        rightPanel.removePickaxe();
    }

    /**
     * Om spelrundan är aktiverad och spelaren har plockat upp alla collectibles startas nästa nivå.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public void enteredGoal() throws FileNotFoundException, InterruptedException {
        if (startButtonPressed && allCollectiblesObtained) {
            audioPlayer.stopClockSound();
            audioPlayer.playGoalSound();
            nextLevel();
            rightPanel.pauseClock();
            rightPanel.setTheTime(seconds);
            gameStarted = true;
            time.setGameOver(true);
            time = null;
        }
    }

    /**
     * Baserad på den aktuella världen väljer programmmet vilken nivå som ska spelas.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public void nextLevel() throws FileNotFoundException, InterruptedException {

        if (world == 0) {
            mainProgram.nextWorld1Level(currentLevel, heartCrystals);
        }
        else if (world == 1) {
            mainProgram.nextWorld2Level(currentLevel, heartCrystals);
        }
        else if (world == 2) {
            mainProgram.nextWorld3Level(currentLevel, heartCrystals);
        }
        else if (world == 3) {
            mainProgram.nextWorld4Level(currentLevel, heartCrystals);
        }
        else if (world == 4) {
            mainProgram.nextWorld5Level(currentLevel, heartCrystals);
        }
        else if (world == 5) {
            mainProgram.nextWorld6Level(currentLevel, heartCrystals);
        }
    }

    /**
     * Startar spelrundan och timern.
     */
    public void startLevel() {

        if (!totalTimeStarted){
            rightPanel.startTotalTimer();
            rightPanel.setTimerIsStarted(true);
        }

        if (!gameStarted){
            rightPanel.resumeClock();
            gameStarted = true;
            time = null;
            time = new TimeThread(seconds, rightPanel);
            time.setGameOver(false);
            time.start();

        }
        /*else if (startNotClickedOnce){
            rightPanel.runClock();
            time = new TimeThread(seconds, rightPanel);
            time.setGameOver(false);
            time.start();

        }

         */
        totalTimeStarted = true;
        startNotClickedOnce = false;
        audioPlayer.playStartSound();
        startButtonPressed = true;
    }

    /**
     * När muspekaren lämnar en label slutar den att highlightas.
     * @param e Används för att hitta rätt label.
     */
    public void exitedLabel(MouseEvent e) {
        Label label = (Label)e.getSource();
        FadeTransition fade = new FadeTransition();
        fade.setNode(label);
        fade.setDuration(Duration.seconds(0.3));
        fade.setFromValue(0.6);
        fade.setToValue(10);
        fade.play();
    }

    /**
     * Om spelrundan är startad och spelaren har plockat upp en yxa går det att förstöra väggen.
     * Om spelrundan är startad och spelaren inte plockat upp en yxa förlorar hen ett liv vid kollision med väggen.
     * @param e Används för att hitta rätt label.
     */
    public void enteredBreakableWall(MouseEvent e) { //TODO detta behöver implementeras i keyboard också

        Label label = (Label)e.getSource();
        ImageView pathView = new ImageView(path);

        if (startButtonPressed) {

            if (pickaxeObtained) {
                label.setGraphic(pathView);
                pickaxeObtained = false;
                rightPanel.removePickaxe();
                wallDestroyed = true;
                audioPlayer.playBreakableWallSound();
            }
            else if (!wallDestroyed) {
                enteredWall(e);
            }
        }
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    /**
     * En listener som körs när spelaren plockar upp en collectible eller en yxa.
     */
    private class MouseListener implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {
            if (startButtonPressed) {

                for (Label label : pickaxes){
                    if (e.getSource()== label){
                        audioPlayer.playPickAxeSound();
                        label.setVisible(false);
                        pickaxeObtained = true;
                        rightPanel.addPickaxe();
                    }
                }

                for (Label label: collectibles) {
                    if (e.getSource() == label) {
                        audioPlayer.playCollectibleSound();
                        label.setVisible(false);
                        collectiblesObtained++;
                        if (collectiblesObtained == collectibles.size()) {
                            allCollectiblesObtained = true;
                        }
                    }
                }
            }
        }
    }

    public int getLevel() {
        return level.length;
    }

    /////////////ALLT HÄR NEDAN ÄR FÖR KEYBOARD. SKA BRA FINNAS I KEYBOARDCAMPAIGN /////////////
/*
    public void updatePlayerImage(int x, int y) { //sker varje gång spelaren går ett steg

        if(player == null){
            this.player = new KeyboardPlayer(x, y);
        }

        //startLevelKeyboard(x, y);

        Label playerLabel = new Label();
        ImageView playerView = new ImageView(playerImage);

        playerView.setFitHeight(squareSize);
        playerView.setFitWidth(squareSize);
        playerLabel.setGraphic(playerView);

        getChildren().removeIf(node -> node instanceof Label && ((Label) node).getGraphic() instanceof ImageView && ((ImageView) ((Label) node).getGraphic()).getImage() == playerImage);

        add(playerLabel, x, y);
    }

    private void handleKeyPressed(javafx.scene.input.KeyEvent event) throws FileNotFoundException, InterruptedException {

            KeyCode keyCode = event.getCode();
            int newX = 0;
            int newY = 0;

            switch (keyCode) {
                case UP:
                    newY = player.getY() - player.getSpeed();
                    newX = player.getX();
                    if(!hitWall(newX, newY)) {
                        player.setY(player.getY() - player.getSpeed());
                    }
                    break;

                case DOWN:
                    newY = player.getY() + player.getSpeed();
                    newX = player.getX();
                    if(!hitWall(newX, newY)) {
                        player.setY(player.getY() + player.getSpeed());
                    }
                    break;

                case LEFT:
                    newX = player.getX() - player.getSpeed();
                    newY = player.getY();
                    if(!hitWall(newX, newY)) {
                        player.setX(player.getX() - player.getSpeed());
                    }
                    break;

                case RIGHT:
                    newX = player.getX() + player.getSpeed();
                    newY = player.getY();
                    if(!hitWall(newX, newY)) {
                        player.setX(player.getX() + player.getSpeed());
                    }
                    break;

                default:
                    // Lägg till ljud för fel knapp här?
                    break;
            }

            newX = player.getX();
            newY = player.getY();

            updatePlayerImage(newX, newY);
            checkCollectibles(newX, newY);
            checkReachedGoal(newX, newY);
    }

    private void handleKeyReleased(KeyEvent event) {
        System.out.println("released " + event.getCode().toString()); // Kolla senare om denna metoden behövs
    }

    public boolean hitWall(int newX, int newY){ // lägg till förlorat liv

        if (newX == 0 || newY == 0 || newX == level.length+1 || newY == level.length+1 || level[newY - 1][newX - 1] == 0) { //kolla om spelaren försöker gå utanför banan eller in i en vägg

            FadeTransition fade = new FadeTransition();
            fade.setDuration(Duration.seconds(0.3));
            fade.setFromValue(10);
            fade.setToValue(0.6);
            fade.play();

            heartCrystals--;
            rightPanel.changeHeartCounter(String.valueOf(heartCrystals));

            if (heartCrystals == 0) {
                gameOver();
            }
            audioPlayer.playDeathSound();

            return true; // lägg till sound för fel
        } //TODO lägg till hantering av breakable wall
        else {
            return false; // om allt funkar som det ska
        }
    }

    public void checkCollectibles(int x, int y) {

        if(level[y - 1][x - 1] == 4) { //fixa senare. plocka upp collectible

            for (Label label: collectibles) {

                int labelX = GridPane.getColumnIndex(label);
                int labelY = GridPane.getRowIndex(label);

                if (labelX == x && labelY == y) {
                    audioPlayer.playCollectibleSound();
                    label.setVisible(false);
                    collectiblesObtained++;
                    if (collectiblesObtained == collectibles.size()) {
                        allCollectiblesObtained = true;
                    }
                }
            }



        } else if (level[y - 1][x - 1] == 5) { // Plocka upp pickaxe
            for (Label label : pickaxes){

                int labelX = GridPane.getColumnIndex(label);
                int labelY = GridPane.getRowIndex(label);

                if (labelX == x && labelY == y){
                    audioPlayer.playPickAxeSound();
                    label.setVisible(false);
                    pickaxeObtained = true;
                    rightPanel.addPickaxe();
                }
            }

        } else if(level[y - 1][x - 1] == 7) { // Plocka upp heartcrystal
                //TODO lägg till hjärta i right panel och ta bort det från grid
        }
    }

    public void setPlayerOnStart(int x, int y) {
        if (level[y - 1][x - 1] == 2) {
            updatePlayerImage(x, y);
        }
    }

    public void checkReachedGoal(int x, int y) throws InterruptedException, FileNotFoundException {
        if ((level[y - 1][x - 1] == 3) && (allCollectiblesObtained)) {
            audioPlayer.stopClockSound();
            audioPlayer.playGoalSound();
            rightPanel.pauseClock();
            rightPanel.setTheTime(seconds);
            gameStarted = true;
            time.setGameOver(true);
            time = null;
            nextLevelKeyboard(x, y);
        }
    }

    public void nextLevelKeyboard(int x, int y) throws FileNotFoundException, InterruptedException {

        if (world == 0) {
            mainProgram.nextWorld1Level(currentLevel, heartCrystals);
        } else if (world == 1) {
            mainProgram.nextWorld2Level(currentLevel, heartCrystals);
        } else if (world == 2) {
            mainProgram.nextWorld3Level(currentLevel, heartCrystals);
        } else if (world == 3) {
            mainProgram.nextWorld4Level(currentLevel, heartCrystals);
        } else if (world == 4) {
            mainProgram.nextWorld5Level(currentLevel, heartCrystals);
        } else if (world == 5) {
            mainProgram.nextWorld6Level(currentLevel, heartCrystals);
        }

    }


    public void startLevelKeyboard(int x, int y) {

        gameStarted = true;

        if (!totalTimeStarted){
            rightPanel.startTotalTimer();
            rightPanel.setTimerIsStarted(true);
        }
        System.out.println(gameStarted);

        if (gameStarted){
            System.out.println("this method is running");
            rightPanel.resumeClock();
            gameStarted = true;
            time = new TimeThread(seconds, rightPanel);
            time.setGameOver(false);
            time.start();

        }

        totalTimeStarted = true;
        //startNotClickedOnce = false;
        audioPlayer.playStartSound();
        startButtonPressed = true;
    }

 */
}
