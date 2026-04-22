package it.unicam.cs.mpgc.rpg129207.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.GameState;
import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import it.unicam.cs.mpgc.rpg129207.view.GameView;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameController {
    private Map map;
    private Player player;
    private List<Entity> entities;
    private HashSet<KeyCode> pressedKeys; //uso un set perchè non voglio duplicati
    private AnimationTimer gameLoop;
    private GameView view;


    public GameController(Map map, Player player,  GameView view, List<Entity> entities) {
        this.map = map;
        this.player = player;
        this.entities = new ArrayList<>();
        this.entities.add(player);
        this.pressedKeys = new HashSet<>();
        this.view = view;
        createGameLoop();
    }

    private void createGameLoop() { //per il momento il refresh rate è lo stesso dello schermo dell'user, il che non ha troppo senso, quindi va corretto
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
    }

    private void updateGame() {
        double speed = 2.0;

        if (pressedKeys.contains(KeyCode.W)) player.move(0, -speed, map);
        if (pressedKeys.contains(KeyCode.S)) player.move(0, speed, map);
        if (pressedKeys.contains(KeyCode.A)) player.move(-speed, 0, map);
        if (pressedKeys.contains(KeyCode.D)) player.move(speed, 0, map);

        for (Entity e : entities) {
            e.update(map);
        }

        view.render();
    }

    /*
    preparo il GameController a ricevere input da Scene e aggiungerli al set
    e aggiungo il tasto "P" per salvare
     */
    public void connectKeyboard(Scene scene) {
        scene.setOnKeyPressed(event -> { pressedKeys.add(event.getCode());
            if (event.getCode() == KeyCode.P) {saveState();}
        });
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
    }


    public void saveState() {
        GameState currentState = new GameState(this.player, this.map);

        File file = new File("savedState.json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(currentState, writer);
            System.out.println("Salvataggio effettuato!");
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio!" + e.getMessage());
        }
    }

    public static GameState loadState() {
        File file = new File("savedState.json");
        if (!file.exists()) {
            System.err.println("Errore nel caricamento!");
            return null;
        }
        try (java.io.FileReader reader = new java.io.FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, GameState.class);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento!" + e.getMessage());
            return null;
        }
    }

    public void startLoop() {
        if (gameLoop != null) {
            gameLoop.start();
        }
    }
}
