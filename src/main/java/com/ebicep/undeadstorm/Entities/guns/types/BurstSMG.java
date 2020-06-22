package com.ebicep.undeadstorm.Entities.guns.types;

import com.ebicep.undeadstorm.Entities.Projectile;
import com.ebicep.undeadstorm.Entities.guns.AbstractGun;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;

import java.awt.*;

public class BurstSMG extends AbstractGun {
    public BurstSMG(Map map, int x, int y) {
        super(map, x, y, 30, 40, Color.PINK, 15, 750);
    }

    public void resetStats() {
        resetToTheseStats(15, 750);
    }

    public void fire(double x, double y, double xVel, double yVel) {
        if (ammo > 0) {
            if (System.currentTimeMillis() > lastShot + cooldown && !map.isDrawUpgradeMenu()) {
                Game.changeSoundEffect(5);
                double spaceBetweenBullets = 1;
                for (int i = 0; i < 4; i++) {
                    Projectile tempProjectile = new Projectile(map, x, y, 3, this.getDPS());
                    tempProjectile.setxVel(xVel * spaceBetweenBullets);
                    tempProjectile.setyVel(yVel * spaceBetweenBullets);
                    Game.getMap().getEntities().add(tempProjectile);
                    spaceBetweenBullets += .2;
                }
                ammo--;
                lastShot = System.currentTimeMillis();
            }
        } else
            Game.changeSoundEffect(6);
    }
}
