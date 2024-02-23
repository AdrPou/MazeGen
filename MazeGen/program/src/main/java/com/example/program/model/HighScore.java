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
    private final String path = "/Users/adamleijman/Kurser/År2/DA489A_Systemutveckling2/MazeGen/MazeGen/program/src/main/resources/com/example/program/files/HighScoreList.txt";

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

    public boolean checkNewScore(String newName, int newScore) {
        if(newScore < score[9]) {
            score[9] = newScore;
            name[9] = newName;
            sortList();
            return true;
        }
        else {return false;}
    }

    /**
     * Sorts the highscorelist by score in descending order.
     */
    private void sortList() {
        int n = list.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (score[i] > score[i + 1]) {
                    // Byt plats på poängen
                    int tempScore = score[i];
                    score[i] = score[i + 1];
                    score[i + 1] = tempScore;

                    // Byt plats på namnen
                    String tempName = name[i];
                    name[i] = name[i + 1];
                    name[i + 1] = tempName;

                    // Byt plats på listelementen
                    String tempList = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = tempList;

                    swapped = true;
                }
            }
            n--;
        } while (swapped);
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