package com.example.program.view.Campaign;

import com.example.program.control.MainProgram;
import com.example.program.model.KeyboardPlayer;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;


public class KeyBoardCampaign extends GridPane {

    private KeyboardPlayer player;
    private Image image;
    private World1Template campaign;
    private int squareSize;
    private static final String BASE_PATH = "/com/example/program/files/";


    public KeyBoardCampaign(KeyboardPlayer player, World1Template campaign) {
        this.player = player;
        this.campaign = campaign;
        squareSize = 600/(campaign.getLevel()+2);
        image = new Image(getClass().getResource(BASE_PATH + "playerTest.png").toString(), squareSize, squareSize, false, false);

        // addPlayerImage(int x, int y);
        setOnKeyPressed(event -> handleKeyPressed(event));
        setOnKeyReleased(event -> handleKeyReleased(event));
    }

    /*
    public void addPlayerImage(int x, int y) {
        Label playerLabel = new Label();
        ImageView playerView = new ImageView(playerImage);
        playerView.setFitHeight(squareSize);
        playerView.setFitWidth(squareSize);
        playerLabel.setGraphic(playerView);
        add(playerLabel, x, y);
    }

     */

    private void handleKeyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        switch (keyCode) {
            case UP:
                // Move the player image up
                break;
            case DOWN:
                // Move the player image down
                break;
            case LEFT:
                // Move the player image left
                break;
            case RIGHT:
                // Move the player image right
                break;
            default:
                // Handle other keys if needed
                break;
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        // Handle key released event if needed
    }


    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        getChildren().clear();
        getChildren().add(new ImageView(image));
    }

    @Override
    protected double computePrefWidth(double height) {
        return 100;
    }

    @Override
    protected double computePrefHeight(double width) {
        return 100;
    }

}
