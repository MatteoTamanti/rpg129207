package it.unicam.cs.mpgc.rpg129207.controller;

import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.Enemy;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import java.util.List;

public class EnemySpawner {

    private final double triggerX;
    private final double triggerY;
    private final double triggerWidth;
    private final double triggerHeight;
    private final int enemyCount;
    private boolean triggered;

    public EnemySpawner(double triggerX, double triggerY, double triggerWidth, double triggerHeight, int enemyCount) {
        this.triggerX = triggerX;
        this.triggerY = triggerY;
        this.triggerWidth = triggerWidth;
        this.triggerHeight = triggerHeight;
        this.enemyCount = enemyCount;
        this.triggered = false;
    }

    public void update(Player player, List<Entity> entities) {

        if (triggered) {
            return;
        }
        if (isPlayerInside(player)) {
            spawnEnemies(entities);
            triggered = true;
        }
    }

    private boolean isPlayerInside(Player player) {
        return player.getX() >= triggerX && player.getX() <= triggerX + triggerWidth && player.getY() >= triggerY && player.getY() <= triggerY + triggerHeight;
    }

    private void spawnEnemies(List<Entity> entities) {
        for (int i = 0; i < enemyCount; i++) {
            entities.add(new Enemy(30, 5,1 ,  triggerX + i * 32, triggerY));
        }
    }
}