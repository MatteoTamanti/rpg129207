package it.unicam.cs.mpgc.rpg129207.view;

public class Camera {

    private double x;
    private double y;

    private final double viewportWidth;
    private final double viewportHeight;
    private final double worldWidth;
    private final double worldHeight;

    public Camera(double viewportWidth, double viewportHeight, double worldWidth, double worldHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void update(double targetX, double targetY) {
        x = clamp(targetX - viewportWidth / 2, 0, worldWidth - viewportWidth);
        y = clamp(targetY - viewportHeight / 2, 0, worldHeight - viewportHeight);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}