package com.example.program.view.Menu;

import com.example.program.control.MainProgram;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import com.example.program.view.AudioPlayer;

import java.io.FileNotFoundException;

/**
 * @author Viktor Näslund
 */

public class Menu extends Pane {
    private final MainProgram mainProgram;

    public Image getCampaign() {
        return campaign;
    }

    public Image getRandomize() {
        return randomize;
    }

    private Image campaign;
    private Image campaignResize;
    private Image randomize;
    private Image randomizeResize;
    private Image help;
    private Image helpResize;
    private Image mazegen;

    public Image getSettings() {
        return settings;
    }

    private Image settings;
    private Image settingsResize;
    private Image highScore;
    private Image highScoreResize;
    private final AudioPlayer audioPlayer;
    private final RightPanel panel;

    /**
     * Konstruktor som tar emot mainProgram, audioPlayer och panel
     * Kör sedan metoder för att länka Image-objekten med png-filer
     *
     * @param mainProgram tas emot och sätts
     * @param audioPlayer tas emot och sätts
     * @param panel       tas emot och sätts
     */
    public Menu(MainProgram mainProgram, AudioPlayer audioPlayer, RightPanel panel) {
        this.mainProgram = mainProgram;
        this.audioPlayer = audioPlayer;
        this.panel = panel;
        setBackground();
        setupImages();
        addButtons();
    }

    /**
     * Metod som länkar Image-objekten till png-filer
     */
    public void setupImages() {
        mazegen = new Image(getClass().getResource("/com/example/program/files/texts/MazegenTitel.png").toString(), 800, 600, false, false);
        campaign = new Image(getClass().getResource("/com/example/program/files/texts/Campaign.png").toString(), 250, 30, false, false);
        campaignResize = new Image(getClass().getResource("/com/example/program/files/texts/Campaign.png").toString(), 255, 33, false, false);
        randomize = new Image(getClass().getResource("/com/example/program/files/texts/Randomize.png").toString(), 250, 30, false, false);
        randomizeResize = new Image(getClass().getResource("/com/example/program/files/texts/Randomize.png").toString(), 255, 33, false, false);
        help = new Image(getClass().getResource("/com/example/program/files/texts/Help.png").toString(), 250, 30, false, false);
        helpResize = new Image(getClass().getResource("/com/example/program/files/texts/Help.png").toString(), 255, 33, false, false);
        settings = new Image(getClass().getResource("/com/example/program/files/texts/settings.png").toString(), 250, 30, false, false);
        settingsResize = new Image(getClass().getResource("/com/example/program/files/texts/settings.png").toString(), 255, 33, false, false);
        highScore = new Image(getClass().getResource("/com/example/program/files/texts/Highscore.png").toString(), 250, 30, false, false);
        highScoreResize = new Image(getClass().getResource("/com/example/program/files/texts/Highscore.png").toString(), 255, 33, false, false);
    }

    /**
     * Metod som sätter bakgrundsbilden
     */
    public void setBackground() {
        BackgroundImage menuBackground = new BackgroundImage(new Image(getClass().getResource("/com/example/program/files/MenuBackground.jpg").toString(), 800, 600, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(menuBackground));
    }

    /**
     * Metod som lägger till klickbara ImageViews i scenen
     * Imageviews förstoras när man hovrar och byter scen när man klickar på dem
     */
    public void addButtons() {
        ImageView mazegenView = new ImageView(mazegen);
        mazegenView.setStyle("fx-background-color: transparent;");


        ImageView campaignView = new ImageView(campaign);
        campaignView.setStyle("fx-background-color: transparent;");
        campaignView.setTranslateX(275);
        campaignView.setTranslateY(200);
        campaignView.toFront();
        campaignView.setOnMouseEntered(e -> {
            campaignView.setImage(campaignResize);
            campaignView.setTranslateX(273);
            campaignView.setTranslateY(197);
        });
        campaignView.setOnMouseExited(e -> {
            campaignView.setImage(campaign);
            campaignView.setTranslateX(275);
            campaignView.setTranslateY(200);
        });
        campaignView.setOnMouseClicked(e -> {
            mainProgram.changeToStartingScene();
            /*mainProgram.changeToCampaign();
            audioPlayer.playLevelMusic("forest");
            panel.setTheTime(25);
            panel.resetTimerLabel();

             */
        });

        ImageView randomizeView = new ImageView(randomize);
        randomizeView.setStyle("fx-background-color: transparent;");
        randomizeView.setTranslateX(275);
        randomizeView.setTranslateY(250);
        randomizeView.toFront();
        randomizeView.setOnMouseEntered(e -> {
            randomizeView.setImage(randomizeResize);
            randomizeView.setTranslateX(273);
            randomizeView.setTranslateY(247);
        });
        randomizeView.setOnMouseExited(e -> {
            randomizeView.setImage(randomize);
            randomizeView.setTranslateX(275);
            randomizeView.setTranslateY(250);
        });
        randomizeView.setOnMouseClicked(e -> {
            mainProgram.chooseDimension();
            audioPlayer.playButtonSound();
        });

        ImageView settingsView = new ImageView(settings);
        settingsView.setStyle("fx-background-color: transparent;");
        settingsView.setTranslateX(275);
        settingsView.setTranslateY(300);
        settingsView.toFront();
        settingsView.setOnMouseEntered(e -> {
            settingsView.setImage(settingsResize);
            settingsView.setTranslateX(273);
            settingsView.setTranslateY(297);
        });
        settingsView.setOnMouseExited(e -> {
            settingsView.setImage(settings);
            settingsView.setTranslateX(275);
            settingsView.setTranslateY(300);
        });
        settingsView.setOnMouseClicked(e -> {
            mainProgram.changeToSettings();
            audioPlayer.playButtonSound();
        });

        ImageView highScoreView = new ImageView(highScore);
        highScoreView.setStyle("fx-background-color: transparent;");
        highScoreView.setTranslateX(275);
        highScoreView.setTranslateY(350);
        highScoreView.toFront();
        highScoreView.setOnMouseEntered(e -> {
            highScoreView.setImage(highScoreResize);
            highScoreView.setTranslateX(273);
            highScoreView.setTranslateY(347);
        });
        highScoreView.setOnMouseExited(e -> {
            highScoreView.setImage(highScore);
            highScoreView.setTranslateX(275);
            highScoreView.setTranslateY(350);
        });
        highScoreView.setOnMouseClicked(e -> {
            mainProgram.changeToHighScore();
            audioPlayer.playButtonSound();
        });


        ImageView helpView = new ImageView(help);
        helpView.setStyle("fx-background-color: transparent;");
        helpView.setTranslateX(275);
        helpView.setTranslateY(400);
        helpView.toFront();
        helpView.setOnMouseEntered(e -> {
            helpView.setImage(helpResize);
            helpView.setTranslateX(273);
            helpView.setTranslateY(397);
        });
        helpView.setOnMouseExited(e -> {
            helpView.setImage(help);
            helpView.setTranslateX(275);
            helpView.setTranslateY(400);
        });
        helpView.setOnMouseClicked(e -> {
            mainProgram.changeToHelp();
            audioPlayer.playButtonSound();
        });

        this.getChildren().addAll(campaignView, randomizeView, helpView, settingsView, highScoreView, mazegenView);
    }

}
