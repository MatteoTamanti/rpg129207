package it.unicam.cs.mpgc.rpg129207.view;

import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;

public class GameView {

    private final Pane root;
    private final Pane world;
    private final Camera camera;
    private final Player player;
    private final Pane entityLayer;
    private final List<Entity> entities;
    private final java.util.Map<Entity, Rectangle> entityShapes;
    private final MapView mapView;
    private final int tileSize;

    public GameView(Map map, List<Entity> entities, Player player, double viewportWidth, double viewportHeight) {

        this.entities = entities;
        this.player = player;
        this.root = new Pane();
        this.world = new Pane();
        this.entityLayer = new Pane();
        this.entityShapes = new HashMap<>();
        this.mapView = new MapView(map);
        this.tileSize = map.getTileSize();


        this.camera = new Camera(viewportWidth, viewportHeight, map.getWidth() * map.getTileSize(), map.getHeight() * map.getTileSize());

        world.getChildren().add(mapView.getRoot());
        world.getChildren().add(entityLayer);
        root.getChildren().add(world);

        root.setPrefSize(viewportWidth, viewportHeight);
        root.setClip(new Rectangle(viewportWidth, viewportHeight));

        for (Entity entity : entities) {
            Rectangle shape = new Rectangle(map.getTileSize(), map.getTileSize());

            shape.setFill(Color.BLUEVIOLET);
            entityShapes.put(entity, shape);
            entityLayer.getChildren().add(shape);
        }
    }

    public void render() {
        for (Entity entity : entities) {
            Rectangle shape = entityShapes.computeIfAbsent(entity, e -> createShapeFor());
            shape.setX(entity.getX());
            shape.setY(entity.getY());
        }
        camera.update(player.getX(), player.getY());
        world.setTranslateX(-Math.round(camera.getX()));
        world.setTranslateY(-Math.round(camera.getY()));
    }

    private Rectangle createShapeFor() {
        Rectangle shape = new Rectangle(tileSize, tileSize);
        shape.setFill(Color.BLUEVIOLET);
        entityLayer.getChildren().add(shape);
        return shape;
    }

    public Pane getRoot() {
        return root;
    }
}