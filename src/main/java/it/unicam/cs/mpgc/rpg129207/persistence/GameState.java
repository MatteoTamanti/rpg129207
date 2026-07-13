package it.unicam.cs.mpgc.rpg129207.persistence;

import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map map;
    private final List<Entity> entities;

    public GameState(Map map, List<Entity> entities) {
        this.map = map;
        this.entities = entities;
    }

    public Map getMap() {
        return map;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Player getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player player) {
                return player;
            }
        }
        throw new IllegalStateException("No player found in saved game");
    }
}