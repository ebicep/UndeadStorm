package com.ebicep.undeadstorm.Entities.zombies.types;

import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;
import com.ebicep.undeadstorm.Map;

public class FatZombie extends AbstractZombie {
    public FatZombie(Map map, int x, int y) {
        super(map, x, y, 80, 250, .5, 1, 2500, 20);
    }
}
