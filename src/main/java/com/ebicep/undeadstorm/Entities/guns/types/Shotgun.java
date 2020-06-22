package com.ebicep.undeadstorm.Entities.guns.types;

import com.ebicep.undeadstorm.Entities.Projectile;
import com.ebicep.undeadstorm.Entities.guns.AbstractGun;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;

import java.awt.*;

public class Shotgun extends AbstractGun {
    public Shotgun(Map map, int x, int y) {
        super(map, x, y, 30, 25, Color.RED, 18, 900);
    }

    public void resetStats() {
        resetToTheseStats(18, 900);
    }

    public void fire(double x, double y, double xVel, double yVel) {
        if (ammo > 0) {
            if (System.currentTimeMillis() > lastShot + cooldown && !map.isDrawUpgradeMenu()) {
                Game.changeSoundEffect(5);
                for (int i = 0; i < 5; i++) {
                    double degreeX = Math.random() * 2;
                    double degreeY = Math.random() * 2;
                    Projectile tempProjectile = new Projectile(map, x, y, 5, this.getDPS());
                    tempProjectile.setxVel(xVel + degreeX);
                    tempProjectile.setyVel(yVel + degreeY);
                    Game.getMap().getEntities().add(tempProjectile);
                }
                ammo--;
                lastShot = System.currentTimeMillis();
            }
        } else
            Game.changeSoundEffect(6);
    }
}
