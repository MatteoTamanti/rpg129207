package it.unicam.cs.mpgc.rpg129207.model;

public class MapGenerator {

    public Map generateMap() {

        Map map = new Map(100, 100);
        fillWithWalls(map);

        createRoom(map, 2, 2, 8, 8);
        createRoom(map, 15, 2, 8, 8);
        createRoom(map, 15, 15, 8, 8);
        createRoom(map, 2, 15, 8, 8);

        createHorizontalCorridor(map, 9, 18, 5);
        createVerticalCorridor(map, 18, 5, 18);
        createHorizontalCorridor(map, 9, 18, 18);

        return map;
    }

    private void fillWithWalls(Map map) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                map.setTile(y, x, TileType.WALL);
            }
        }
    }

    private void createRoom(Map map, int startX, int startY, int width, int height) {
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                map.setTile(y, x, TileType.FLOOR);
            }
        }
    }

    private void createHorizontalCorridor(Map map, int startX, int endX, int y) {
        for (int x = startX; x <= endX; x++) {
            map.setTile(y, x, TileType.FLOOR);
        }
    }

    private void createVerticalCorridor(Map map, int x, int startY, int endY) {
        for (int y = startY; y <= endY; y++) {
            map.setTile(y, x, TileType.FLOOR);
        }
    }

}