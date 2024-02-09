package com.example.program.model;

import javafx.scene.image.Image;

public class KeyboardPlayer { //this class will be responsible for controlling the player

    private double x;
    private double y;
    // implementera speed här?

    public KeyboardPlayer(double x, double y){
        this.x = y;
        this.y = y;
    }

    //metod för move?

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
       this.x = x;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
