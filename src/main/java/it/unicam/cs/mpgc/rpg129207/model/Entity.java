package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;


public abstract class Entity implements Serializable {
    protected double x, y;

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected double size = 32;

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




    public abstract void update(Map map, Player player);

    public double getX() { return x; }
    public double getY() { return y; }
}
