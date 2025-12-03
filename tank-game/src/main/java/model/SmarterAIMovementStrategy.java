package model;

import java.util.Random;

public class SmarterAIMovementStrategy implements MovementStrategy {

    private Random rand = new Random();
    private int moveStep = 2;
    private int frameCounter = 0;
    private int moveInterval = 5; // moves more smoothly

    @Override
    public void move(Tank tank) {
        frameCounter++;
        if (frameCounter < moveInterval) return;
        frameCounter = 0;

        GameModel model = tank.getModel();
        PlayerTank player = model.getPlayerTank();

        // 1. ---- Player detection radius ----
        double dx = player.getX() - tank.getX();
        double dy = player.getY() - tank.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        boolean seesPlayer = distance < 350;  // detection radius

        // 2. ---- Choose direction ----
        if (seesPlayer) {
            // move toward player
            if (Math.abs(dx) > Math.abs(dy)) {
                tank.direction = dx > 0 ? Direction.RIGHT : Direction.LEFT;
            } else {
                tank.direction = dy > 0 ? Direction.DOWN : Direction.UP;
            }
        } else {
            // random wandering when not seeing player
            if (rand.nextDouble() < 0.03) {
                tank.direction = Direction.values()[rand.nextInt(4)];
            }
        }

        // 3. ---- Predict movement & avoid walls ----
        int nextX = tank.getX();
        int nextY = tank.getY();

        switch (tank.getDirection()) {
            case UP -> nextY -= moveStep;
            case DOWN -> nextY += moveStep;
            case LEFT -> nextX -= moveStep;
            case RIGHT -> nextX += moveStep;
        }

        // If predicted movement hits a wall, choose a new direction
        if (!model.canMoveTo(nextX, nextY, tank)) {
            tank.direction = Direction.values()[rand.nextInt(4)];
            return; // skip movement this frame
        }

        // 4. ---- Move tank ----
        tank.x = nextX;
        tank.y = nextY;

        // 5. ---- Smart shooting ----
        boolean alignedX = Math.abs(dx) < 60; // vertical alignment
        boolean alignedY = Math.abs(dy) < 60; // horizontal alignment

        boolean facingPlayer =
                (dx > 0 && tank.direction == Direction.RIGHT) ||
                        (dx < 0 && tank.direction == Direction.LEFT) ||
                        (dy > 0 && tank.direction == Direction.DOWN) ||
                        (dy < 0 && tank.direction == Direction.UP);

        if (seesPlayer && facingPlayer && (alignedX || alignedY)) {
            if (rand.nextDouble() < 0.15) { // higher fire chance when aligned
                Missile m = tank.fire();
                model.getMissiles().add(m);
            }
        }

        // 6. ---- Stay inside map ----
        tank.x = Math.max(0, Math.min(tank.x, 800 - tank.getWidth()));
        tank.y = Math.max(0, Math.min(tank.y, 600 - tank.getHeight()));
    }

//    private boolean collidesWithWall(int nextX, int nextY, Tank tank, GameModel model) {
//        for (Wall w : model.getWalls()) {
//            if (w.collides(nextX, nextY, tank.getWidth(), tank.getHeight())) {
//                return true;
//            }
//        }
//        return false;
//    }
}
