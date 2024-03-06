package com.example.program.model;

import javafx.scene.image.Image;

public class KeyboardPlayer { //this class will be responsible for controlling the player

    private int x;
    private int y;
    private int speed;

    public KeyboardPlayer(int x, int y){
        this.x = x;
        this.y = y;
        this.speed = 1;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
       this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void move(int newX, int newY) {
        System.out.println("Moving to " + newX + ", " + newY);
        this.x = newX;
        this.y = newY;
    }
}
