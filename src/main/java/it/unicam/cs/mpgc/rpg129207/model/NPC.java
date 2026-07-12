package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;
import java.util.List;

public class NPC extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<String> dialogueLines;
    private int currentLine;

    public NPC(double x, double y, List<String> dialogueLines) {
        super(x, y, 1, 0, 0);
        this.dialogueLines = dialogueLines;
        this.currentLine = 0;
    }

    @Override
    public void update(Map map, Player player) {
    }

    public String nextMessage() {
        String message = dialogueLines.get(currentLine);

        if (currentLine < dialogueLines.size() - 1) {
            currentLine++;
        }

        return message;
    }
}