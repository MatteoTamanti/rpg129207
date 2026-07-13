package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;

public class Player extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_LIFE_POINTS = 100;
    private static final int DEFAULT_ATTACK = 15;
    private static final double DEFAULT_ATTACK_COOLDOWN = 0.3;

    public Player(double x, double y) {
        this(DEFAULT_LIFE_POINTS, DEFAULT_ATTACK, DEFAULT_ATTACK_COOLDOWN, x, y);
    }

    public Player(int lifePoints, int attack, double attackCooldownSeconds, double x, double y) {
        super(x, y, lifePoints, attack, attackCooldownSeconds);
    }

    @Override
    public void update(Map map, Player player) {
    }
}