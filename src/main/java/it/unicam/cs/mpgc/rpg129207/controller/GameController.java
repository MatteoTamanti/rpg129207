package it.unicam.cs.mpgc.rpg129207.controller;

import it.unicam.cs.mpgc.rpg129207.model.Enemy;
import it.unicam.cs.mpgc.rpg129207.model.Entity;
import it.unicam.cs.mpgc.rpg129207.model.Map;
import it.unicam.cs.mpgc.rpg129207.model.Player;
import it.unicam.cs.mpgc.rpg129207.persistence.GameState;
import it.unicam.cs.mpgc.rpg129207.persistence.GameStateRepository;
import it.unicam.cs.mpgc.rpg129207.view.GameView;
import java.util.List;
import javafx.scene.input.KeyCode;

public class GameController {
    private Map map;
    private Player player;
    private List<Entity> entities;
    private GameView view;
    private InputController inputController;
    private GameLoop gameLoop;
    private GameStateRepository gameStateRepository;
    private List<EnemySpawner> enemySpawners;
    private PlayerCombatHandler combatHandler;

    public GameController(Map map, Player player,  GameView view, List<Entity> entities, InputController inputController,
                          GameStateRepository gameStateRepository, List<EnemySpawner> enemySpawners, PlayerCombatHandler combatHandler) {
        this.map = map;
        this.player = player;
        this.entities = entities;
        this.view = view;
        this.inputController  = inputController;
        this.gameLoop = new GameLoop(this::updateGame);
        this.gameStateRepository = gameStateRepository;
        this.enemySpawners = enemySpawners;
        this.combatHandler = combatHandler;
    }

    private void saveGame() {
        GameState state = new GameState(map, entities);
        gameStateRepository.save(state);
    }

    private void updateGame() {
        double speed = 2.0;

        if (inputController.isKeyPressed(KeyCode.W)) player.move(0, -speed, map);
        if (inputController.isKeyPressed(KeyCode.S)) player.move(0, speed, map);
        if (inputController.isKeyPressed(KeyCode.A)) player.move(-speed, 0, map);
        if (inputController.isKeyPressed(KeyCode.D)) player.move(speed, 0, map);

        if (inputController.consumeKey(KeyCode.P)) {
            saveGame();
        }

        combatHandler.update(player, entities, inputController);

        for (Entity e : entities) {
            e.update(map, player);
        }

        removeDeadEnemies();

        for (EnemySpawner spawner : enemySpawners) {
            spawner.update(player, entities);
        }

        view.render();
    }

    private void removeDeadEnemies() {
        entities.removeIf(e -> e instanceof Enemy enemy && !enemy.isAlive() && view.removeEntity(enemy));
    }

    public void startLoop() {
        gameLoop.start();
    }
}
