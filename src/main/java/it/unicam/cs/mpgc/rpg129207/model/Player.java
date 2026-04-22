package it.unicam.cs.mpgc.rpg129207.model;

/*
Il giocatore deve avere dei life points e un attacco e può interagire con npc, nemici eccetera.
 Il giocatore soprattutto deve avere una posizione nella mappa.
*/

import java.io.Serializable;

public class Player extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int lifePoints;
    private int attack;

    public Player(int lifePoints, int attack, double x, double y) {
        super(x, y);
        this.lifePoints = lifePoints;
        this.attack = attack;
    }

    @Override
    public void update(Map map) { //Per ora non fa niente

    }

    public void move(double dx, double dy, Map map) {
        double limiteX = map.getMapX() * map.getPixelPerCell();
        double limiteY = map.getMapY() * map.getPixelPerCell();

        double provvisorioX = x + dx;
        double provvisorioY = y + dy;

        //controllo bordo destro e sinistro
        if (provvisorioX > limiteX) {
            x = limiteX;
        } else if (provvisorioX < 0) {
            x = 0;
        } else {
            x = provvisorioX;
        }

        //controllo bordo superiore e inferiore
        if (provvisorioY > limiteY) {
            y = limiteY;
        } else if (provvisorioY < 0) {
            y = 0;
        } else {
            y = provvisorioY;
        }
    }

    public double getPlayerX() {
        return x;
    }

    public double getPlayerY() {
        return y;
    }

}

