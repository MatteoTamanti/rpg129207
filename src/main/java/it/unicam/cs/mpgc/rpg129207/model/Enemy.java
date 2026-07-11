package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;

public class Enemy extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double SPEED = 1.0;

    private int lifePoints;
    private int attack;

    public Enemy(int lifePoints, int attack, double x, double y) {
        super(x, y);
        this.lifePoints = lifePoints;
        this.attack = attack;
    }

    @Override
    public void update(Map map, Player player) {
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            move((dx / distance) * SPEED, (dy / distance) * SPEED, map);
        }
    }
}
