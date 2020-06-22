package com.ebicep.undeadstorm.Entities.grenades;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Entities.Player;
import com.ebicep.undeadstorm.Entities.Projectile;
import com.ebicep.undeadstorm.Entities.grenades.types.FireBomb;
import com.ebicep.undeadstorm.Entities.grenades.types.Frag;
import com.ebicep.undeadstorm.Entities.grenades.types.Sticky;
import com.ebicep.undeadstorm.Entities.grenades.types.Stun;
import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;
import com.ebicep.undeadstorm.MathUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class AbstractGrenade extends Projectile {

    protected int numberOfGrenades = 0;
    protected int duration;
    protected int damage; //per tick, every 100 milliseconds
    protected Color color;
    protected long future;
    protected int explosionDiam;

    protected Map map;

    protected boolean changeIcon = false;

    protected double rotation = 0;
    IGrenadeState resting;
    IGrenadeState thrown;
    IGrenadeState exploding;
    IGrenadeState blownUp;
    IGrenadeState grenadeState;

    protected ImageIcon fireBombIcon = new ImageIcon(Map.class.getResource("/images/fireBomb.png"));
    protected ImageIcon fireBombExplodedIcon = new ImageIcon(Map.class.getResource("/images/fire.png"));
    protected ImageIcon fragIcon = new ImageIcon(Map.class.getResource("/images/frag.png"));
    protected ImageIcon stickyIcon = new ImageIcon(Map.class.getResource("/images/sticky.png"));
    protected ImageIcon stickyExplodedIcon = new ImageIcon(Map.class.getResource("/images/stickyPuddle.png"));
    protected ImageIcon stunIcon = new ImageIcon(Map.class.getResource("/images/stun.png"));

    public AbstractGrenade(Map map, int x, int y, int size, int duration, int damage, Color color, int explosionDiam) {
        super(map, x, y, size, damage);
        this.map = map;
        numberOfGrenades++;
        this.duration = duration;
        this.damage = damage;
        this.color = color;
        this.explosionDiam = explosionDiam;
        future = -1;
        resting = new Resting(this);
        setGrenadeState(resting);
        thrown = new Thrown(map, this);
        exploding = new Exploding(map, this);
        blownUp = new BlownUp(this);
    }

    public String toString() {
        String output = "x = " + (int) getX() + " y = " + (int) getY() + " xVel = " + xVel + " yVel = " + yVel + " ";

        if (grenadeState instanceof Resting)
            output += ("resting");
        else if (grenadeState instanceof Thrown)
            output += ("thrown");
        else if (grenadeState instanceof Exploding)
            output += ("exploding");
        else if (grenadeState instanceof BlownUp)
            output += ("blownup");
        else
            output += "something is wrong no state";

        return output;
    }

    public void setGrenadeState(IGrenadeState newGrenadeState) {
        grenadeState = newGrenadeState;
    }

    public void nowResting() {
        grenadeState.resting();
    }

    public void nowThrown() {
        grenadeState.thrown();
    }

    public void nowExploding() {
        grenadeState.exploding();
    }

    public void nowBlownUp() {
        grenadeState.blowingUp();
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public IGrenadeState resting() {
        return resting;
    }

    public IGrenadeState isThrown() {
        return thrown;
    }

    public IGrenadeState isExploding() {
        return exploding;
    }

    public IGrenadeState isBlownUp() {
        return blownUp;
    }


    @Override
    public void drawSelf(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (!changeIcon) {
            AffineTransform old = g2d.getTransform();
            g2d.rotate(rotation, getCenterX(), getCenterY() + 10);
            if (this instanceof FireBomb) {
                g2d.drawImage(fireBombIcon.getImage(), (int) x, (int) y, (int) (diam * 2.5), (int) (diam * 2.5), null);
            } else if (this instanceof Frag) {
                g2d.drawImage(fragIcon.getImage(), (int) x, (int) y, diam, diam, null);
            } else if (this instanceof Sticky) {
                g2d.drawImage(stickyIcon.getImage(), (int) x, (int) y, diam * 2, diam * 2, null);
            } else if (this instanceof Stun) {
                g2d.drawImage(stunIcon.getImage(), (int) x, (int) y, diam * 2, diam * 2, null);
            }
            g2d.setTransform(old);
        } else {
            if (this instanceof FireBomb) {
                g2d.drawImage(fireBombExplodedIcon.getImage(), (int) x - explosionDiam / 2, (int) y - explosionDiam / 2, explosionDiam, explosionDiam, null);
            } else if (this instanceof Sticky) {
                g2d.drawImage(stickyExplodedIcon.getImage(), (int) x - explosionDiam / 2, (int) y - explosionDiam / 2, explosionDiam, explosionDiam, null);
            }
        }

    }

    @Override
    public boolean act() {

        Player p = map.getPlayer();

        setHitBoxX((int) x);
        setHitBoxY((int) y);


        if (future != -1 && System.currentTimeMillis() <= future) {
            nowThrown();
        } else if (future != -1 && System.currentTimeMillis() <= future + 1000) {
            rotation = 0;
            nowExploding();
        } else if (future != -1) {
            grenadeState.blowingUp();
        }
        if (future != -1 && future + 2000 + duration < System.currentTimeMillis()) {
            for (Entity e : Game.getMap().getEntities()) {
                if (e instanceof AbstractZombie) {
                    ((AbstractZombie) e).setSpeed(((AbstractZombie) e).getNormalSpeed());
                }
            }
            future = -1;
            return true;
        }

        //checking if this grenade is in the grenade slot
        if (p.getGrenadeslot() == this && future == -1) {
            x = -100;
            y = -100;
            return false;
        }

        //picking up grenade
        if (future == -1 && MathUtil.distance(p, this) <= (diam / 2f) + (p.getDiam() / 2f)) {
            p.setGrenadeslot(this);
        }
        return false;
    }


    //fires where mouse position is
    public abstract void throwing(double x, double y, double xVel, double yVel);

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public double getxVel() {
        return xVel;
    }

    @Override
    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    @Override
    public double getyVel() {
        return yVel;
    }

    @Override
    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public int getExplosionDiam() {
        return explosionDiam;
    }

    public int getDamage() {
        return damage;
    }

    public long getFuture() {
        return future;
    }

    public ImageIcon getFireBombIcon() {
        return fireBombIcon;
    }

    public ImageIcon getFragIcon() {
        return fragIcon;
    }

    public ImageIcon getStickyIcon() {
        return stickyIcon;
    }

    public ImageIcon getStunIcon() {
        return stunIcon;
    }
}
