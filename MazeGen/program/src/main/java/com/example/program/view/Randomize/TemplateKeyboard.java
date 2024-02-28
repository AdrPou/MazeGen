package com.example.program.view.Randomize;


import com.example.program.model.KeyboardPlayer;
import com.example.program.model.MazeGeneration.GenerateNextLevel;
import com.example.program.control.MainProgram;
import com.example.program.model.TimeThread;
import com.example.program.view.AudioPlayer;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import javafx.scene.media.Media;
import com.example.program.view.Menu.RightPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author André Eklund
 * edit Viktor Näslund
 */

public class TemplateKeyboard extends GridPane {

    private MainProgram mainProgram;
    private GenerateNextLevel generateNextLevel;
    private int[][] level;
    private ArrayList<Label> collectibles = new ArrayList<>();
    private boolean gameStarted;
    private boolean allCollectiblesObtained;
    private int collectiblesObtained = 0;
    private int squareSize;;

    private Image wall;
    private Image path;
    private Image border;
    private Image goal;
    private Image diamond;
    private Image start;
    private AudioPlayer audioPlayer;

    private static final String IMAGE_BASE_PATH = "/com/example/program/files/";
    private static final String SOUND_BASE_PATH = "/com/example/program/files/sounds/";

    private MediaPlayer diamondPlayer = new MediaPlayer(new Media(getClass().getResource(SOUND_BASE_PATH + "Diamond1.mp3").toString()));
    private MediaPlayer deathPlayer = new MediaPlayer(new Media(getClass().getResource(SOUND_BASE_PATH + "MazegenDeath.mp3").toString()));

    private MediaPlayer startPlayer = new MediaPlayer(new Media(getClass().getResource(SOUND_BASE_PATH + "MazegenStart.mp3").toString()));
    private MediaPlayer goalPlayer = new MediaPlayer(new Media(getClass().getResource(SOUND_BASE_PATH + "MazegenGoal.mp3").toString()));

    private KeyboardPlayer player;
    private Image playerImage; // Behöver skapa objekt av spelgubben sen i konstruktorn.



    /**
     * Konstruktorn ska kunna ta emot int-arrayer och representera dem i GUIt
     */
    public TemplateKeyboard(int[][] level, MainProgram mainProgram, GenerateNextLevel generateNextLevel) throws FileNotFoundException {
        this.mainProgram = mainProgram;
        this.level = level;
        this.generateNextLevel = generateNextLevel;

        squareSize = 600/(level.length+2);
        setBackground();
        setupImages(new Random().nextInt(6));
        setupBorders();
        setupLevel();

        setPlayerOnStart(1, 1);

        setOnKeyPressed(event -> {
            try {
                handleKeyPressed(event);
            } catch (FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        setOnKeyReleased(this::handleKeyReleased);

        setFocusTraversable(true);
    }

    private void handleKeyReleased(KeyEvent event) {
        System.out.println("released " + event.getCode().toString());
    }

    /**
     * Sätter bakgrunden i fönstret.
     */
    public void setBackground(){
        BackgroundImage menuBackground = new BackgroundImage(new Image(getClass().getResource(IMAGE_BASE_PATH + "MenuBackground.jpg").toString(), 800, 600, false, true),
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
                    if (new Random().nextInt(5) == 4) {
                        add(addCollectible(),j + 1,i + 1);
                    }
                }
                else if (level[i][j] == 0){
                    add(getWall(),j + 1,i + 1);
                }
                else if (level[i][j] == 2){
                    add(getStart(),j + 1,i + 1);
                }
                else if (level[i][j] == 3){
                    add(getGoal(),j + 1,i + 1);
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
            folder = "lava";
        }
        else if (value == 2) {
            folder = "underground";
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

        path = new Image(getClass().getResource(IMAGE_BASE_PATH + folder + "/path.png").toString(), squareSize, squareSize, false, false);
        goal = new Image(getClass().getResource(IMAGE_BASE_PATH + folder + "/goal.png").toString(), squareSize, squareSize, false, false);
        diamond = new Image(getClass().getResource(IMAGE_BASE_PATH + folder + "/collectible.png").toString(), squareSize, squareSize, false, false);
        start = new Image(getClass().getResource(IMAGE_BASE_PATH + folder + "/start.png").toString(), squareSize, squareSize, false, false);

        if (value != 5) {
            border = new Image(getClass().getResource(IMAGE_BASE_PATH + folder + "/border.png").toString(), squareSize, squareSize, false, false);
            wall = new Image(getClass().getResource(IMAGE_BASE_PATH + folder + "/wall.png").toString(), squareSize, squareSize, false, false);
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
        collectibles.add(collectible);
        return collectible;
    }

    /**
     * Startar spelrundan och timern.
     */
    public void startLevel() { //TODO timern fungerar inte som den ska
        startPlayer.play();
        startPlayer.seek(Duration.ZERO);
        gameStarted = true;
    }

    public void setPlayerOnStart(int x, int y) {
        if (level[y - 1][x - 1] == 2) {
            updatePlayerImage(x, y);
        }
    }

    public void updatePlayerImage(int x, int y) { // sker varje gång spelaren går ett steg

        if(player == null){
            this.player = new KeyboardPlayer(x, y);
        }

        Label playerLabel = new Label();
        ImageView playerView = new ImageView(playerImage);

        playerView.setFitHeight(squareSize);
        playerView.setFitWidth(squareSize);
        playerLabel.setGraphic(playerView);

        getChildren().removeIf(node -> node instanceof Label && ((Label) node).getGraphic() instanceof ImageView && ((ImageView) ((Label) node).getGraphic()).getImage() == playerImage);

        add(playerLabel, x, y);
    }

    public boolean hitWall(int newX, int newY){ //TODO lägg till if-sats för breakable wall

        if (newX == 0 || newY == 0 || newX == level.length+1 || newY == level.length+1 || level[newY - 1][newX - 1] == 0) { //kolla om spelaren försöker gå utanför banan eller in i en vägg

            FadeTransition fade = new FadeTransition();
            fade.setDuration(Duration.seconds(0.3));
            fade.setFromValue(10);
            fade.setToValue(0.6);
            fade.play();

            audioPlayer.playDeathSound();

            return true; // lägg till sound för fel
        }
        else {
            return false; // om allt funkar som det ska
        }
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

    public void checkCollectibles(int x, int y) { //Plockar upp diamanter

        if(level[y - 1][x - 1] == 4) {

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
        }
    }

    public void checkReachedGoal(int x, int y) throws InterruptedException, FileNotFoundException {
        if ((level[y - 1][x - 1] == 3) && (allCollectiblesObtained)) {
            audioPlayer.stopClockSound();
            audioPlayer.playGoalSound();
            //rightPanel.pauseClock();
            //rightPanel.setTheTime(seconds);
            gameStarted = true;
            //time.setGameOver(true);
            //time = null;
            //nextLevelKeyboard(x, y);
            generateNextLevel.generateNewMaze();
            setPlayerOnStart(x, y);
        }
    }

}
