package model;
//Strategy Pattern
public class PlayerControlStrategy implements MovementStrategy {
    private Direction direction;
    private final int speed = 1;
    public PlayerControlStrategy(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void move(Tank tank) {
        //Move the tank based on the most recent direction input
        switch (direction) {
            case UP -> tank.setY(tank.getY() - speed);
            case DOWN -> tank.setY(tank.getY() + speed);
            case LEFT -> tank.setX(tank.getX() - speed);
            case RIGHT -> tank.setX(tank.getX() + speed);
        }
        tank.setDirection(direction);
    }
}
