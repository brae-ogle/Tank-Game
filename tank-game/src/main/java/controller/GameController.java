package controller;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.*;
import view.GameView;
import javafx.application.Platform;


public class GameController implements EventHandler<KeyEvent> {

    private GameModel model;
    private GameView view;
    private AnimationTimer timer;
    private int moveStep = 5; // pixels moved per key press

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }


    public void handle(KeyEvent e) {
        if(model.isGameOver()){
            Platform.runLater(() -> {
                view.showLosePopup(model.getScore());
                timer.stop();
            });
            return;
        }
        if(model.isGameWon()){
            Platform.runLater(() -> {
                view.showWinPopup(model.getScore());
                timer.stop();
            });
            return;
        }
        PlayerTank player = model.getPlayerTank();

        switch (e.getCode()) {
            case UP -> player.setMovementStrategy(new PlayerControlStrategy(Direction.UP));
            case DOWN -> player.setMovementStrategy(new PlayerControlStrategy(Direction.DOWN));
            case LEFT -> player.setMovementStrategy(new PlayerControlStrategy(Direction.LEFT));
            case RIGHT -> player.setMovementStrategy(new PlayerControlStrategy(Direction.RIGHT));

            case SPACE -> {
                Missile m = player.fire();
                model.getMissiles().add(m);
            }
        }
    }

    public void setGameLoop(AnimationTimer timer) {
        this.timer = timer;
    }
    public AnimationTimer getTimer() {
        return this.timer;
    }


    /**
     * Call this from an AnimationTimer to continuously update game state
     */
    public void updateGameLoop() {
        model.update();  // moves tanks/missiles, handles collisions, etc.
        view.render();   // redraw canvas with updated positions
    }

}
