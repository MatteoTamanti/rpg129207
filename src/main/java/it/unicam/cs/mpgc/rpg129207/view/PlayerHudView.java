package it.unicam.cs.mpgc.rpg129207.view;

import it.unicam.cs.mpgc.rpg129207.model.Player;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PlayerHudView {

    private static final double WIDTH = 200;
    private static final double HEIGHT = 20;
    private static final double MARGIN = 20;

    private final HealthBarView healthBar;

    public PlayerHudView(Pane hudLayer) {
        this.healthBar = new HealthBarView(WIDTH, HEIGHT, Color.LIMEGREEN);
        healthBar.setPosition(MARGIN, MARGIN);
        hudLayer.getChildren().addAll(healthBar.getBackground(), healthBar.getFill());
    }

    public void update(Player player) {
        healthBar.update(player.getLifePoints() / (double) player.getMaxLifePoints());
    }
}