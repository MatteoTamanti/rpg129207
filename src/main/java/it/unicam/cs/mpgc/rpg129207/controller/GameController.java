package it.unicam.cs.mpgc.rpg129207.controller;

import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import java.util.HashSet;
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






}
