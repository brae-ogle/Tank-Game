package model;


public class PlayerControlStrategy implements MovementStrategy {
    private Direction direction;

    public PlayerControlStrategy(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void move(Tank tank) {
        int speed = 1;

        int oldX = tank.getX();
        int oldY = tank.getY();

        switch (direction) {
            case UP -> tank.setY(oldY - speed);
            case DOWN -> tank.setY(oldY + speed);
            case LEFT -> tank.setX(oldX - speed);
            case RIGHT -> tank.setX(oldX + speed);
        }

        tank.setDirection(direction);
    }
}
