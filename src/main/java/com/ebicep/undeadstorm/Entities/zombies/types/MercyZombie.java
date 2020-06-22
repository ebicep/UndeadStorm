package com.ebicep.undeadstorm.Entities.zombies.types;

import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;
import com.ebicep.undeadstorm.Map;

public class MercyZombie extends AbstractZombie {
    public MercyZombie(Map map, int x, int y) {
        super(map, x, y, 60, 125, 1.2, .5, 1500, 15);
    }
}
