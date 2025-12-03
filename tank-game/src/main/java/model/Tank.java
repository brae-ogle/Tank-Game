package model;

public abstract class Tank {
    protected int x, y;
    protected int health = 100;
    protected Direction direction;
    protected MovementStrategy movementStrategy;
    private GameModel model;

    public Tank(int x, int y, Direction direction, GameModel model, MovementStrategy movementStrategy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.model = model;
        this.movementStrategy = movementStrategy;
    }

    public void move() {
        if (movementStrategy == null) return;
        int oldX = x;
        int oldY = y;
        movementStrategy.move(this);
        if (!model.canMoveTo(x, y, this)) {
            x = oldX;
            y = oldY;
        }
    }




    public Missile fire() {
        return new Missile(x, y, direction, this, model);
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health <= 0) {
            explode();
            if(this instanceof PlayerTank) {
                model.setGameOver(true);
            }
        }

    }

    public void heal() {
        health = 100;
        if(this instanceof PlayerTank) {
            model.increaseScore(10);
        }
    }

    protected void explode() {
        if(this instanceof EnemyTank) {
            model.increaseScore(50);
        }
        // Notify observers (view) about the explosion
        model.getEventManager().notifyExplosion(new ExplosionEvent(x, y));
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Direction getDirection() { return direction; }
    public int getWidth() { return 40; }
    public int getHeight() { return 40; }
    public int getHealth() { return health; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setDirection(Direction direction) { this.direction = direction; }
    public GameModel getModel() { return model; }
}