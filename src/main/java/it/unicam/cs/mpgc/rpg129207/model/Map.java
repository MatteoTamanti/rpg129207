package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;

public class Map implements Serializable {
    private static final long serialVersionUID = 1L;

    private int [][] matrix;
    private int mapX;
    private int mapY;
    private int pixelPerCell;

    public Map(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
        matrix = new int [mapY][mapX];
        this.pixelPerCell = 32;
    }

    public int getMapX() {
        return mapX;
    }
    public int getMapY() {
        return mapY;
    }

    public int getPixelPerCell() {
        return pixelPerCell;
    }

    public int getCoord(int i, int j) {
        return matrix[i][j];
    }

    public void setCoord(int i, int j, int v) {
        matrix[i][j] = v;
    }

}
