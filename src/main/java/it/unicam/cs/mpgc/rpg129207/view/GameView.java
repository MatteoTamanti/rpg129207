package it.unicam.cs.mpgc.rpg129207.view;

import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.NPC;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class GameView {

    private final Pane root;
    private final Pane world;
    private final Camera camera;
    private final Player player;
    private final Pane entityLayer;
    private final List<Entity> entities;
    private final java.util.Map<Entity, ImageView> entityShapes;
    private final Function<Entity, Image> spriteSelector;
    private final MapView mapView;
    private final int tileSize;
    private final EntityHealthBarRenderer healthBarRenderer;
    private final PlayerHudView playerHud;
    private final EndGameOverlay endGameOverlay;

    public GameView(Map map, List<Entity> entities, Player player,
                    double viewportWidth, double viewportHeight,
                    Function<Entity, Image> spriteSelector,
                    Image floorImage, Image wallImage) {

        this.entities = entities;
        this.player = player;
        this.tileSize = map.getTileSize();
        this.spriteSelector = spriteSelector;
        this.root = new Pane();
        this.world = new Pane();
        this.entityLayer = new Pane();
        this.entityShapes = new HashMap<>();
        this.mapView = new MapView(map, floorImage, wallImage);

        this.camera = new Camera(
                viewportWidth, viewportHeight,
                map.getWidth() * map.getTileSize(),
                map.getHeight() * map.getTileSize()
        );

        world.getChildren().add(mapView.getRoot());
        world.getChildren().add(entityLayer);
        root.getChildren().add(world);

        root.setPrefSize(viewportWidth, viewportHeight);
        root.setClip(new Rectangle(viewportWidth, viewportHeight));
        root.setStyle("-fx-background-color: black;");

        for (Entity entity : entities) {
            entityShapes.put(entity, createViewFor(entity));
        }

        this.healthBarRenderer = new EntityHealthBarRenderer(entityLayer, e -> e != player && !(e instanceof NPC));
        this.playerHud = new PlayerHudView(root);
        this.endGameOverlay = new EndGameOverlay(root, viewportWidth, viewportHeight);
    }

    public void render() {
        for (Entity entity : entities) {
            ImageView view = entityShapes.computeIfAbsent(entity, this::createViewFor);

            double displaySize = spriteSizeFor(entity);
            double offset = (displaySize - tileSize) / 2;

            view.setX(entity.getX() - offset);
            view.setY(entity.getY() - offset);
        }

        healthBarRenderer.update(entities);
        playerHud.update(player);

        camera.update(player.getX(), player.getY());
        world.setTranslateX(-Math.round(camera.getX()));
        world.setTranslateY(-Math.round(camera.getY()));
    }

    private ImageView createViewFor(Entity entity) {
        double displaySize = spriteSizeFor(entity);

        ImageView view = new ImageView(spriteSelector.apply(entity));
        view.setFitWidth(displaySize);
        view.setFitHeight(displaySize);
        entityLayer.getChildren().add(view);
        return view;
    }

    private double spriteSizeFor(Entity entity) {
        return entity instanceof Player ? tileSize * 1.75 : tileSize;
    }

    public boolean removeEntity(Entity entity) {
        ImageView view = entityShapes.remove(entity);
        if (view != null) {
            entityLayer.getChildren().remove(view);
        }

        healthBarRenderer.remove(entity);
        return true;
    }

    public void showGameOver() {
        endGameOverlay.show("GAME OVER", Color.RED);
    }

    public void showVictory() {
        endGameOverlay.show("VICTORY", Color.GOLD);
    }

    public void showMessage(String text) {
        Label messageLabel = new Label(text);
        messageLabel.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-padding: 10; -fx-font-size: 16;");
        messageLabel.setLayoutX(20);
        messageLabel.setLayoutY(root.getHeight() - 60);
        root.getChildren().add(messageLabel);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> root.getChildren().remove(messageLabel));
        pause.play();
    }

    public Pane getRoot() {
        return root;
    }
}