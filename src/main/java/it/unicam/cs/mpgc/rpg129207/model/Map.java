package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;

public class Map implements Serializable {
    private static final long serialVersionUID = 1L;

    private TileType[][] matrix;
    private int mapX;
    private int mapY;
    private int tileSize;


    public Map(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
        this.tileSize = 32;
        this.matrix = new TileType[mapY][mapX];
    }

    public int getWidth() {
        return mapX;
    }

    public int getHeight() {
        return mapY;
    }

    public int getTileSize() {
        return tileSize;
    }

    public TileType getTile(int y, int x) {
        return matrix[y][x];
    }

    public void setTile(int y, int x, TileType tile) {
        matrix[y][x] = tile;
    }
}