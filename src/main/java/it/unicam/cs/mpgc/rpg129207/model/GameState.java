package it.unicam.cs.mpgc.rpg129207.model;

import javafx.scene.input.KeyCode;
import java.io.Serializable;
import java.util.List;


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
