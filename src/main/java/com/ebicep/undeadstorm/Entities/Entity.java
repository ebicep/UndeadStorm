package com.ebicep.undeadstorm.Entities;

import com.ebicep.undeadstorm.Entities.grenades.AbstractGrenade;
import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;

import java.awt.*;

public abstract class Entity implements IPosition {

    protected double x;
    protected double y;
    protected int diam;
    protected double xVel;
    protected double yVel;

    protected Rectangle hitbox;
    protected int hitBoxX;
    protected int hitBoxY;
    protected int hitBoxWidth;
    protected int hitBoxHeight;

    public Entity(double x, double y, int diam, double xVel, double yVel) {
        this.x = x;
        this.y = y;
        this.diam = diam;
        this.xVel = xVel;
        this.yVel = yVel;

        hitBoxX = (int) x;
        hitBoxY = (int) y;
        hitBoxWidth = diam;
        hitBoxHeight = diam;
        hitbox = new Rectangle(hitBoxX, hitBoxY, hitBoxWidth, hitBoxHeight);
    }

    public abstract void drawSelf(Graphics g);

    public abstract boolean act();


    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public double getCenterX() {
        return this.x + ((double) diam / 2);
    }

    @Override
    public double getCenterY() {
        return this.y + ((double) diam / 2);
    }

    public int getDiam() {
        return diam;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDiam(int diam) {
        this.diam = diam;
    }

    public Rectangle getBoundsHorizontal() {
        if (this instanceof Projectile)
            return new Rectangle((int) (hitBoxX + xVel), hitBoxY, hitBoxWidth, hitBoxHeight);
        else if (this instanceof AbstractZombie) {
            return new Rectangle((int) (hitBoxX + xVel * 3), hitBoxY, (int) (hitBoxWidth + xVel * 3), hitBoxHeight);
        }
        return new Rectangle(hitBoxX, hitBoxY, hitBoxWidth, hitBoxHeight);
    }

    public Rectangle getBoundsVertical() {
        if (this instanceof AbstractZombie) {
            return new Rectangle(hitBoxX, (int) (hitBoxY + yVel * -5), hitBoxWidth, (int) (hitBoxHeight + yVel * -5));
        } else if (this instanceof AbstractGrenade)
            return new Rectangle(hitBoxX, (int) (hitBoxY + yVel * -2), hitBoxWidth, hitBoxHeight);

        return new Rectangle(hitBoxX, (int) (hitBoxY + yVel), hitBoxWidth, hitBoxHeight);
    }

    public int getHitBoxWidth() {
        return hitBoxWidth;
    }

    public int getHitBoxHeight() {
        return hitBoxHeight;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public int getHitBoxX() {
        return hitBoxX;
    }

    public void setHitBoxX(int hitBoxX) {
        this.hitBoxX = hitBoxX;
    }

    public int getHitBoxY() {
        return hitBoxY;
    }

    public void setHitBoxY(int hitBoxY) {
        this.hitBoxY = hitBoxY;
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
