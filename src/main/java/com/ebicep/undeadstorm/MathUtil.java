package com.ebicep.undeadstorm;

import com.ebicep.undeadstorm.Entities.IPosition;

/**
 * com.ebicep.UndeadStorm.MathUtil
 * Description:
 **/
public class MathUtil {

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }

    public static double distance(IPosition a, IPosition b) {
        return Math.sqrt((Math.pow(a.getCenterX() - b.getCenterX(), 2) + Math.pow(b.getCenterY() - a.getCenterY(), 2)));
    }

    public static double distance2(IPosition a, IPosition b) {
        return Math.sqrt((Math.pow(a.getX() - b.getX(), 2) + Math.pow(b.getY() - a.getY(), 2)));
    }
}
