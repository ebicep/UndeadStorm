package com.ebicep.undeadstorm.Entities.zombies.types;

import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;
import com.ebicep.undeadstorm.Map;

public class BossZombie extends AbstractZombie {
    public BossZombie(Map map, int x, int y) {
        super(map, x, y, 200, 2500, .2, .8, 5000, 40);
    }
}
