package it.unicam.cs.mpgc.rpg129207.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthBarView {

    private final Rectangle background;
    private final Rectangle fill;
    private final double width;

    public HealthBarView(double width, double height, Color fillColor) {
        this.width = width;
        this.background = new Rectangle(width, height, Color.DARKGRAY);
        this.fill = new Rectangle(width, height, fillColor);
    }

    public void update(double percentage) {
        double clamped = Math.max(0, Math.min(1, percentage));
        fill.setWidth(width * clamped);
    }

    public void setPosition(double x, double y) {
        background.setX(x);
        background.setY(y);
        fill.setX(x);
        fill.setY(y);
    }

    public Rectangle getBackground() { return background; }
    public Rectangle getFill() { return fill; }
}