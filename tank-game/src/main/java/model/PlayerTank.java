package model;
public class PlayerTank extends Tank {
    public PlayerTank(int x, int y, Direction d, GameModel m, MovementStrategy ms) {
        super(x, y, d, m, ms);
    }

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }
}
