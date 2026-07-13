package it.unicam.cs.mpgc.rpg129207.model;

import java.io.Serializable;
import java.util.List;
import it.unicam.cs.mpgc.rpg129207.model.Map;

public class NPC extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int NPC_LIFE_POINTS = 1;
    private static final int NPC_ATTACK = 0;
    private static final double NPC_ATTACK_COOLDOWN = 0;

    private final List<String> dialogueLines;
    private int currentLine;

    public NPC(double x, double y, List<String> dialogueLines) {
        this(x, y, dialogueLines, 0);
    }

    public NPC(double x, double y, List<String> dialogueLines, int currentLine) {
        super(x, y, NPC_LIFE_POINTS, NPC_ATTACK, NPC_ATTACK_COOLDOWN);
        this.dialogueLines = dialogueLines;
        this.currentLine = currentLine;
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

    public List<String> getDialogueLines() {
        return dialogueLines;
    }

    public int getCurrentLine() {
        return currentLine;
    }
}