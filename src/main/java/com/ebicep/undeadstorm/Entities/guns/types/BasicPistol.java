package com.ebicep.undeadstorm.Entities.guns.types;

import com.ebicep.undeadstorm.Entities.Projectile;
import com.ebicep.undeadstorm.Entities.guns.AbstractGun;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;

import java.awt.*;

public class BasicPistol extends AbstractGun {
    public BasicPistol(Map map, int x, int y) {
        super(map, x, y, 20, 50, Color.BLACK, 20, 350);
    }

    public void resetStats() {
        resetToTheseStats(20, 350);
    }

    public void fire(double x, double y, double xVel, double yVel) {
        //counter if the amount of ammo
        if (ammo > 0) {
            if (System.currentTimeMillis() > lastShot + cooldown && !map.isDrawUpgradeMenu()) {
                Game.changeSoundEffect(5);
                Projectile tempProjectile = new Projectile(map, x, y, 4, this.getDPS());
                tempProjectile.setxVel(xVel);
                tempProjectile.setyVel(yVel);
                Game.getMap().getEntities().add(tempProjectile);
                ammo--;
                lastShot = System.currentTimeMillis();
            }
        } else
            Game.changeSoundEffect(6);
    }
}
