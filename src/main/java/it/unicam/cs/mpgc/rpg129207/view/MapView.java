package it.unicam.cs.mpgc.rpg129207.view;

import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.TileType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapView {

    private final Map map;
    private final Pane root;

    public MapView(Map map) {
        this.map = map;
        this.root = new Pane();

        drawMap();
    }

    private void drawMap() {

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {

                Rectangle tile = new Rectangle(
                        map.getTileSize(),
                        map.getTileSize()
                );

                tile.setX(x * map.getTileSize());
                tile.setY(y * map.getTileSize());

                if (map.getTile(y, x) == TileType.WALL) {
                    tile.setFill(Color.DARKGRAY);
                } else {
                    tile.setFill(Color.LIGHTGRAY);
                }
                root.getChildren().add(tile);
            }
        }
    }


    public Pane getRoot() {
        return root;
    }
}