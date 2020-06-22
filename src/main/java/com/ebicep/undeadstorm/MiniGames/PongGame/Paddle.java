package com.ebicep.undeadstorm.MiniGames.PongGame;

import java.awt.*;

public class Paddle {
    private int x;
    private int y;
    private int width;
    private int height;

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
        width = 20;
        height = 100;
    }

    public void drawSelf(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
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
}
