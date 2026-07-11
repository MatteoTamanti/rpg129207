package it.unicam.cs.mpgc.rpg129207.controller;

import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.Enemy;
import it.unicam.cs.mpgc.rpg129207.model.Player;

import java.util.List;

public class PlayerCombatHandler {

    private static final double ATTACK_RANGE = 30;

    public void update(Player player, List<Entity> entities, InputController inputController) {
        if (!inputController.consumeKey(javafx.scene.input.KeyCode.SPACE)) {
            return;
        }

        if (!player.canAttack()) {
            return;
        }

        Enemy target = findNearestEnemy(player, entities);

        if (target != null && player.isInRange(target, ATTACK_RANGE)) {
            target.takeDamage(player.getAttack());
            player.resetAttackCooldown();
        }
    }

    private Enemy findNearestEnemy(Player player, List<Entity> entities) {
        Enemy nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Entity e : entities) {
            if (e instanceof Enemy enemy && enemy.isAlive()) {
                double dx = enemy.getX() - player.getX();
                double dy = enemy.getY() - player.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearest = enemy;
                }
            }
        }

        return nearest;
    }
}