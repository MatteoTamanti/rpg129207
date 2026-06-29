package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;


public abstract class Entity implements Serializable {
    protected double x, y;

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public void move(double dx, double dy, Map map) {
        double maxX = map.getMapX() * map.getPixelPerCell();
        double maxY = map.getMapY() * map.getPixelPerCell();

        double temporaryX = x + dx;
        double temporaryY = y + dy;


        if (temporaryX > maxX) {
            x = maxX;
        } else if (temporaryX < 0) {
            x = 0;
        } else {
            x = temporaryX;
        }


        if (temporaryY > maxY) {
            y = maxY;
        } else if (temporaryY < 0) {
            y = 0;
        } else {
            y = temporaryY;
        }
    }


    public abstract void update(Map map, Player player);

    public double getX() { return x; }
    public double getY() { return y; }
}
