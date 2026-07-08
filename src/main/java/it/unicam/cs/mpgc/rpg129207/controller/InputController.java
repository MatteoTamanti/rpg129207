package it.unicam.cs.mpgc.rpg129207.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;

public class InputController {
    private HashSet<KeyCode> pressedKeys;

    public InputController() {
        this.pressedKeys = new HashSet<>();
    }

    public boolean consumeKey(KeyCode key) {
        if (pressedKeys.contains(key)) {
            pressedKeys.remove(key);
            return true;
        } return false;
    }

    public void connectKeyboard(Scene scene) {
        scene.setOnKeyPressed(event ->  pressedKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
    }

    public boolean isKeyPressed(KeyCode key) {
        return pressedKeys.contains(key);
    }

}
