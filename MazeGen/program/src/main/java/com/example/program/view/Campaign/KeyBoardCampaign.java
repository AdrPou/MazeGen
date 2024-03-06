package com.example.program.view.Campaign;


import com.example.program.control.MainProgram;
import com.example.program.model.KeyboardPlayer;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import com.example.program.model.TimeThread;
import com.example.program.model.TotalTime;
import com.example.program.view.AudioPlayer;
import com.example.program.view.Menu.RightPanel;

import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Thread.sleep;


public class KeyBoardCampaign extends GridPane {


    private MainProgram mainProgram;
    private int[][] level;
    private ArrayList<Label> collectibles = new ArrayList<>();
    private ArrayList<Label> pickaxes = new ArrayList<>();
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
    boolean gameStarted;
    // private boolean startNotClickedOnce = true;
    private boolean totalTimeStarted = false;
    private int world;
    private int seconds = 25;
    private RightPanel rightPanel;
    private AudioPlayer audioPlayer;
    private TimeThread time;
    private TotalTime totTime;
    private KeyboardPlayer player;
    boolean gameOver = false; // When true, game should stop listening for keyboard input
    private ImageView playerView;
    private HashMap<Label, Boolean> soundPlayedForLabel = new HashMap<>(); // To know if the sound for the collectible has been played or not, so it doesn't play again

    private Label heartCrystal;

    //private boolean heartTaken; // so that the sound for the heart is only played once

    private static final String BASE_PATH = "/com/example/program/files/";
    private List<Label> ghosts;


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
    public KeyBoardCampaign(int[][] level, int currentLevel, int heartCrystals, MainProgram mainProgram, RightPanel rightPanel, int world, AudioPlayer audioPlayer, int seconds) throws FileNotFoundException {
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
        System.out.println("Setting up level");
        setupLevel();
        rightPanel.setSTARTTIME(seconds);
        rightPanel.resetTimerLabel();

        // To know if the sound for the collectible has been played or not, so it doesn't play again
        for (Label label : collectibles) {
            soundPlayedForLabel.put(label, false);
        }

        totTime = new TotalTime(false);

        setOnKeyPressed(event -> {
            try {
                handleKeyPressed(event);
            } catch (FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        });



        setFocusTraversable(true);
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
                    setPlayerOnStart(j + 1, i + 1);
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
        return label;
    }

    /**
     * En metod som skapar ett objekt av label som representerar en förstörbar vägg.
     * @return Returnerar en label.
     */
    private Label getBreakableWall() { //TODO fixa för keyboard
        Label label = new Label();
        ImageView borderView = new ImageView(breakableWall);
        borderView.setFitHeight(squareSize);
        borderView.setFitWidth(squareSize);
        label.setGraphic(borderView);
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
        collectibles.add(collectible);
        return collectible;
    }

    /**
     * En metod som skapar ett objekt av label som representerar ett spelarliv.
     * @return Returnerar en label.
     */
    public Label addHeartCrystal() {
        heartCrystal = new Label();
        ImageView borderView = new ImageView(heart);
        borderView.setFitHeight(squareSize);
        borderView.setFitWidth(squareSize);
        Glow glow = new Glow();
        glow.setLevel(0.8);
        borderView.setEffect(glow);
        heartCrystal.setStyle("fx-background-color: transparent;");
        heartCrystal.setGraphic(borderView);
        heartCrystal.setOpacity(0.8);
        return heartCrystal;
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
        pickaxes.add(pickAxe);
        return pickAxe;
    }

    /**
     * Avslutar spelrundan och kör metoden gameOver i mainProgram.
     */
    private void gameOver() {
        gameOver = true;
        audioPlayer.playGameOverSound();
        audioPlayer.stopMusic();
        mainProgram.gameOver();
        rightPanel.pauseClock();


        //gameStarted = true;
        time.setGameOver(true);
        rightPanel.setGameOver(true); // använd denna boolean för testing.
        //time.resetTime();
        time = null;
        rightPanel.removePickaxe();

    }



    /**
     * Om spelrundan är aktiverad och spelaren har plockat upp alla collectibles startas nästa nivå.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    /*
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

     */



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

    public int getLevelLength() {
        return level.length;
    }

    public void updatePlayerImage(int x, int y) { // sker varje gång spelaren går ett steg

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

    public void handleKeyPressed(KeyEvent event) throws FileNotFoundException, InterruptedException {
        System.out.println("Key pressed");
        if(gameOver) {
            System.out.println("Game over");
            return;
        }
        if(!gameStarted){
            System.out.println("Game not started");
            startLevelKeyboard(1, 1);
        }

        KeyCode keyCode = event.getCode();
        int newX = player.getX();
        int newY = player.getY();

        switch (keyCode) {
            case UP:
                System.out.println("UP");
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                newX++;
                break;
            default:
                return;
        }

        if (newX > 0 && newY > 0 && newX <= level[0].length && newY <= level.length && level[newY - 1][newX - 1] == 6) {
            if (pickaxeObtained) {
                level[newY - 1][newX - 1] = 1; // Replace the breakable wall with a path
                Label label = (Label)getNodeFromGridPane(this, newX, newY);
                ImageView pathView = new ImageView(path);
                label.setGraphic(pathView);
                pickaxeObtained = false;
                rightPanel.removePickaxe();
                audioPlayer.playBreakableWallSound();
            }
            System.out.println("Hit breakable wall");
            return;
        } else if (hitWall(newX, newY)) {
            System.out.println("Hit wall");
            return;
            }

        player.move(newX, newY);
        updatePlayerImage(newX, newY);
        checkCollectibles(newX, newY);
        checkReachedGoal(newX, newY);
    }
    // TODO: Check if ghost is at the player's position
    /*
    private boolean isGhostAt(int x, int y) {
        for (Ghost ghost : ghosts) {
            if (ghost.getX() == x && ghost.getY() == y) {
                return true;
            }
        }
        return false;
    }
    */

    /*
    public void checkIfCollidingWithGhost(List<Label> ghosts) {
        while(gameStarted && !gameOver){

            // Check for collision with each ghost
            for (Label ghost : ghosts) {
                if (isColliding(player, ghost)) {
                    FadeTransition fade = new FadeTransition();
                    fade.setDuration(Duration.seconds(0.3));
                    fade.setFromValue(10);
                    fade.setToValue(0.6);
                    fade.play();

                    if(heartCrystals > 0){
                        heartCrystals--;
                        rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
                        audioPlayer.playDeathSound();
                    }
                    if (heartCrystals == 0) {
                        gameOver();
                    }
                } else {
                }
            }
        }
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

     */

    // Function to check collision between player and ghost
    public void isColliding(List<Label> ghosts) {
        if(this.ghosts == null){
            this.ghosts = ghosts;
        }
        Rectangle boundsPlayer = new Rectangle(player.getX(), player.getY(),
                squareSize, squareSize);
        if(ghosts != null){
            for(Label ghost : ghosts){
                Rectangle boundsGhost = new Rectangle(ghost.getLayoutX(), ghost.getLayoutY(),
                        squareSize, squareSize);
                if(boundsPlayer.getBoundsInParent().intersects(boundsGhost.getBoundsInParent())){
                    if(heartCrystals > 0){
                        heartCrystals--;
                        rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
                        audioPlayer.playDeathSound();
                    }
                    if (heartCrystals == 0) {
                        gameOver();
                    }
                }
            }
        }

    }



    public boolean hitWall(int newX, int newY){ //TODO lägg till if-sats för breakable wall

        isColliding(ghosts);
        if (newX == 0 || newY == 0 || newX == level.length+1 || newY == level.length+1 || level[newY - 1][newX - 1] == 0 || level[newY - 1][newX - 1] == 6) { //kolla om spelaren försöker gå utanför banan eller in i en vägg

            FadeTransition fade = new FadeTransition();
            fade.setDuration(Duration.seconds(0.3));
            fade.setFromValue(10);
            fade.setToValue(0.6);
            fade.play();

            if(heartCrystals > 0){
                heartCrystals--;
                rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
                audioPlayer.playDeathSound();
            }
            if (heartCrystals == 0) {
                gameOver();
            }


            return true; // lägg till sound för fel
        }
        else {
            return false; // om allt funkar som det ska
        }
    }

    public void checkCollectibles(int x, int y) { //TODO ska kunna plocka upp hjärtan

        if(level[y - 1][x - 1] == 4) { //fixa senare. plocka upp collectible

            for (Label label: collectibles) {
                int labelX = GridPane.getColumnIndex(label);
                int labelY = GridPane.getRowIndex(label);

                if (labelX == x && labelY == y) {
                    if (!soundPlayedForLabel.get(label)) {
                        audioPlayer.playCollectibleSound();
                        soundPlayedForLabel.put(label, true);
                    }
                    label.setVisible(false);
                    collectiblesObtained++;
                    if (collectiblesObtained == collectibles.size()) {
                        allCollectiblesObtained = true;
                    }
                }
            }

        } else if (level[y - 1][x - 1] == 5) { // fixa senare. plocka upp pickaxe
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
        } else if (level[y - 1][x - 1] == 7 && heartCrystal.isVisible()) { // New code for heart
            // Code to handle heart collection
            // For example, you might want to increase the player's health
            if(heartCrystals < 3 ){
                heartCrystals++;
                rightPanel.changeHeartCounter(String.valueOf(heartCrystals));
                // Remove the heart from the GUI
            }
            audioPlayer.playHeartSound(); // Assuming you have a sound for collecting hearts
            heartCrystal.setVisible(false);
            //heartTaken = true;
        }


    }

    public void setPlayerOnStart(int x, int y) {
        System.out.println("Setting player on start " + x + ", " + y);
        if (level[y - 1][x - 1] == 2) {
            updatePlayerImage(x, y);
        }
    }

    public void  checkReachedGoal(int x, int y) throws InterruptedException, FileNotFoundException {
        if ((level[y - 1][x - 1] == 3) && (allCollectiblesObtained)) {
            audioPlayer.stopClockSound();
            audioPlayer.playGoalSound();
            nextLevelKeyboard(x, y);
            rightPanel.pauseClock();
            System.out.println(seconds);
            rightPanel.setTheTime(seconds);
            //gameStarted = true;
            time.setGameOver(true);
            time = null;
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

    public void startLevelKeyboard(int x, int y) { // TODO lägg till metodanrop här eller skriv metod för keyboard i denna metoden
        if (!totalTimeStarted){
            rightPanel.startTotalTimer();
            rightPanel.setTimerIsStarted(true);
        }

        if (!gameStarted){
            rightPanel.resumeClock();
            gameStarted = true;
            if(time != null){
                time = null;
            }
            time = new TimeThread(seconds, rightPanel);
            time.setGameOver(false);
            time.start();

        }

        totalTimeStarted = true;
        audioPlayer.playStartSound();
        startButtonPressed = true;
        System.out.println("Game started");
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public KeyboardPlayer getPlayer() {
        return player;
    }

    public int getHeartCrystals() {
        return heartCrystals;
    }

    public int getCollectiblesObtained() {
        return collectiblesObtained;
    }

    public boolean isPickaxeObtained() {
        return pickaxeObtained;
    }

    public boolean isGameOver() {
        return gameOver;
    }

}
