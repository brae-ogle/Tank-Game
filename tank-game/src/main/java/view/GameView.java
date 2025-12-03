package view;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Rectangle;
import javafx.application.Platform;
import model.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GameView implements Observer {
    private Canvas canvas;
    private GraphicsContext gc;
    private GameModel model;
    private SpriteManager spriteManager;
    private ArrayList<ExplosionEvent> activeExplosions = new ArrayList<>();

    public GameView(GameModel model, Canvas canvas) {
        this.model = model;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.spriteManager = new SpriteManager();
        model.getEventManager().attach(this);
    }

    @Override
    public void update() {
        render();
        for (ExplosionEvent e : model.getEventManager().getExplosionEvents()) {
            activeExplosions.add(e);
        }
        model.getEventManager().clearExplosionEvents();
    }


    public void render() {
        // Clear screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if(model.isGameOver()) return;
        drawTanks();
        drawMissles();
        drawWalls();
        drawExplosions();
        drawMedPacks();
        drawHealthBar();
    }

    public void drawExplosions() {
        Iterator<ExplosionEvent> it = activeExplosions.iterator();
        while (it.hasNext()) {
            ExplosionEvent exp = it.next();
            exp.update();
            if (exp.isFinished()) {
                it.remove();
            } else {
                gc.drawImage(exp.getCurrentFrame(), exp.getX(), exp.getY());
            }
        }

    }

    public void drawTanks(){
        // Draw tanks
        for (Tank tank : model.getTanks()) {
            Image img;
            switch (tank.getDirection()) {
                case UP -> img = spriteManager.tankUp;
                case DOWN -> img = spriteManager.tankDown;
                case LEFT -> img = spriteManager.tankLeft;
                case RIGHT -> img = spriteManager.tankRight;
                default -> img = spriteManager.tankUp;
            }
            gc.drawImage(img, tank.getX(), tank.getY(), tank.getWidth(), tank.getHeight());
        }
    }

    public  void drawMissles() {
        for (Missile missile : model.getMissiles()) {
            Image img;
            switch (missile.getDirection()) {
                case UP -> img = spriteManager.missileUp;
                case DOWN -> img = spriteManager.missileDown;
                case LEFT -> img = spriteManager.missileLeft;
                case RIGHT -> img = spriteManager.missileRight;
                default -> img = spriteManager.missileUp;
            }
            gc.drawImage(img, missile.getX(), missile.getY(), missile.getWidth(), missile.getHeight());
        }
    }

    public void drawWalls() {
        for (Wall w : model.getWalls()) {
            gc.drawImage(
                    spriteManager.wall,
                    w.getX(),
                    w.getY(),
                    w.getWidth(),
                    w.getHeight()
            );
        }
    }

    public void drawMedPacks() {
        for (MedPack m : model.getMedPacks()) {
            gc.drawImage(spriteManager.medpack, m.getX(), m.getY(), 40, 40);
        }
    }


    public void drawHealthBar() {
        double barX = canvas.getWidth() - 270;      // distance from left edge
        double barY = 20;      // distance from top edge
        double barWidth = 250;
        double barHeight = 20;

        // Background (red)
        gc.setFill(Color.DARKRED);
        gc.fillRect(barX, barY, barWidth, barHeight);

        // Foreground (green) – scaled to health
        double percent = model.getPlayerTank().getHealth() / 100.0;
        gc.setFill(Color.LIMEGREEN);
        gc.fillRect(barX, barY, barWidth * percent, barHeight);

    }

    public void showLosePopup(int score) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("You Lose!");
            alert.setContentText("Your Score: " + score);
            alert.showAndWait();
        });
    }

    public void showWinPopup(int score) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Victory!");
            alert.setHeaderText("You Win!");
            alert.setContentText("Your Score: " + score);
            alert.showAndWait();
        });
    }




}
