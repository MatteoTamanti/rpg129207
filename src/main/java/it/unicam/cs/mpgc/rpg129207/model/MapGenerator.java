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

    private static final int MAP_WIDTH = 100;
    private static final int MAP_HEIGHT = 100;
    private static final int CORRIDOR_THICKNESS = 3;

    private static final int CORRIDOR_RANGE_START = 24;
    private static final int CORRIDOR_RANGE_END = 40;
    private static final int TOP_CORRIDOR_Y = 13;
    private static final int MIDDLE_CORRIDOR_X = 48;
    private static final int BOTTOM_CORRIDOR_Y = 48;

    public Map generateMap() {

        Map map = new Map(MAP_WIDTH, MAP_HEIGHT);
        fillWithWalls(map);

        createRoom(map, ROOM1_X, ROOM1_Y, ROOM_SIZE, ROOM_SIZE);
        createRoom(map, ROOM2_X, ROOM2_Y, ROOM_SIZE, ROOM_SIZE);
        createRoom(map, ROOM3_X, ROOM3_Y, ROOM_SIZE, ROOM_SIZE);
        createRoom(map, ROOM4_X, ROOM4_Y, ROOM_SIZE, ROOM_SIZE);

        createHorizontalCorridor(map, CORRIDOR_RANGE_START, CORRIDOR_RANGE_END, TOP_CORRIDOR_Y);
        createVerticalCorridor(map, MIDDLE_CORRIDOR_X, CORRIDOR_RANGE_START, CORRIDOR_RANGE_END);
        createHorizontalCorridor(map, CORRIDOR_RANGE_START, CORRIDOR_RANGE_END, BOTTOM_CORRIDOR_Y);

        return map;
    }

    public double[] findRoom1Spawn(Map map) {
        int centerX = ROOM1_X + ROOM_SIZE / 2;
        int centerY = ROOM1_Y + ROOM_SIZE / 2;
        return new double[] { centerX * map.getTileSize(), centerY * map.getTileSize() };
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
        for (int currentY = y; currentY < y + CORRIDOR_THICKNESS; currentY++) {
            for (int x = startX; x <= endX; x++) {
                map.setTile(currentY, x, TileType.FLOOR);
            }
        }
    }

    private void createVerticalCorridor(Map map, int x, int startY, int endY) {
        for (int currentX = x; currentX < x + CORRIDOR_THICKNESS; currentX++) {
            for (int y = startY; y <= endY; y++) {
                map.setTile(y, currentX, TileType.FLOOR);
            }
        }
    }
}