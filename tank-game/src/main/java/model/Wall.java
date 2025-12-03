package model;

public class Wall {
    private int x, y;
    private final int width = 40;
    private final int height = 40;
    public Wall(int x, int y) { this.x = x; this.y = y; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
