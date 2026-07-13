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

    private static final double PLAYER_SPEED = 2.0;

    private final Map map;
    private final Player player;
    private final List<Entity> entities;
    private final GameView view;
    private final InputController inputController;
    private final GameLoop gameLoop;
    private final GameStateRepository gameStateRepository;
    private final List<EnemySpawner> enemySpawners;
    private final PlayerCombatHandler combatHandler;
    private final NPCInteractionHandler npcInteractionHandler;

    private GameStatus status = GameStatus.PLAYING;

    public GameController(Map map, Player player, GameView view, List<Entity> entities, InputController inputController,
                          GameStateRepository gameStateRepository, List<EnemySpawner> enemySpawners,
                          PlayerCombatHandler combatHandler, NPCInteractionHandler npcInteractionHandler) {
        this.map = map;
        this.player = player;
        this.entities = entities;
        this.view = view;
        this.inputController = inputController;
        this.gameLoop = new GameLoop(this::updateGame);
        this.gameStateRepository = gameStateRepository;
        this.enemySpawners = enemySpawners;
        this.combatHandler = combatHandler;
        this.npcInteractionHandler = npcInteractionHandler;
    }

    private void updateGame() {
        if (status == GameStatus.PLAYING) {
            handlePlayerMovement();
            handleSaveRequest();

            combatHandler.update(player, entities, inputController);
            npcInteractionHandler.update(player, entities, inputController, view);

            updateEntities();
            removeDeadEnemies();
            updateEnemySpawners();

            checkGameEndConditions();
        }

        view.render();
    }

    private void handlePlayerMovement() {
        if (inputController.isKeyPressed(KeyCode.W)) player.move(0, -PLAYER_SPEED, map);
        if (inputController.isKeyPressed(KeyCode.S)) player.move(0, PLAYER_SPEED, map);
        if (inputController.isKeyPressed(KeyCode.A)) player.move(-PLAYER_SPEED, 0, map);
        if (inputController.isKeyPressed(KeyCode.D)) player.move(PLAYER_SPEED, 0, map);
    }

    private void handleSaveRequest() {
        if (inputController.consumeKey(KeyCode.P)) {
            saveGame();
        }
    }

    private void saveGame() {
        GameState state = new GameState(map, entities);
        gameStateRepository.save(state);
    }

    private void updateEntities() {
        for (Entity e : entities) {
            e.update(map, player);
        }
    }

    private void updateEnemySpawners() {
        for (EnemySpawner spawner : enemySpawners) {
            spawner.update(player, entities);
        }
    }

    private void removeDeadEnemies() {
        entities.removeIf(e -> e instanceof Enemy enemy && !enemy.isAlive() && view.removeEntity(enemy));
    }

    private void checkGameEndConditions() {
        if (!player.isAlive()) {
            status = GameStatus.GAME_OVER;
            view.showGameOver();
            return;
        }

        EnemySpawner finalDungeon = enemySpawners.get(enemySpawners.size() - 1);
        if (finalDungeon.isCleared()) {
            status = GameStatus.VICTORY;
            view.showVictory();
        }
    }

    public void startLoop() {
        gameLoop.start();
    }
}