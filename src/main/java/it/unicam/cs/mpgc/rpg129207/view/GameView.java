package it.unicam.cs.mpgc.rpg129207.view;

import it.unicam.cs.mpgc.rpg129207.model.Player;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameView {
    private Pane root;
    private Rectangle playerShape; //per adesso
    private Player player;

    public GameView(Player player) {
        this.player = player;
        this.root = new Pane();
        this.playerShape = new Rectangle(32, 32, Color.BLUEVIOLET);
        this.root.getChildren().add(playerShape);
    }
    public void render() {
        playerShape.setX(player.getPlayerX());
        playerShape.setY(player.getPlayerY());
    }

    public Pane getRoot() {
        return root;
    }

}
