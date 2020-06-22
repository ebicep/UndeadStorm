package com.ebicep.undeadstorm.Entities.items;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Entities.Player;
import com.ebicep.undeadstorm.Entities.items.types.HealingItem;
import com.ebicep.undeadstorm.Entities.items.types.InstaKillItem;
import com.ebicep.undeadstorm.Entities.items.types.RefillAmmoItem;
import com.ebicep.undeadstorm.Entities.items.types.SpeedItem;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;
import com.ebicep.undeadstorm.MathUtil;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractItem extends Entity {
    private Map map;
    protected ImageIcon healingIcon = new ImageIcon(Map.class.getResource("/images/healthPack.png"));
    protected ImageIcon instaKillIcon = new ImageIcon(Map.class.getResource("/images/instakill.png"));
    protected ImageIcon ammoIcon = new ImageIcon(Map.class.getResource("/images/ammoBox.png"));
    protected ImageIcon speedIcon = new ImageIcon(Map.class.getResource("/images/energybar.png"));

    public AbstractItem(Map map, double x, double y, int diam) {
        super(x, y, diam, 0, 0);
        this.map = map;
    }


    public void drawSelf(Graphics g) {
        if (this instanceof HealingItem) {
            g.drawImage(healingIcon.getImage(), (int) x, (int) y, diam, diam, null);
        } else if (this instanceof InstaKillItem) {
            g.drawImage(instaKillIcon.getImage(), (int) x, (int) y, diam, diam, null);
        } else if (this instanceof RefillAmmoItem) {
            g.drawImage(ammoIcon.getImage(), (int) x, (int) y, diam, diam, null);
        } else if (this instanceof SpeedItem) {
            g.drawImage(speedIcon.getImage(), (int) x, (int) y, diam, diam, null);
        }
    }

    public boolean act() {
        Player p = map.getPlayer();
        if (MathUtil.distance(p, this) <= ((double) p.getDiam() + diam) / 2) {
            Game.changeSoundEffect(9);
            if (this instanceof HealingItem) {
                p.addHealth(p.getHealthToAdd());
            } else if (this instanceof RefillAmmoItem) {
                p.getGunslot1().addAmmo();
            } else if (this instanceof InstaKillItem) {
                if (p.getGunslot1() != null)
                    p.getGunslot1().setDPS(250);
                if (p.getGunslot2() != null)
                    p.getGunslot2().setDPS(250);
                p.setTimeToEndInstaKillItem(System.currentTimeMillis() + p.getDurationOfPowerup());
            } else if (this instanceof SpeedItem) {
                p.setSpeed(8);
                p.setTimeToEndSpeedItem(System.currentTimeMillis() + p.getDurationOfPowerup());
            }
            return true;
        }
        return false;
    }

}
