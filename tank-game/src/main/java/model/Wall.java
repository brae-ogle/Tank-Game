package model;

public class Wall {
    private int x, y;
    //Optional attributes for wall dimensions
    //Options to customize wall size if needed
    private final int width = 40;
    private final int height = 40;
    public Wall(int x, int y) { this.x = x; this.y = y; }

    //Getter methods
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    //Setter methods
    public void setWidth(int width) { /* Optional: implement if wall size is customizable */ }
    public void setHeight(int height) { /* Optional: implement if wall size is customizable */}
}
