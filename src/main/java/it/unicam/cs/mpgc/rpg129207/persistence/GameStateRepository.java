package it.unicam.cs.mpgc.rpg129207.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;


public class GameStateRepository {

    public void save(GameState gameState) {

        File file = new File("savedState.json");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(gameState, writer);
            System.out.println("Game saved");

        } catch (IOException e) {
            System.err.println("Error saving game " + e.getMessage());
        }
    }

    public GameState load() {

        File file = new File("savedState.json");

        if (!file.exists()) { return null;}

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, GameState.class);

        } catch (IOException e) {
            System.err.println("Error loading game " + e.getMessage());
            return null;

        }
    }
}

















