package com.ebicep.undeadstorm.Entities.grenades.types;

import com.ebicep.undeadstorm.Entities.grenades.AbstractGrenade;
import com.ebicep.undeadstorm.Map;

import java.awt.*;

public class Frag extends AbstractGrenade {

    public Frag(Map map, int x, int y) {
        super(map, x, y, 25, 2000, 40, Color.BLUE, 0);
    }

    @Override
    public void throwing(double x, double y, double xVel, double yVel) {
        if (numberOfGrenades > 0) {
            this.xVel = xVel / 2;
            this.yVel = yVel / 2;
            future = System.currentTimeMillis() + 5000;

        }
    }
}
