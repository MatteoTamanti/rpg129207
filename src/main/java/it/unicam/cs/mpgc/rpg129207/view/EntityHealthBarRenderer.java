package it.unicam.cs.mpgc.rpg129207.view;

import it.unicam.cs.mpgc.rpg129207.model.Entity;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class EntityHealthBarRenderer {

    private static final double WIDTH = 32;
    private static final double HEIGHT = 4;
    private static final double VERTICAL_OFFSET = 8;

    private final Pane layer;
    private final Predicate<Entity> shouldTrack;
    private final Map<Entity, HealthBarView> bars;

    public EntityHealthBarRenderer(Pane layer, Predicate<Entity> shouldTrack) {
        this.layer = layer;
        this.shouldTrack = shouldTrack;
        this.bars = new HashMap<>();
    }

    public void update(List<Entity> entities) {
        for (Entity entity : entities) {
            if (shouldTrack.test(entity)) {
                updateBarFor(entity);
            }
        }
    }

    private void updateBarFor(Entity entity) {
        HealthBarView bar = bars.computeIfAbsent(entity, e -> createBar());
        bar.setPosition(entity.getX(), entity.getY() - VERTICAL_OFFSET);
        bar.update(entity.getLifePoints() / (double) entity.getMaxLifePoints());
    }

    private HealthBarView createBar() {
        HealthBarView bar = new HealthBarView(WIDTH, HEIGHT, Color.RED);
        layer.getChildren().addAll(bar.getBackground(), bar.getFill());
        return bar;
    }

    public void remove(Entity entity) {
        HealthBarView bar = bars.remove(entity);
        if (bar != null) {
            layer.getChildren().removeAll(bar.getBackground(), bar.getFill());
        }
    }
}