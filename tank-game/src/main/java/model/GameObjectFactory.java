package model;
//Factory class to create game objects
public class GameObjectFactory {
    public Tank createTank(String type, int x, int y, Direction d, GameModel m, MovementStrategy ms) {
        return switch (type.toLowerCase()) {
            case "player" -> new PlayerTank(x, y, d, m, ms);
            case "enemy" -> new EnemyTank(x, y, d, m, ms);
            default -> null;
        };
    }
    public Missile createMissile(Tank t, GameModel model) {
        return new Missile(t.x, t.y, t.direction, t, model);
    }
    public Wall createWall(int x, int y) { return new Wall(x, y); }
    public MedPack createMedPack(int x, int y) { return new MedPack(x, y); }
}
