package com.ebicep.undeadstorm.Entities.zombies.types;

import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;
import com.ebicep.undeadstorm.Map;

public class DogZombie extends AbstractZombie {
    public DogZombie(Map map, int x, int y) {
        super(map, x, y, 25, 50, 2, .6, 500, 10);
    }
}
