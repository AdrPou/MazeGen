package com.example.program.model;

import java.io.*;
import java.util.Scanner;


/**
 * Class that reads the text file HighScore and changes it if there is a new one.
 */
public class HighScore {

    private String[] name = new String[10];
    private int[] score = new int[10];
    private String[] list = new String[10];
    private int place = 1;
    private final String path = "/com/example/program/files/HighScoreList.txt";

    /**
     * Inputs a new person and time to the highScoreList.
     *
     * @param name  the name of the person.
     * @param time how long it took to win.
     */
    public void writeToList(String[] name, int[] time) {
        try {
            FileWriter myWriter = new FileWriter(path);
            for (int i = 0; i < name.length; i++) {
                myWriter.write(name[i] + "\n" + time[i] + "\n");
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
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @return The scores from the highscorelist
     */
    public int[] getScore() {
        return score;
    }

    /**
     * @return The names from the highscorelist
     */
    public String[] getName() {
        return name;
    }

    public String[] getList() {
        return list;
    }

}