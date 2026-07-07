package it.unicam.cs.mpgc.rpg129207.controller;

import javafx.animation.AnimationTimer;

public class GameLoop {
    private final AnimationTimer animationTimer;

    public GameLoop(Runnable updateAction) {

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateAction.run();
            }
        };
    }

    public void start() {
        animationTimer.start();
    }

    public void stop() {
        animationTimer.stop();
    }
}

