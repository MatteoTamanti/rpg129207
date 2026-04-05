package it.unicam.cs.mpgc.rpg129207;

import it.unicam.cs.mpgc.rpg129207.controller.GameController;
import it.unicam.cs.mpgc.rpg129207.model.GameState;
import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import it.unicam.cs.mpgc.rpg129207.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Map map;
        Player player;
        GameState savedData = GameController.loadState();

        if (savedData != null) {
            map = savedData.getMap();
            player = savedData.getPlayer();
            System.out.println("Salvataggio caricato! Bentornato.");
        } else {
            map = new Map(20, 20);
            player = new Player(100, 10, 100, 100);
            System.out.println("Nessun salvataggio trovato. Nuova partita.");
        }
        GameView view = new GameView(player);
        GameController controller = new GameController(map, player, view);
        Scene scene = new Scene(view.getRoot(), 800, 600);
        controller.connectKeyboard(scene);

        primaryStage.setTitle("RPG Project");
        primaryStage.setScene(scene);
        primaryStage.show();
        controller.startLoop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}