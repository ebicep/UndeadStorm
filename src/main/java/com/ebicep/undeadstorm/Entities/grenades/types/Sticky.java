package com.ebicep.undeadstorm.Entities.grenades.types;

import com.ebicep.undeadstorm.Entities.grenades.AbstractGrenade;
import com.ebicep.undeadstorm.Map;

import java.awt.*;

public class Sticky extends AbstractGrenade {
    public Sticky(Map map, int x, int y) {
        super(map, x, y, 20, 2500, 0, Color.GRAY, 300);
    }

    public String toString() {
        return "STICKY: " + super.toString();
    }

    @Override
    public void throwing(double x, double y, double xVel, double yVel) {
        if (numberOfGrenades > 0) {
            this.xVel = xVel / 2;
            this.yVel = yVel / 2;
            future = System.currentTimeMillis() + 5000;
        }
    }

    public int getDamage() {
        return -2;
    }
}
