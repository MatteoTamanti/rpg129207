package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;

public class Enemy extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final double SPEED = 1.0;
    private static final double ATTACK_RANGE = 20;
    private static final int DEFAULT_LIFE_POINTS = 30;
    private static final int DEFAULT_ATTACK = 5;
    private static final double DEFAULT_ATTACK_COOLDOWN = 1.5;

    public Enemy(double x, double y) {
        this(DEFAULT_LIFE_POINTS, DEFAULT_ATTACK, DEFAULT_ATTACK_COOLDOWN, x, y);
    }

    public Enemy(int lifePoints, int attack, double attackCooldownSeconds, double x, double y) {
        super(x, y, lifePoints, attack, attackCooldownSeconds);
    }

    @Override
    public void update(Map map, Player player) {
        if (!isAlive()) {
            return;
        }

        if (isInRange(player, ATTACK_RANGE)) {
            attackPlayer(player);
        } else {
            chasePlayer(map, player);
        }
    }

    private void chasePlayer(Map map, Player player) {
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            move((dx / distance) * SPEED, (dy / distance) * SPEED, map);
        }
    }

    private void attackPlayer(Player player) {
        if (canAttack()) {
            player.takeDamage(getAttack());
            resetAttackCooldown();
        }
    }
}