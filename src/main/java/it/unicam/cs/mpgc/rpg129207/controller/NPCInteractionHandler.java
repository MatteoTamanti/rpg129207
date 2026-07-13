package it.unicam.cs.mpgc.rpg129207.controller;

import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.NPC;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import it.unicam.cs.mpgc.rpg129207.view.GameView;
import javafx.scene.input.KeyCode;

import java.util.List;

public class NPCInteractionHandler {

    private static final double INTERACTION_RANGE = 50;

    public void update(Player player, List<Entity> entities, InputController inputController, GameView view) {
        if (!inputController.consumeKey(KeyCode.E)) {
            return;
        }

        NPC nearest = findNearestNPC(player, entities);

        if (nearest != null && player.isInRange(nearest, INTERACTION_RANGE)) {
            view.showMessage(nearest.nextMessage());
        }
    }

    private NPC findNearestNPC(Player player, List<Entity> entities) {
        NPC nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Entity e : entities) {
            if (e instanceof NPC npc) {
                double distance = player.distanceTo(npc);

                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearest = npc;
                }
            }
        }

        return nearest;
    }
}