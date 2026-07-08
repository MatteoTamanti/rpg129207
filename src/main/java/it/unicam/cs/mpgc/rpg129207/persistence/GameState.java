package it.unicam.cs.mpgc.rpg129207.persistence;

import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.Player;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private Player player;
    private Map map;

    public GameState(Player player, Map map) {
        this.player = player;
        this.map = map;
    }

    public Player getPlayer() {
        return player;
    }
    public Map getMap() {
        return map;
    }

}