package model;

import java.util.Random;

public class AIMovementStrategy implements MovementStrategy {
    private Random rand = new Random();
    private int moveStep = 2;
    private int frameCounter = 0;
    private int moveInterval = 10; // move only every 10 frames

    @Override
    public void move(Tank tank) {
        frameCounter++;
        if (frameCounter < moveInterval) return;
        frameCounter = 0;

        // Randomly change direction sometimes
        if (rand.nextDouble() < 0.05) {
            int dir = rand.nextInt(4);
            tank.direction = Direction.values()[dir];
        }

        // Move tank
        switch (tank.getDirection()) {
            case UP -> tank.y -= moveStep;
            case DOWN -> tank.y += moveStep;
            case LEFT -> tank.x -= moveStep;
            case RIGHT -> tank.x += moveStep;
        }

        if (rand.nextDouble() < 0.011) {
            Missile m = tank.fire();
            tank.getModel().getMissiles().add(m);
        }

        // Keep inside canvas
        tank.x = Math.max(0, Math.min(tank.x, 800 - tank.getWidth()));
        tank.y = Math.max(0, Math.min(tank.y, 600 - tank.getHeight()));
    }
}
