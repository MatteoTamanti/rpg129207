package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;

public class Player extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    public Player(int lifePoints, int attack, double attackCooldownSeconds, double x, double y) {
        super(x, y, lifePoints, attack, attackCooldownSeconds);
    }

    @Override
    public void update(Map map, Player player) {
    }
}