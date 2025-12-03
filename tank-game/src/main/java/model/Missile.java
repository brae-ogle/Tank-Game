package model;
public class Missile {
    private int x, y, speed = 2;
    private Direction direction;
    private Tank owner;
    private GameModel model;

    public Missile(int x, int y, Direction direction,Tank owner, GameModel model) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.owner = owner;
        this.model = model;
    }

    public void move() {
        switch (direction) {
            case UP -> y -= speed;
            case DOWN -> y += speed;
            case LEFT -> x -= speed;
            case RIGHT -> x += speed;
        }
        if(model.canMoveTo(x, y, owner)) {
            x = x;
            y = y;
        } else {
            // Notify observers (view) about the explosion
            model.getEventManager().notifyExplosion(new ExplosionEvent(x, y));
            //Destroy Missile
            model.removeMissile(this);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Direction getDirection() { return direction; }
    public int getWidth() { return 20; }
    public int getHeight() { return 20; }
    public Tank getOwner() { return owner; }
}
