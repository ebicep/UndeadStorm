package com.ebicep.undeadstorm.Entities.zombies.types;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Map;
import com.ebicep.undeadstorm.MathUtil;

import java.awt.*;

public class BossProjectile extends Entity {
    private int damage;
    private Map map;

    public BossProjectile(Map map, double x, double y, int size, int damage) {
        super(x, y, size, 0, 0);
        this.damage = damage;
        this.map = map;
    }

    public void drawSelf(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int) x, (int) y, diam, diam);
    }

    public boolean act() {

        setHitBoxX((int) x);
        setHitBoxY((int) y);
        this.x += xVel;
        this.y += yVel * -1;

        if (MathUtil.distance(this, map.getPlayer()) <= ((double) this.getDiam() + map.getPlayer().getDiam()) / 2) {
            map.getPlayer().addHealth(-damage);
            return true;
        }

        return x < -10 || x > 4010 || y < -10 || y > 3010;
    }

    public int getDamage() {
        return damage;
    }
}