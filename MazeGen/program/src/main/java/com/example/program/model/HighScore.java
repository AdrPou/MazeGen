package com.example.program.model;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;


/**
 * Class that reads the text file HighScore and changes it if there is a new one.
 */
public class HighScore {

    private String[] name = new String[10];
    private int[] score = new int[10];
    private final String[] list = new String[10];
    private int place = 1;
    private final String path = "MazeGen\\program\\src\\main\\java\\files\\HighScoreList.txt";
    //private final String path = "/Users/adamleijman/Desktop/MazeGen/MazeGen/program/src/main/resources/com/example/program/files/HighScoreList.txt";


    /**
     * Inputs a new person and time to the highScoreList.
     *
     * @param name  the name of the person.
     * @param score how long it took to win.
     */
    public void writeToList(String[] name, int[] score) {
        try {
            FileWriter myWriter = new FileWriter(path);
            for (int i = 0; i < 10; i++) {
                if (name[i] != null) {
                    myWriter.write(name[i] + "\n" + score[i] + "\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Could not write to HighScoreList");
            e.printStackTrace();
        }
    }


    /**
     * Reads the text from HighScoreList.
     *
     * @return the highscorelist
     */
    public String[] readList() {
        int i = 0;
        try {
            File myFile = new File(path);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                name[i] = myReader.nextLine();
                score[i] = Integer.parseInt(myReader.nextLine());
                if (name[i] != null) {
                    list[i] = String.format(place + " " + name[i] + " " + score[i]);
                    i++;
                    place++;
                }
            }
            // Initialize the remaining scores to the lowest value
            for (int j = i; j < 10; j++) {
                score[j] = Integer.MIN_VALUE;
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return list;
    }


    public boolean checkNewScore(int newScore) {
        // Find the position of the new score
        int newPosition = 0;
        newScore = newScore * 50;
        while (newPosition < 10 && newScore <= score[newPosition]) {
            newPosition++;
        }

        // If the new score is in the top 10
        if (newPosition < 10) {
            String newName = JOptionPane.showInputDialog(null, "You are in the Top10! \n" + "Enter name");

            // Shift existing scores down to make room for the new score
            for (int i = 9; i > newPosition; i--) {
                score[i] = score[i - 1];
                name[i] = name[i - 1];
            }

            // Insert the new score
            score[newPosition] = newScore;
            name[newPosition] = newName;

            // Write the updated list to file and return true
            writeToList(name, score);
            return true;
        } else {
            // If the new score is not in the top 10, return false
            return false;
        }
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public int[] getScore() {
        return score;
    }

    public void setScore(int[] score) {
        this.score = score;
    }
}
