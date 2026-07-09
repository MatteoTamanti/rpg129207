package it.unicam.cs.mpgc.rpg129207;

import it.unicam.cs.mpgc.rpg129207.controller.GameController;
import it.unicam.cs.mpgc.rpg129207.controller.InputController;
import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.MapGenerator;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import it.unicam.cs.mpgc.rpg129207.persistence.GameState;
import it.unicam.cs.mpgc.rpg129207.persistence.GameStateRepository;
import it.unicam.cs.mpgc.rpg129207.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

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
            player = new Player(100, 10, 70, 70);
            entities = new ArrayList<>();
            entities.add(player);
            System.out.println("No save data found. New game.");
        }

        GameView view = new GameView(map, entities);

        InputController inputController = new InputController();

        GameController controller = new GameController(map, player, view, entities, inputController, gameStateRepository);

        Scene scene = new Scene(view.getRoot(), 800, 600);

        inputController.connectKeyboard(scene);

        primaryStage.setTitle("RPG Project");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.startLoop();
    }


    public static void main(String[] args) {
        launch(args);
    }
}