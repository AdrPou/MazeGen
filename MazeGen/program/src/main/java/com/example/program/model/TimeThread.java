package com.example.program.model;

import com.example.program.view.Menu.RightPanel;

/**
 * @author Sebastian Helin
 * En tråd som räknar ner den
 * totala tiden som skickas in från konstruktorn
 */

public class TimeThread extends Thread {

    private int seconds;
    private final RightPanel panel;
    private boolean gameOver = false;

    /**
     * @param seconds Varje nivå skickar in en seconds variabel till tråden
     * @param panel   Höger panelen som visar information när man spelar.
     */
    public TimeThread(int seconds, RightPanel panel) {
        this.seconds = seconds;
        this.panel = panel;
    }

    /**
     * Körs tills det blir GameOver eller en bana blir avklarad
     * Instansieras om och körs igen för varje nivå
     */
    public void run() {
        while (!gameOver) {
            try {
                Thread.sleep(1000);
                if (seconds > 0) {
                    seconds--;
                }
                if (seconds == 5) {
                    panel.fiveSecLeft();
                }
                if (seconds == 0) {
                    panel.startTask();
                    gameOver = true;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        panel.timeRemaining(seconds);
    }

    /**
     * @param gameOver setter för trådens villkor
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

}
