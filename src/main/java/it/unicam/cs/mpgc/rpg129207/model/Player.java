package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;

public class Player extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int lifePoints;
    private int attack;

    public Player(int lifePoints, int attack, double x, double y) {
        super(x, y);
        this.lifePoints = lifePoints;
        this.attack = attack;
    }

    @Override
    public void update(Map map,  Player player) {

    }


    public double getPlayerX() {
        return x;
    }

    public double getPlayerY() {
        return y;
    }

}

