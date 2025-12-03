package model;
import java.util.*;

public class GameModel {
    private static GameModel instance;
    private List<Tank> tanks = new ArrayList<>();
    private List<Missile> missiles = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    private List<MedPack> medpacks = new ArrayList<>();
    private int score = 0;
    private GameEventManager eventManager = new GameEventManager();
    private GameObjectFactory factory = new GameObjectFactory();
    private final int canvasWidth = 800;
    private final int canvasHeight = 600;
    private boolean gameOver = false;

    private GameModel() {}

    //Game Loop Update
    public void update() {
        // Move tanks
        for (Tank t : tanks) {
            if (t instanceof EnemyTank enemy) {
                enemy.move(); // automatic movement
            } else {
                t.move(); // player tank movement
            }

            // Safe medpack collision check
            Iterator<MedPack> medIter = medpacks.iterator();
            while (medIter.hasNext()) {
                MedPack mp = medIter.next();
                boolean overlap =
                        t.getX() < mp.getX() + 20 &&
                                t.getX() + t.getWidth() > mp.getX() &&
                                t.getY() < mp.getY() + 20 &&
                                t.getY() + t.getHeight() > mp.getY();

                if (overlap) {
                    t.heal();
                    medIter.remove();
                }
            }
        }

        Iterator<Missile> missileIter = missiles.iterator();
        while (missileIter.hasNext()) {
            Missile m = missileIter.next();
            m.move();
        }
        eventManager.notifyObservers();
        checkMissileCollisions();
    }

    //Collision Detection for Tank Movement
    public boolean canMoveTo(int nextX, int nextY, Tank tank) {
        int size = tank.getWidth();

        // --- Canvas boundaries ---
        if (nextX < 0) return false;
        if (nextY < 0) return false;
        if (nextX + size > canvasWidth) return false;
        if (nextY + size > canvasHeight) return false;

        // --- Wall collisions ---
        for (Wall w : walls) {
            boolean overlap =
                    nextX < w.getX() + w.getWidth() &&
                            nextX + size > w.getX() &&
                            nextY < w.getY() + w.getHeight() &&
                            nextY + size > w.getY();

            if (overlap) return false;
        }

        return true;
    }

    // Missile-Tank Collision Detection
    public void checkMissileCollisions() {
        List<Missile> toRemoveMissiles = new ArrayList<>();
        List<Tank> toRemoveTanks = new ArrayList<>();

        for (Missile m : missiles) {
            for (Tank t : tanks) {
                if (m.getOwner() == t) continue;

                boolean overlap =
                        m.getX() < t.getX() + t.getWidth() &&
                                m.getX() + m.getWidth() > t.getX() &&
                                m.getY() < t.getY() + t.getHeight() &&
                                m.getY() + m.getHeight() > t.getY();

                if (overlap) {
                    t.takeDamage(40);
                    toRemoveMissiles.add(m);

                    if (t.getHealth() <= 0) {
                        toRemoveTanks.add(t);
                    }
                }
            }
        }

        missiles.removeAll(toRemoveMissiles);
        tanks.removeAll(toRemoveTanks);
    }

    //Getters
    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }
    public GameEventManager getEventManager() { return eventManager; }
    public int getScore() { return score; }
    public List<Tank> getTanks() { return tanks; }
    public List<Missile> getMissiles() { return missiles; }
    public List<Wall> getWalls() { return walls; }
    public List<MedPack> getMedPacks() { return medpacks; }

    public PlayerTank getPlayerTank() {
        for (Tank t : tanks) {
            if (t instanceof PlayerTank) {
                return (PlayerTank) t;
            }
        }
        return null;
    }
    public int getEnemyTankNumber() {
        int count = 0;

        for (Tank t : tanks) {
            if (t instanceof EnemyTank) {
                count++;
            }
        }
        return count;
    }

    //Adders
    public void addTank(Tank t) { tanks.add(t); }
    public void addMissile(Missile m) { missiles.add(m); }
    public void addWall(Wall w) { walls.add(w); }
    public void addMedPack(MedPack m) { medpacks.add(m); }
    public void increaseScore(int s) { score += s; }

    //Removers
    public void removeMissile(Missile m) {
        missiles.remove(m);
    }

    //Game Progression Methods
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean over) {
        gameOver = over;
    }
    public boolean isGameWon() {
        if(getEnemyTankNumber() == 0) {
            return true;
        }
        return false;
    }

    //Model Setups for different scenarios
    public void loadScenario1(){
        defaultModelSetup1();
    }
    public void loadScenario2(){
        defaultModelSetup2();
    }
    public void loadScenario3(){
        defaultModelSetup3();
    }
    public void loadScenario4(){
        defaultModelSetup4();
    }

    private void defaultModelSetup1() {

        // Create player tank
        tanks.add(factory.createTank(
                "player",
                100, 100,
                Direction.UP,
                this,
                new PlayerControlStrategy(Direction.DOWN)
        ));

        // Create enemy tanks
        tanks.add(factory.createTank(
                "enemy",
                300, 100,
                Direction.DOWN,
                this,
                new SmarterAIMovementStrategy()
        ));

        tanks.add(factory.createTank(
                "enemy",
                500, 200,
                Direction.LEFT,
                this,
                new SmarterAIMovementStrategy()
        ));

        // Add walls
        walls.add(factory.createWall(200, 200));
        walls.add(factory.createWall(240, 200));
        walls.add(factory.createWall(280, 200));


        // Add medpacks
        medpacks.add(factory.createMedPack(150, 150));
        medpacks.add(factory.createMedPack(600, 400));
    }
    private void defaultModelSetup2() {

        // --- PLAYER TANK ---
        tanks.add(factory.createTank(
                "player",
                100, 100,
                Direction.UP,
                this,
                new PlayerControlStrategy(Direction.UP)
        ));

        // --- ENEMY TANKS (more of them) ---
        tanks.add(factory.createTank("enemy", 300, 100, Direction.DOWN, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 500, 200, Direction.LEFT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 200, 400, Direction.UP, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 450, 350, Direction.DOWN, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 600, 500, Direction.LEFT, this, new AIMovementStrategy()));

        // --- WALLS (smaller & more of them) ---
        walls.add(factory.createWall(200, 200));
        walls.add(factory.createWall(220, 200));
        walls.add(factory.createWall(240, 200));
        walls.add(factory.createWall(260, 200));
        walls.add(factory.createWall(280, 200));

        walls.add(factory.createWall(400, 300));
        walls.add(factory.createWall(420, 300));
        walls.add(factory.createWall(440, 300));

        walls.add(factory.createWall(100, 450));
        walls.add(factory.createWall(120, 450));
        walls.add(factory.createWall(140, 450));
        walls.add(factory.createWall(160, 450));

        // --- MEDPACKS (more of them) ---
        medpacks.add(factory.createMedPack(150, 150));
        medpacks.add(factory.createMedPack(600, 400));
        medpacks.add(factory.createMedPack(350, 250));
        medpacks.add(factory.createMedPack(700, 500));
        medpacks.add(factory.createMedPack(250, 500));
    }

    private void defaultModelSetup3() {

        // --- PLAYER TANK ---
        tanks.add(factory.createTank(
                "player",
                100, 100,
                Direction.UP,
                this,
                new PlayerControlStrategy(Direction.UP)
        ));

        // --- ENEMY TANKS using AIStart ---
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 150, Direction.RIGHT, this, new AIMovementStrategy()));


        // --- WALLS ---
        walls.add(factory.createWall(250, 200));
        walls.add(factory.createWall(270, 200));
        walls.add(factory.createWall(290, 200));
        walls.add(factory.createWall(500, 300));
        walls.add(factory.createWall(520, 300));

        // --- MEDPACKS ---
        medpacks.add(factory.createMedPack(150, 150));
        medpacks.add(factory.createMedPack(600, 400));
        medpacks.add(factory.createMedPack(350, 250));
    }

    private void defaultModelSetup4() {

        // --- PLAYER TANK ---
        tanks.add(factory.createTank(
                "player",
                100, 100,
                Direction.UP,
                this,
                new PlayerControlStrategy(Direction.UP)
        ));

        // --- ENEMY TANKS using SmarterAISTart ---
        tanks.add(factory.createTank("enemy", 400, 100, Direction.DOWN, this, new SmarterAIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 600, 200, Direction.LEFT, this, new SmarterAIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 700, 400, Direction.RIGHT, this, new SmarterAIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 500, 350, Direction.UP, this, new SmarterAIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 500, 250, Direction.UP, this, new SmarterAIMovementStrategy()));
        tanks.add(factory.createTank("enemy", 300, 450, Direction.DOWN, this, new SmarterAIMovementStrategy()));

        // --- WALLS (smaller & more maze-like) ---
        walls.add(factory.createWall(200, 150));
        walls.add(factory.createWall(220, 150));
        walls.add(factory.createWall(240, 150));
        walls.add(factory.createWall(400, 250));
        walls.add(factory.createWall(420, 250));
        walls.add(factory.createWall(440, 250));
        walls.add(factory.createWall(600, 400));
        walls.add(factory.createWall(620, 400));

        // --- MEDPACKS (strategically placed) ---
        medpacks.add(factory.createMedPack(150, 100));
        medpacks.add(factory.createMedPack(700, 450));
        medpacks.add(factory.createMedPack(350, 300));
        medpacks.add(factory.createMedPack(500, 500));
    }
}
