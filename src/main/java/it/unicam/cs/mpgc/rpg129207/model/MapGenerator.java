package it.unicam.cs.mpgc.rpg129207.model;

public class MapGenerator {

    public static final int ROOM_SIZE = 20;
    public static final int ROOM1_X = 5;
    public static final int ROOM1_Y = 5;
    public static final int ROOM2_X = 40;
    public static final int ROOM2_Y = 5;
    public static final int ROOM3_X = 40;
    public static final int ROOM3_Y = 40;
    public static final int ROOM4_X = 5;
    public static final int ROOM4_Y = 40;

    public Map generateMap() {

        Map map = new Map(100, 100);
        fillWithWalls(map);

        createRoom(map, ROOM1_X, ROOM1_Y, ROOM_SIZE, ROOM_SIZE);
        createRoom(map, ROOM2_X, ROOM2_Y, ROOM_SIZE, ROOM_SIZE);
        createRoom(map, ROOM3_X, ROOM3_Y, ROOM_SIZE, ROOM_SIZE);
        createRoom(map, ROOM4_X, ROOM4_Y, ROOM_SIZE, ROOM_SIZE);

        createHorizontalCorridor(map, 24, 40, 13);
        createVerticalCorridor(map, 48, 24, 40);
        createHorizontalCorridor(map, 24, 40, 48);

        return map;
    }

    public double[] findCenterSpawn(Map map) {
        int centerX = map.getWidth() / 2;
        int centerY = map.getHeight() / 2;

        for (int radius = 0; radius < map.getWidth(); radius++) {
            for (int y = centerY - radius; y <= centerY + radius; y++) {
                for (int x = centerX - radius; x <= centerX + radius; x++) {
                    if (isInsideMap(map, x, y) && map.getTile(y, x) == TileType.FLOOR) {
                        return new double[] { x * map.getTileSize(), y * map.getTileSize() };
                    }
                }
            }
        } throw new IllegalStateException("No floor tile found near map center");
    }

    private boolean isInsideMap(Map map, int x, int y) {
        return x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight();
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
        for (int currentY = y; currentY < y + 3; currentY++) {
            for (int x = startX; x <= endX; x++) {
                map.setTile(currentY, x, TileType.FLOOR);
            }
        }
    }

    private void createVerticalCorridor(Map map, int x, int startY, int endY) {
        for (int currentX = x; currentX < x + 3; currentX++) {
            for (int y = startY; y <= endY; y++) {
                map.setTile(y, currentX, TileType.FLOOR);
            }
        }
    }
}