package it.unicam.cs.mpgc.rpg129207.model;

/*
Il giocatore deve avere dei life points e un attacco e può interagire con npc, nemici eccetera.
 Il giocatore soprattutto deve avere una posizione nella mappa.
*/

public class Player {
    private int lifePoints;
    private int attack;
    private double x, y; //uso double per rendere il movimento fluido

    public Player(int lifePoints, int attack) { //prendo i due attributi, perché intendo aggiungere più tipi di Player che l'user può scegliere
        this.lifePoints = lifePoints;
        this.attack = attack;
    }

    public void playerSpawn() {
       this.x = 5;
       this.y = 5;
   }

}
