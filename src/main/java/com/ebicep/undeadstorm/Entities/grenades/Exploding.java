package com.ebicep.undeadstorm.Entities.grenades;

import com.ebicep.undeadstorm.Entities.Projectile;
import com.ebicep.undeadstorm.Entities.grenades.types.Frag;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;

import java.util.ArrayList;

public class Exploding implements IGrenadeState {

    AbstractGrenade abstractGrenade;
    long future;
    Map map;

    private boolean playGrenadeSound = true;

    public Exploding(Map map, AbstractGrenade newAbstractGrenade) {
        this.map = map;
        abstractGrenade = newAbstractGrenade;
        future = abstractGrenade.getFuture();
    }

    @Override
    public void resting() {

    }

    @Override
    public void thrown() {

    }

    @Override
    public void exploding() {
        //exploding state - after 5 seconds
        abstractGrenade.changeIcon = true;
        if (playGrenadeSound) {
            Game.changeSoundEffect(2);
            playGrenadeSound = false;
        }
        if (abstractGrenade instanceof Frag) {
            ArrayList<Projectile> projectiles = new ArrayList<>();
            int degree = 0;
            for (int i = 0; i < 20; i++) {
                double speed = (Math.random() * 2) + 2;
                Projectile tempProjectile = new Projectile(map, abstractGrenade.getX(), abstractGrenade.getY(), 5, 40);
                tempProjectile.setxVel(speed * Math.cos(degree));
                tempProjectile.setyVel(speed * Math.sin(degree));
                projectiles.add(tempProjectile);
                degree += Math.random() * 10;
            }
            for (Projectile p : projectiles)
                Game.getMap().getEntities().add(p);
        }

        abstractGrenade.setGrenadeState(abstractGrenade.isBlownUp());
    }

    @Override
    public void blowingUp() {

    }
}
