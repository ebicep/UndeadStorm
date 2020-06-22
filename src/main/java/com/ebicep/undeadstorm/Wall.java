package com.ebicep.undeadstorm;

import java.awt.*;

public class Wall {
    private Map map;
    private int x;
    private int y;
    private final int width;
    private final int height;
    Rectangle hitbox;

    public Wall(Map map, int x, int y, int width, int height) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hitbox = new Rectangle(x, y, width, height);
    }

    public void drawSelf(Graphics g) {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean inside(int a, int b) {
        return hitbox.contains(a, b);
    }
}
