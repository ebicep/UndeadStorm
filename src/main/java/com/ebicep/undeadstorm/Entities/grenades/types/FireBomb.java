package com.ebicep.undeadstorm.Entities.grenades.types;

import com.ebicep.undeadstorm.Entities.grenades.AbstractGrenade;
import com.ebicep.undeadstorm.Map;

import java.awt.*;

public class FireBomb extends AbstractGrenade {

    public FireBomb(Map map, int x, int y) {
        super(map, x, y, 10, 5000, 1, Color.CYAN, 100);
    }

    public String toString() {
        return "FIREBOMB: " + super.toString();
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
