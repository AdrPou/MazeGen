package com.example.program.model.Maps;

/**
 * @author André Eklund, Sebastian Helin, Viktor Näslund, Filip Örnling
 */

public class World1Maps {

    private int[][] level11 = {{0, 0, 0, 0, 0, 0, 1, 3},
                               {0, 0, 1, 1, 4, 1, 1, 1},
                               {0, 0, 1, 0, 0, 0, 0, 0},
                               {0, 4, 1, 1, 1, 0, 0, 0},
                               {0, 0, 0, 0, 1, 0, 0, 0},
                               {0, 0, 0, 1, 1, 1, 4, 0},
                               {1, 4, 1, 1, 0, 0, 0, 0},
                               {2, 1, 1, 0, 0, 0, 0, 0}};

    private int[][] level12 = {{0, 0, 0, 0, 0, 0, 1, 2},
                               {0, 4, 1, 0, 1, 1, 1, 0},
                               {0, 0, 1, 1, 1, 0, 1, 0},
                               {0, 0, 0, 0, 0, 1, 4, 0},
                               {0, 0, 1, 1, 1, 1, 0, 0},
                               {0, 0, 1, 0, 0, 0, 0, 0},
                               {0, 4, 1, 0, 1, 1, 4, 1},
                               {0, 0, 1, 1, 4, 0, 0, 3}};

    private int[][] level13 = {{4, 0, 0, 0, 0, 0, 0, 4},
                               {1, 0, 1, 1, 4, 1, 1, 1},
                               {1, 1, 1, 0, 0, 1, 0, 0},
                               {0, 1, 0, 4, 0, 1, 1, 0},
                               {0, 1, 0, 1, 0, 0, 1, 0},
                               {0, 1, 1, 1, 4, 0, 1, 0},
                               {0, 0, 0, 0, 1, 0, 4, 1},
                               {0, 3, 1, 4, 1, 0, 0, 2}};

    private int[][] level14 = {{0, 0, 0, 0, 0, 0, 0, 0},
                               {4, 1, 0, 1, 4, 1, 0, 4},
                               {0, 1, 0, 1, 0, 1, 0, 1},
                               {1, 1, 1, 1, 0, 4, 1, 1},
                               {4, 0, 0, 0, 0, 0, 0, 1},
                               {1, 1, 0, 1, 4, 1, 0, 4},
                               {0, 1, 0, 1, 0, 1, 4, 0},
                               {0, 2, 1, 1, 0, 0, 1, 3}};

    private int[][] level15 = {{0, 0, 0, 0, 0, 4, 0, 0},
                               {0, 4, 1, 5, 0, 1, 1, 1},
                               {0, 1, 0, 0, 0, 4, 0, 1},
                               {0, 1, 0, 0, 0, 0, 1, 1},
                               {0, 1, 1, 1, 1, 1, 1, 0},
                               {0, 6, 0, 1, 1, 0, 0, 0},
                               {4, 7, 4, 0, 1, 1, 1, 0},
                               {4, 3, 4, 0, 0, 0, 1, 2}};

    /**
     * Returnerar en array som representerar en nivå i spelet.
     * @return returnerar en array av siffror.
     */
    public int[][] getLevel11(){
        return level11;
    }

    public int[][] getLevel12(){
        return level12;
    }

    public int[][] getLevel13(){
        return level13;
    }

    public int[][] getLevel14(){
        return level14;
    }

    public int[][] getLevel15() {
        return level15;
    }
}
