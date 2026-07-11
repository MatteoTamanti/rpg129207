package it.unicam.cs.mpgc.rpg129207.view;

import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.TileType;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MapView {

    private final Canvas canvas;

    public MapView(Map map) {
        this.canvas = new Canvas(map.getWidth() * map.getTileSize(), map.getHeight() * map.getTileSize());

        drawMap(map);
    }

    private void drawMap(Map map) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {

                gc.setFill(map.getTile(y, x) == TileType.WALL
                        ? Color.DARKGRAY
                        : Color.LIGHTGRAY);

                gc.fillRect(x * map.getTileSize(), y * map.getTileSize(), map.getTileSize(), map.getTileSize());
            }
        }
    }

    public Node getRoot() {
        return canvas;
    }
}