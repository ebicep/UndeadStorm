package com.ebicep.undeadstorm.Entities.zombies.types;

import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;
import com.ebicep.undeadstorm.Map;


public class BasicZombie extends AbstractZombie {
    public BasicZombie(Map map, int x, int y) {
        super(map, x, y, 50, 100, 1, .4, 1000, 5);
    }

}
