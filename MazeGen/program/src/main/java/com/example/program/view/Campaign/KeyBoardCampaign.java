package com.example.program.view.Campaign;

import com.example.program.model.KeyboardPlayer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardCampaign extends GridPane implements KeyListener {

    private KeyboardPlayer player;
    private Image image;
    private static final String BASE_PATH = "com/example/program/files/";


    public KeyBoardCampaign(KeyboardPlayer player) {
        this.player = player;
        image = new Image(getClass().getResource(BASE_PATH + "settings.png").toString());
        setPrefSize(100, 100);
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


    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            player.setY(player.getY() - 5); //ändra senare

        } else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            player.setY(player.getY() + 5);

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.setX(player.getX() + 5);

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            player.setX(player.getX() - 5);

        } else {
            //lägg till ljud för fel knapp?
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
