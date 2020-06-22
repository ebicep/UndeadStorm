package com.ebicep.undeadstorm.Entities.guns.types;

import com.ebicep.undeadstorm.Entities.Projectile;
import com.ebicep.undeadstorm.Entities.guns.AbstractGun;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;

import java.awt.*;

public class Sniper extends AbstractGun {
    public Sniper(Map map, int x, int y) {
        super(map, x, y, 15, 25, Color.PINK, 150, 2000);
    }

    public void resetStats() {
        resetToTheseStats(150, 2000);
    }

    public void fire(double x, double y, double xVel, double yVel) {
        if (ammo > 0) {
            if (System.currentTimeMillis() > lastShot + cooldown && !map.isDrawUpgradeMenu()) {
                Game.changeSoundEffect(5);
                Projectile tempProjectile = new Projectile(map, x, y, 7, this.getDPS());
                tempProjectile.setxVel(xVel * 5);
                tempProjectile.setyVel(yVel * 5);
                Game.getMap().getEntities().add(tempProjectile);
                ammo--;
                lastShot = System.currentTimeMillis();
            }
        } else
            Game.changeSoundEffect(6);
    }
}
