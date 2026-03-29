package it.unicam.cs.mpgc.rpg129207.model;

/*
Per creare la mappa mi serve prima una matrice al cui interno
delimiterò una mappa decidendo dove il personaggio può o non può andare (circa)
*/

public class Map {
    private int [][] matrix;

    public Map(int width, int height) {
        matrix = new int [width][height];
    }

    public int getCoord(int i, int j) {
        return matrix[i][j];
    }

    public void setCoord(int i, int j, int v) {
        matrix[i][j] = v;
    }

}
