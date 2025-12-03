package model;
public class MedPack {
    private int x, y;
    public MedPack(int x, int y) { this.x = x; this.y = y; }
    public void heal(Tank t) { t.heal(); }
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
}
