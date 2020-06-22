package com.ebicep.undeadstorm.Entities.grenades;

import com.ebicep.undeadstorm.Map;

public class Thrown implements IGrenadeState {

    private final AbstractGrenade abstractGrenade;
    private Map map;
    private boolean setCoords = true;
    private double rotationAmount = 0;

    public Thrown(Map map, AbstractGrenade newAbstractGrenade) {
        this.map = map;
        abstractGrenade = newAbstractGrenade;

    }

    @Override
    public void resting() {

    }

    @Override
    public void thrown() {
        long future = abstractGrenade.getFuture();
        if (setCoords) {
            abstractGrenade.setX(map.getPlayer().getCenterX() - abstractGrenade.getDiam() / 2);
            abstractGrenade.setY(map.getPlayer().getCenterY() - abstractGrenade.getDiam() / 2);
            setCoords = false;
        }
        if (future > System.currentTimeMillis()) {
            abstractGrenade.setX(abstractGrenade.getX() + (abstractGrenade.getxVel() / 6000) * (future - System.currentTimeMillis()));
            abstractGrenade.setY(abstractGrenade.getY() + (abstractGrenade.getyVel() / 6000) * (future - System.currentTimeMillis()) * -1);
            abstractGrenade.setRotation(abstractGrenade.getRotation() + .11 - rotationAmount);
            rotationAmount += .0004;
            if (abstractGrenade.getRotation() >= 6)
                abstractGrenade.setRotation(0);
        }
    }

    @Override
    public void exploding() {
        abstractGrenade.setGrenadeState(abstractGrenade.isExploding());
    }

    @Override
    public void blowingUp() {

    }
}
