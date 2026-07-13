package it.unicam.cs.mpgc.rpg129207;

import it.unicam.cs.mpgc.rpg129207.controller.*;
import it.unicam.cs.mpgc.rpg129207.model.*;
import it.unicam.cs.mpgc.rpg129207.persistence.GameState;
import it.unicam.cs.mpgc.rpg129207.persistence.GameStateRepository;
import it.unicam.cs.mpgc.rpg129207.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Main extends Application {

    private static final double VIEWPORT_WIDTH = 800;
    private static final double VIEWPORT_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        Map map;
        Player player;
        List<Entity> entities;

        GameStateRepository gameStateRepository = new GameStateRepository();
        GameState savedData = gameStateRepository.load();

        if (savedData != null) {
            map = savedData.getMap();
            entities = savedData.getEntities();
            player = savedData.getPlayer();
            System.out.println("Game loaded");

        } else {
            MapGenerator generator = new MapGenerator();
            map = generator.generateMap();

            double[] spawn = generator.findRoom1Spawn(map);

            player = new Player(spawn[0], spawn[1]);

            entities = new ArrayList<>();
            entities.add(player);

            NPC npc = new NPC(spawn[0] + 60, spawn[1], List.of(
                    "Greetings noble knight!",
                    "Our town is plagued by ghouls! Would you be so kind to help us?",
                    "We would be eternally grateful for your assistance!"
            ));
            entities.add(npc);

            System.out.println("No save data found. New game.");
        }

        List<EnemySpawner> enemySpawners = List.of(
                new EnemySpawner(MapGenerator.ROOM2_X * map.getTileSize(), MapGenerator.ROOM2_Y * map.getTileSize(),
                        MapGenerator.ROOM_SIZE * map.getTileSize(), MapGenerator.ROOM_SIZE * map.getTileSize(), 1),

                new EnemySpawner(MapGenerator.ROOM3_X * map.getTileSize(), MapGenerator.ROOM3_Y * map.getTileSize(),
                        MapGenerator.ROOM_SIZE * map.getTileSize(), MapGenerator.ROOM_SIZE * map.getTileSize(), 1),

                new EnemySpawner(MapGenerator.ROOM4_X * map.getTileSize(), MapGenerator.ROOM4_Y * map.getTileSize(),
                        MapGenerator.ROOM_SIZE * map.getTileSize(), MapGenerator.ROOM_SIZE * map.getTileSize(), 1)
        );

        PlayerCombatHandler combatHandler = new PlayerCombatHandler();

        NPCInteractionHandler npcInteractionHandler = new NPCInteractionHandler();

        Image playerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/player.png")));
        Image enemyImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/enemy.png")));
        Image floorImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/floor.png")));
        Image wallImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/wall.png")));
        Image npcImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/npc.png")));

        Function<Entity, Image> spriteSelector = entity -> {
            if (entity instanceof Enemy) {
                return enemyImage;
            }
            if (entity instanceof NPC) {
                return npcImage;
            }
            return playerImage;
        };

        GameView view = new GameView(
                map, entities, player, VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
                spriteSelector, floorImage, wallImage
        );

        InputController inputController = new InputController();

        GameController controller = new GameController(map, player, view, entities, inputController, gameStateRepository, enemySpawners, combatHandler, npcInteractionHandler);

        Scene scene = new Scene(view.getRoot(), VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        inputController.connectKeyboard(scene);

        primaryStage.setTitle("BERK");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.startLoop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}