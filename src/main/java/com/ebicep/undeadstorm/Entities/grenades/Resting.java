package com.ebicep.undeadstorm.Entities.grenades;

public class Resting implements IGrenadeState {

    private final AbstractGrenade abstractGrenade;
    private final long future;

    public Resting(AbstractGrenade newAbstractGrenade) {
        abstractGrenade = newAbstractGrenade;
        future = abstractGrenade.getFuture();
    }

    @Override
    public void resting() {

    }

    @Override
    public void thrown() {
        abstractGrenade.setGrenadeState(abstractGrenade.isThrown());
    }

    @Override
    public void exploding() {

    }

    @Override
    public void blowingUp() {

    }
}
