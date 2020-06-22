package com.ebicep.undeadstorm.Entities.zombies;

import com.ebicep.undeadstorm.Entities.Player;
import com.ebicep.undeadstorm.Map;

import javax.swing.*;
import java.awt.*;

public class Fire {

    private int x;
    private int y;
    private Rectangle hitbox;
    private ImageIcon fireIcon = new ImageIcon(Map.class.getResource("/images/fire.png"));

    public Fire(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(x, y, 60, 60);
    }

    public void drawSelf(Graphics g) {
        g.drawImage(fireIcon.getImage(), x, y, 60, 60, null);
    }

    public void act(Player p) {
        if (hitbox.intersects(new Rectangle(p.getHitBoxX(), p.getHitBoxY(), 60, 60)) && !p.isOnFire()) {
            p.setOnFire(true);
            p.setNextTickDamage(System.currentTimeMillis() + 1000);
            p.setTimeUntilFireEnds(System.currentTimeMillis() + 5000);
        }
    }

}
