package com.example.program.view.Randomize;


import com.example.program.model.KeyboardPlayer;
import com.example.program.model.MazeGeneration.GenerateNextLevel;
import com.example.program.control.MainProgram;
import javafx.animation.FadeTransition;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.media.Media;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author André Eklund
 * edit Viktor Näslund
 */

public class MapTemplate extends GridPane {

    private final MainProgram mainProgram;
    private final GenerateNextLevel generateNextLevel;
    private final int[][] level;
    private final ArrayList<Label> collectibles = new ArrayList<>();
    private final MouseListener mouseListener = new MouseListener();
    private boolean startButtonPressed;
    private boolean allCollectiblesObtained;
    private int collectiblesObtained = 0;
    private final int squareSize;
    private Image wall;
    private Image path;
    private Image border;
    private Image goal;
    private Image diamond;
    private Image start;

    private static final String IMAGE_BASE_PATH = "/com/example/program/files/";
    private static final String SOUND_BASE_PATH = "/com/example/program/files/sounds/";

    private final MediaPlayer diamondPlayer = new MediaPlayer(new Media(getClass().getResource(SOUND_BASE_PATH + "Diamond1.mp3").toString()));
    private final MediaPlayer deathPlayer = new MediaPlayer(new Media(getClass().getResource(SOUND_BASE_PATH + "MazegenDeath.mp3").toString()));

    private final MediaPlayer startPlayer = new MediaPlayer(new Media(getClass().getResource(SOUND_BASE_PATH + "MazegenStart.mp3").toString()));
    private final MediaPlayer goalPlayer = new MediaPlayer(new Media(getClass().getResource(SOUND_BASE_PATH + "MazegenGoal.mp3").toString()));

    private KeyboardPlayer player;
    private Image playerImage;


    /**
     * Konstruktorn ska kunna ta emot int-arrayer och representera dem i GUIt
     */
    public MapTemplate(int[][] level, MainProgram mainProgram, GenerateNextLevel generateNextLevel) {
        this.level = level;
        this.generateNextLevel = generateNextLevel;
        this.mainProgram = mainProgram;

        squareSize = 600 / (level.length + 2);
        setBackground();
        setupImages(new Random().nextInt(6));
        setupBorders();
        setupLevel();
    }

    /**
     * Sätter bakgrunden i fönstret.
     */
    public void setBackground() {
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
                    add(getPath(), j + 1, i + 1);
                    if (new Random().nextInt(5) == 4) {
                        add(addCollectible(), j + 1, i + 1);
                    }
                } else if (level[i][j] == 0) {
                    add(getWall(), j + 1, i + 1);
                } else if (level[i][j] == 2) {
                    add(getStart(), j + 1, i + 1);
                } else if (level[i][j] == 3) {
                    add(getGoal(), j + 1, i + 1);
                }
            }
        }
    }

    /**
     * Instansierar de olika bilderna som används som grafik inuti spelet.
     * Baserad på value så sätts bilderna till en specifik folder per värld.
     *
     * @param value Den aktuella världen.
     */
    public void setupImages(int value) {

        String folder = "";

        if (value == 0) {
            folder = "forest";
        } else if (value == 1) {
            folder = "lava";
        } else if (value == 2) {
            folder = "underground";
        } else if (value == 3) {
            folder = "cloud";
        } else if (value == 4) {
            folder = "desert";
        } else if (value == 5) {
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     * Om spelaren vidrör muspekaren vid en vägg avslutas spelrundan.
     *
     * @param e Används för att hitta rätt label.
     */
    public void enteredWall(MouseEvent e) {
        Label label = (Label) e.getSource();
        FadeTransition fade = new FadeTransition();
        fade.setNode(label);
        fade.setDuration(Duration.seconds(0.3));
        fade.setFromValue(10);
        fade.setToValue(0.6);
        fade.play();

        if (startButtonPressed) {
            deathPlayer.play();
            deathPlayer.seek(Duration.ZERO);
            startButtonPressed = false;
        }
    }

    /**
     * Om spelrundan är aktiverad och spelaren har plockat upp alla collectibles startas nästa nivå.
     *
     * @throws FileNotFoundException if file not found.
     * @throws InterruptedException  if thread was interrupted.
     */
    public void enteredGoal() throws FileNotFoundException, InterruptedException {
        if (startButtonPressed && allCollectiblesObtained) {
            goalPlayer.play();
            goalPlayer.seek(Duration.ZERO);
            generateNextLevel.generateNewMaze();
        }
    }

    /**
     * Startar spelrundan och timern.
     */
    public void startLevel() {
        startPlayer.play();
        startPlayer.seek(Duration.ZERO);
        startButtonPressed = true;
    }

    /**
     * När muspekaren lämnar en label slutar den att highlightas.
     *
     * @param e Används för att hitta rätt label.
     */
    private void exitedLabel(MouseEvent e) {
        Label label = (Label) e.getSource();
        FadeTransition fade = new FadeTransition();
        fade.setNode(label);
        fade.setDuration(Duration.seconds(0.3));
        fade.setFromValue(0.6);
        fade.setToValue(10);
        fade.play();
    }

    /**
     * En listener som körs när spelaren plockar upp en collectible.
     */
    private class MouseListener implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {
            if (startButtonPressed) {
                diamondPlayer.play();
                diamondPlayer.seek(Duration.ZERO);
                for (Label label : collectibles) {
                    if (e.getSource() == label) {
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
}
