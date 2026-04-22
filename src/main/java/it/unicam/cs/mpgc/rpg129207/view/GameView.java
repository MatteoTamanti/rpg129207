package it.unicam.cs.mpgc.rpg129207.view;

import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameView {
    private Pane root;
    private List<Entity> entities;
    private Map<Entity, Rectangle> entityShapes;

    public GameView(List<Entity> entities) {
        this.entities = entities;
        this.root = new Pane();
        this.entityShapes = new HashMap<>();

        for (Entity e : entities) {
            Rectangle shape = new Rectangle(32, 32);
            shape.setFill(Color.BLUEVIOLET);
            entityShapes.put(e, shape);
            root.getChildren().add(shape);
        }
    }

    public void render() {
        for (Entity e : entities) {
            Rectangle shape = entityShapes.get(e);
            shape.setX(e.getX());
            shape.setY(e.getY());
        }
    }

    public Pane getRoot() {
        return root;
    }
}
