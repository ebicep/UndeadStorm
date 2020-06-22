package com.ebicep.undeadstorm.Entities.guns.types;

import com.ebicep.undeadstorm.Entities.Projectile;
import com.ebicep.undeadstorm.Entities.guns.AbstractGun;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;

import java.awt.*;

public class MachineGun extends AbstractGun {
    public MachineGun(Map map, int x, int y) {
        super(map, x, y, 40, 100, Color.GREEN, 25, 300);
    }

    public void resetStats() {
        resetToTheseStats(25, 300);
    }

    public void fire(double x, double y, double xVel, double yVel) {
        if (ammo > 0) {
            if (System.currentTimeMillis() > lastShot + cooldown && !map.isDrawUpgradeMenu()) {
                Game.changeSoundEffect(5);
                Projectile tempProjectile = new Projectile(map, x, y, 5, this.getDPS());
                tempProjectile.setxVel(xVel * 3);
                tempProjectile.setyVel(yVel * 3);
                Game.getMap().getEntities().add(tempProjectile);
                ammo--;
                lastShot = System.currentTimeMillis();
            }
        } else
            Game.changeSoundEffect(6);
    }
}
