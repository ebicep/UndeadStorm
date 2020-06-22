package com.ebicep.undeadstorm.Entities.grenades;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Entities.grenades.types.Frag;
import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.MathUtil;

public class BlownUp implements IGrenadeState {

    AbstractGrenade abstractGrenade;

    public BlownUp(AbstractGrenade newAbstractGrenade) {
        abstractGrenade = newAbstractGrenade;

    }


    @Override
    public void resting() {

    }

    @Override
    public void thrown() {

    }

    @Override
    public void exploding() {

    }

    @Override
    public void blowingUp() {
        long future = abstractGrenade.getFuture();
        if (abstractGrenade instanceof Frag) {

        } else if (future + 1000 < System.currentTimeMillis()) {
            for (Entity e : Game.getMap().getEntities()) {
                if (e instanceof AbstractZombie) {
                    if (MathUtil.distance(abstractGrenade, e) <= ((double) abstractGrenade.explosionDiam + e.getDiam()) / 2) {
                        ((AbstractZombie) e).damage(abstractGrenade.getDamage());
                    }
                }
            }
        }
    }

}
