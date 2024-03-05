package com.example.program.model;

import com.example.program.view.Menu.RightPanel;

public class TimeThread extends Thread {

    private int seconds;
    private final RightPanel panel;
    private boolean gameOver = false;
    // Declare a volatile boolean for pause control
    private volatile boolean isPaused = false;

    public TimeThread(int seconds, RightPanel panel) {
        this.seconds = seconds;
        this.panel = panel;
    }

    public void run() {
        while (!gameOver) {
            try {
                // Check if the thread is paused
                synchronized (this) {
                    while (isPaused) {
                        wait(); // Wait until notified to resume
                    }
                }

                Thread.sleep(1000);

                if (seconds > 0) {
                    System.out.println(seconds);
                    seconds--;
                }
                if (seconds == 5) {
                    panel.fiveSecLeft();
                }
                if (seconds == 0) {
                    panel.startTask();
                    gameOver = true;
                }
                //panel.setTheTime(seconds);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interrupted exception
                e.printStackTrace();
            }
        }
        panel.timeRemaining(seconds);
    }


    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public synchronized void pauseTime() {
        isPaused = true; // Set the pause flag
    }

    public synchronized void resumeTime() {
        isPaused = false; // Clear the pause flag
        notifyAll(); // Notify to resume
    }
}