package com.ebicep.undeadstorm.MiniGames.PongGame;

import java.awt.*;

public class Ball {
    private int x;
    private int y;
    private double xVel;
    private double yVel;
    private int diam;

    public Ball() {
        x = 1500 / 2;
        y = 750 / 2;
        xVel = Math.random() * -3 - 3;
        yVel = Math.random() * -3 - 3;
        diam = 20;
    }

    public void act() {
        x += xVel;
        y += yVel;

    }

    public void drawSelf(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x, y, diam, diam);
    }

    public void reset() {
        x = 1500 / 2;
        y = 750 / 2;
        xVel = Math.random() * -3 - 3;
        yVel = Math.random() * -3 - 3;
    }

    public int getDiam() {
        return diam;
    }

    public int getCenterX() {
        return x + diam / 2;
    }

    public int getCenterY() {
        return y + diam / 2;
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

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }
}
