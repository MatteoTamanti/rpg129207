package it.unicam.cs.mpgc.rpg129207.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.GameState;
import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import it.unicam.cs.mpgc.rpg129207.view.GameView;
import java.io.*;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

public class GameController {
    private Map map;
    private Player player;
    private List<Entity> entities;
    private AnimationTimer gameLoop;
    private GameView view;
    private InputController inputController;


    public GameController(Map map, Player player,  GameView view, List<Entity> entities, InputController inputController) {
        this.map = map;
        this.player = player;
        this.entities = entities;
        this.entities.add(player);
        this.view = view;
        this.inputController  = inputController;

        createGameLoop();
    }

    private void createGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
    }

    private void updateGame() {
        double speed = 2.0;

        if (inputController.isKeyPressed(KeyCode.W)) player.move(0, -speed, map);
        if (inputController.isKeyPressed(KeyCode.S)) player.move(0, speed, map);
        if (inputController.isKeyPressed(KeyCode.A)) player.move(-speed, 0, map);
        if (inputController.isKeyPressed(KeyCode.D)) player.move(speed, 0, map);

        for (Entity e : entities) {
            e.update(map, player);
        }

        view.render();
    }


    public void saveState() {
        GameState currentState = new GameState(this.player, this.map);

        File file = new File("savedState.json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(currentState, writer);
            System.out.println("Game saved");
        } catch (IOException e) {
            System.err.println("Error loading game" + e.getMessage());
        }
    }

    public static GameState loadState() {
        File file = new File("savedState.json");
        if (!file.exists()) {
            System.err.println("Error loading game");
            return null;
        }
        try (java.io.FileReader reader = new java.io.FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, GameState.class);
        } catch (IOException e) {
            System.err.println("Error loading game" + e.getMessage());
            return null;
        }
    }

    public void startLoop() {
        if (gameLoop != null) {
            gameLoop.start();
        }
    }
}
