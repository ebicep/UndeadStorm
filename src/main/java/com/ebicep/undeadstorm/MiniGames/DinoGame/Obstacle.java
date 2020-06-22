package com.ebicep.undeadstorm.MiniGames.DinoGame;

import java.awt.*;

public class Obstacle {

    private int x;
    private int y;
    private double xVel;
    private Rectangle hitbox;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
        xVel = -10;
        hitbox = new Rectangle(this.x, this.y - 25, 50, 25);
    }

    public void drawSelf(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        g2d.fillPolygon(new int[]{x, x + 25, x + 50}, new int[]{y, y - 25, y}, 3);
    }

    public boolean act() {
        x += xVel;
        hitbox.setLocation(x, y - 25);
        return x <= -10;
    }

    public Rectangle getHitbox() {
        return hitbox;
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

    public double getxVel() {
        return xVel;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }
}
