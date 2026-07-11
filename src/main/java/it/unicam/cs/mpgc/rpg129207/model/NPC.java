package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;

public class NPC extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    public NPC(double x, double y) {
        super(x, y);
    }

    @Override
    public void update(Map map, Player player) {
    }
}
