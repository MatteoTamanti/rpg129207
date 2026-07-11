package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected double x, y;
    protected double size = 32;

    private int lifePoints;
    private int maxLifePoints;
    private int attack;
    private final long attackCooldownNanos;
    private long lastAttackTime;

    public Entity(double x, double y, int lifePoints, int attack, double attackCooldownSeconds) {
        this.x = x;
        this.y = y;
        this.lifePoints = lifePoints;
        this.attack = attack;
        this.attackCooldownNanos = (long) (attackCooldownSeconds * 1_000_000_000);
        this.lastAttackTime = 0;
        this.maxLifePoints = lifePoints;
    }

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

        for (int y = top; y <= bottom; y++) {
            for (int x = left; x <= right; x++) {
                if (map.getTile(y, x) == TileType.WALL) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isInRange(Entity other, double range) {
        double dx = other.x - x;
        double dy = other.y - y;
        return Math.sqrt(dx * dx + dy * dy) <= range;
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

    public abstract void update(Map map, Player player);

    public double getX() { return x; }
    public double getY() { return y; }
    public int getAttack() { return attack; }
    public int getLifePoints() { return lifePoints; }
}