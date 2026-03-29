package it.unicam.cs.mpgc.rpg129207.model;

/*
Il giocatore deve avere dei life points e un attacco e può interagire con npc, nemici eccetera.
 Il giocatore soprattutto deve avere una posizione nella mappa.
*/

public class Player {
    private int lifePoints;
    private int attack;
    private double playerX, playerY; //uso double per rendere il movimento fluido

    public Player(int lifePoints, int attack, double playerX, double playerY) { //prendo i due attributi, perché intendo aggiungere più tipi di Player che l'user può scegliere
        this.lifePoints = lifePoints;
        this.attack = attack;
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public void move(double dx, double dy, Map map) {
        double limiteX = map.getMapX() * map.getPixelPerCell();
        double limiteY = map.getMapY() * map.getPixelPerCell();

        double provvisorioX = playerX + dx;
        double provvisorioY = playerY + dy;

        //controllo bordo destro e sinistro
        if (provvisorioX > limiteX) {
            playerX = limiteX;
        } else if (provvisorioX < 0) {
                playerX = 0;
            } else {
                playerX = provvisorioX;
            }


                //controllo bordo superiore e inferiore
                if (provvisorioY > limiteY) {
                    playerY = limiteY;
                } else if (provvisorioY < 0) {
                    playerY = 0;
                } else {
                    playerY = provvisorioY;
                }


            }

            public double getPlayerX () {
                return playerX;
            }

            public double getPlayerY () {
                return playerY;
            }

        }

