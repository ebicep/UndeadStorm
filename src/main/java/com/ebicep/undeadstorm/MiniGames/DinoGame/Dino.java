package com.ebicep.undeadstorm.MiniGames.DinoGame;

import com.ebicep.undeadstorm.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Dino implements KeyListener {

    private int x;
    private int y;
    private double yVel;
    private boolean dead;
    private Rectangle hitbox;
    private int width;
    private int height;

    private boolean jumping;
    private long stopJumping;

    private ImageIcon dinoIcon = new ImageIcon(Game.class.getResource("/images/dino.png"));

    public Dino() {
        x = 200;
        y = 100;
        yVel = 0;
        dead = false;
        width = 40;
        height = 100;
        hitbox = new Rectangle(x, y, width, height);
        jumping = false;
        stopJumping = -1;
    }

    public void drawSelf(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.drawImage(dinoIcon.getImage(), x - 15, y + 5, dinoIcon.getIconWidth() / 9, dinoIcon.getIconHeight() / 9, null);
        //g2d.draw(hitbox);
    }

    public void act() {
        hitbox.setLocation(x, y);
        if (jumping && System.currentTimeMillis() < stopJumping) {
            if (System.currentTimeMillis() < stopJumping - 100)
                y -= 8;
        } else
            y += 8;
        if (y + height >= 425) {
            y = 425 - height;
        }
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

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public long getStopJumping() {
        return stopJumping;
    }

    public void setStopJumping(long stopJumping) {
        this.stopJumping = stopJumping;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 32 && y == 425 - height) {
            jumping = true;
            stopJumping = System.currentTimeMillis() + 300;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
