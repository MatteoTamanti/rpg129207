package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;


public abstract class Entity implements Serializable {
    protected double x, y;

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update(Map map);

    public double getX() { return x; }
    public double getY() { return y; }
}
