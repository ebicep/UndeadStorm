package com.ebicep.undeadstorm.Entities;

import com.ebicep.undeadstorm.Entities.guns.types.Sniper;
import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;
import com.ebicep.undeadstorm.MathUtil;

import java.awt.*;

public class Projectile extends Entity {
    private int damage;
    private Map map;
    private int pierceCounter;

    public Projectile(Map map, double x, double y, int size, int damage) {
        super(x, y, size, 0, 0);
        this.damage = damage;
        this.map = map;
        pierceCounter = 0;
    }

    public void drawSelf(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) x, (int) y, diam, diam);
    }

    public boolean act() {

        setHitBoxX((int) x);
        setHitBoxY((int) y);
        this.x += xVel;
        this.y += yVel * -1;


        for (Entity e : Game.getMap().getEntities()) {
            if (e instanceof AbstractZombie) {
                if (MathUtil.distance(this, e) <= ((double) this.getDiam() + e.getDiam()) / 2) {
                    ((AbstractZombie) e).damage(damage);
                    ((AbstractZombie) e).setHitCooldown(System.currentTimeMillis() + 200);
                    try {
                        if (map.getPlayer().getGunslot1() instanceof Sniper || map.getPlayer().getGunslot2() instanceof Sniper) {
                            pierceCounter++;
                            return pierceCounter == 2;
                        }
                    } catch (NullPointerException x) {

                    }
                    return true;
                }
            }
        }
        return false;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}
