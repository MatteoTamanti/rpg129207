package it.unicam.cs.mpgc.rpg129207.view;

import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.TileType;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MapView {

    private final Canvas canvas;

    public MapView(Map map, Image floorImage, Image wallImage) {
        this.canvas = new Canvas(
                map.getWidth() * map.getTileSize(),
                map.getHeight() * map.getTileSize()
        );
        drawMap(map, floorImage, wallImage);
    }

    private void drawMap(Map map, Image floorImage, Image wallImage) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int tileSize = map.getTileSize();

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Image tileImage = map.getTile(y, x) == TileType.WALL ? wallImage : floorImage;
                gc.drawImage(tileImage, x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }

    public Node getRoot() {
        return canvas;
    }
}