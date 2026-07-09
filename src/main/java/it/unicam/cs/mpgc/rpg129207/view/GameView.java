package it.unicam.cs.mpgc.rpg129207.view;

import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.Map;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;

public class GameView {

    private final Pane root;
    private final Pane entityLayer;
    private final List<Entity> entities;
    private final java.util.Map<Entity, Rectangle> entityShapes;
    private final MapView mapView;

    public GameView(Map map, List<Entity> entities) {

        this.entities = entities;
        this.root = new Pane();
        this.entityLayer = new Pane();
        this.entityShapes = new HashMap<>();
        this.mapView = new MapView(map);

        root.getChildren().add(mapView.getRoot());
        root.getChildren().add(entityLayer);

        for (Entity entity : entities) {
            Rectangle shape = new Rectangle(
                    map.getTileSize(),
                    map.getTileSize()
            );

            shape.setFill(Color.BLUEVIOLET);
            entityShapes.put(entity, shape);
            entityLayer.getChildren().add(shape);
        }
    }

    public void render() {

        for (Entity entity : entities) {
            Rectangle shape = entityShapes.get(entity);
            shape.setX(entity.getX());
            shape.setY(entity.getY());
        }
    }

    public Pane getRoot() {
        return root;
    }
}