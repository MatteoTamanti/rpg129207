package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;


public abstract class Entity implements Serializable {
    protected double x, y;

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public void move(double dx, double dy, Map map) {

        double newX = x + dx;
        double newY = y + dy;

        if (canMove(newX, newY, map)) {
            x = newX;
            y = newY;
        }
    }


    private boolean canMove(double newX, double newY, Map map) {

        int tileSize = map.getTileSize();

        int left = (int) (newX / tileSize);
        int right = (int) ((newX + tileSize - 1) / tileSize);
        int top = (int) (newY / tileSize);
        int bottom = (int) ((newY + tileSize - 1) / tileSize);


        if (left < 0 || right >= map.getWidth()
                || top < 0 || bottom >= map.getHeight()) {
            return false;
        }

        return map.getTile(top, left) == TileType.FLOOR
                && map.getTile(top, right) == TileType.FLOOR
                && map.getTile(bottom, left) == TileType.FLOOR
                && map.getTile(bottom, right) == TileType.FLOOR;
    }

    public abstract void update(Map map, Player player);

    public double getX() { return x; }
    public double getY() { return y; }
}
