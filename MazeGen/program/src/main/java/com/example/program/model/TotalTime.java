package com.example.program.model;

/**
 * @author Sebastian Helin
 * Tråd som räknar den totala tiden det tog att spela
 */
public class TotalTime extends Thread {

    private boolean gameOver;
    private int seconds;
    private int minutes;

    /**
     * @param gameOver Tar en boolean som används som villkor
     */
    public TotalTime(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Tråden sover i 1000 millisekunder
     * Inkrementerar int variabeln med 1
     */
    public void run() {

        while (!gameOver) {
            try {
                Thread.sleep(1000);
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total tid: " + minutes + ":" + seconds);
    }

    /**
     * @param gameOver Setter för villkoret
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
