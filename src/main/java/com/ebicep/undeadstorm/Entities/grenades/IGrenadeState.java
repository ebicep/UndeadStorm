package com.ebicep.undeadstorm.Entities.grenades;

public interface IGrenadeState {
    //when grenade is just still
    void resting();

    //grenade throw
    void thrown();

    //grenade stops and waits 5 seconds
    void exploding();

    //grenade blows up
    void blowingUp();
}
