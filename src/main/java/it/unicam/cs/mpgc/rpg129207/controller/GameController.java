package it.unicam.cs.mpgc.rpg129207.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg129207.model.GameState;
import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

//uso un set perchè non voglio duplicati

public class GameController {
    private Map map;
    private Player player;
    private HashSet<KeyCode> pressedKeys;

    public GameController(Map map, Player player) {
        this.map = map;
        this.player = player;
        this.pressedKeys = new HashSet<>();
    }

    //preparo il GameController a ricevere input da Scene e aggiungerli al set
    public void connectKeyboard(Scene scene) {
        scene.setOnKeyPressed(event -> {pressedKeys.add(event.getCode());});
        scene.setOnKeyReleased(event -> {pressedKeys.remove(event.getCode());});
    }

    public void SaveState() {
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






}
