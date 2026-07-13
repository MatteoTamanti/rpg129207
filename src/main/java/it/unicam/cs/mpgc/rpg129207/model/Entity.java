package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;
import it.unicam.cs.mpgc.rpg129207.model.Map;

public abstract class Entity implements Serializable {

    private static final double DEFAULT_ENTITY_SIZE = 32;
    private static final long NANOS_PER_SECOND = 1_000_000_000;

    protected double x;
    protected double y;
    protected final double size;

    private final int maxLifePoints;
    private int lifePoints;
    private final int attack;
    private final long attackCooldownNanos;
    private long lastAttackTime;

    public Entity(double x, double y, int lifePoints, int attack, double attackCooldownSeconds) {
        this(x, y, lifePoints, attack, attackCooldownSeconds, DEFAULT_ENTITY_SIZE);
    }

    public Entity(double x, double y, int lifePoints, int attack, double attackCooldownSeconds, double size) {
        this.x = x;
        this.y = y;
        this.lifePoints = lifePoints;
        this.attack = attack;
        this.attackCooldownNanos = (long) (attackCooldownSeconds * NANOS_PER_SECOND);
        this.lastAttackTime = 0;
        this.maxLifePoints = lifePoints;
        this.size = size;
    }

    public abstract void update(Map map, Player player);

    public void move(double dx, double dy, Map map) {
        if (canMove(x + dx, y, map)) {
            x += dx;
        }

        if (canMove(x, y + dy, map)) {
            y += dy;
        }
    }

    private boolean canMove(double newX, double newY, Map map) {
        int tileSize = map.getTileSize();

        int left = (int) (newX / tileSize);
        int right = (int) ((newX + size - 1) / tileSize);
        int top = (int) (newY / tileSize);
        int bottom = (int) ((newY + size - 1) / tileSize);

        if (left < 0 || right >= map.getWidth()
                || top < 0 || bottom >= map.getHeight()) {
            return false;
        }

        for (int tileY = top; tileY <= bottom; tileY++) {
            for (int tileX = left; tileX <= right; tileX++) {
                if (map.getTile(tileY, tileX) == TileType.WALL) {
                    return false;
                }
            }
        }

        return true;
    }

    public double distanceTo(Entity other) {
        double dx = other.x - x;
        double dy = other.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean isInRange(Entity other, double range) {
        return distanceTo(other) <= range;
    }

    public boolean canAttack() {
        return System.nanoTime() - lastAttackTime >= attackCooldownNanos;
    }

    public void resetAttackCooldown() {
        lastAttackTime = System.nanoTime();
    }

    public void takeDamage(int amount) {
        lifePoints -= amount;
        if (lifePoints < 0) {
            lifePoints = 0;
        }
    }

    public boolean isAlive() {
        return lifePoints > 0;
    }

    public int getMaxLifePoints() { return maxLifePoints; }
    public double getX() { return x; }
    public double getY() { return y; }
    public int getAttack() { return attack; }
    public int getLifePoints() { return lifePoints; }

}