package it.unicam.cs.mpgc.rpg129207.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EndGameOverlay {

    private final StackPane overlay;
    private final Label label;

    public EndGameOverlay(Pane root, double width, double height) {
        this.label = new Label();
        this.label.setFont(Font.font("Arial", FontWeight.BOLD, 64));

        this.overlay = new StackPane(label);
        this.overlay.setAlignment(Pos.CENTER);
        this.overlay.setPrefSize(width, height);
        this.overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        this.overlay.setVisible(false);

        root.getChildren().add(overlay);
    }

    public void show(String text, Color color) {
        label.setText(text);
        label.setTextFill(color);
        overlay.setVisible(true);
    }
}