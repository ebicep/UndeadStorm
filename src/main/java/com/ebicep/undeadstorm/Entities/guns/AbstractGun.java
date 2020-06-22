package com.ebicep.undeadstorm.Entities.guns;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Entities.Player;
import com.ebicep.undeadstorm.Entities.guns.types.*;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;
import com.ebicep.undeadstorm.MathUtil;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractGun extends Entity {

    protected Map map;
    protected int ammo;
    protected Color color;
    protected int DPS;
    protected int cooldown;

    private double maxAmmo;
    protected long lastShot = 0;

    private int normalDPS;

    protected ImageIcon basicPistolIcon = new ImageIcon(Map.class.getResource("/images/basicPistol.png"));
    protected ImageIcon burstSMGIcon = new ImageIcon(Map.class.getResource("/images/burstSMG.png"));
    protected ImageIcon desertEagleIcon = new ImageIcon(Map.class.getResource("/images/desertEagle.png"));
    protected ImageIcon machineGunIcon = new ImageIcon(Map.class.getResource("/images/machineGun.png"));
    protected ImageIcon shotgunIcon = new ImageIcon(Map.class.getResource("/images/shotgun.png"));
    protected ImageIcon sniperIcon = new ImageIcon(Map.class.getResource("/images/sniper.png"));


    public AbstractGun(Map map, int x, int y, int size, int ammo, Color color, int DPS, int cooldown) {
        super(x, y, size, 0, 0);
        this.map = map;
        this.ammo = ammo;
        this.color = color;
        this.DPS = DPS;
        this.cooldown = cooldown;
        maxAmmo = ammo;
        normalDPS = DPS;
    }

    public abstract void resetStats();

    public void resetToTheseStats(int dps, int cooldown) {
        this.DPS = dps;
        this.cooldown = cooldown;
    }

    @Override
    public void drawSelf(Graphics g) {
        if (this instanceof BasicPistol) {
            g.drawImage(basicPistolIcon.getImage(), (int) x, (int) y, basicPistolIcon.getIconWidth(), basicPistolIcon.getIconHeight(), null);
        } else if (this instanceof BurstSMG) {
            g.drawImage(burstSMGIcon.getImage(), (int) x, (int) y, burstSMGIcon.getIconWidth(), burstSMGIcon.getIconHeight(), null);
        } else if (this instanceof DesertEagle) {
            g.drawImage(desertEagleIcon.getImage(), (int) x, (int) y, desertEagleIcon.getIconWidth(), desertEagleIcon.getIconHeight(), null);
        } else if (this instanceof MachineGun) {
            g.drawImage(machineGunIcon.getImage(), (int) x, (int) y, machineGunIcon.getIconWidth(), machineGunIcon.getIconHeight(), null);
        } else if (this instanceof Shotgun) {
            g.drawImage(shotgunIcon.getImage(), (int) x, (int) y, shotgunIcon.getIconWidth(), shotgunIcon.getIconHeight(), null);
        } else if (this instanceof Sniper) {
            g.drawImage(sniperIcon.getImage(), (int) x, (int) y, sniperIcon.getIconWidth(), sniperIcon.getIconHeight(), null);

        }
    }

    @Override
    public boolean act() {
        Player p = Game.getMap().getPlayer();

        if (MathUtil.distance(p, this) <= (diam / 2f) + (p.getDiam() / 2f)) {
            if (p.getHotbarPosition() == 1)
                p.setGunslot1(this);
            else if (p.getHotbarPosition() == 2)
                p.setGunslot2(this);
            map.getUpgradeMenu().applyGunUpgrades();
            x = -100;
            y = -100;
            return true;
        }

        return false;
    }

    //fires a projectile from the gun given players position and velocities to fire at
    public abstract void fire(double x, double y, double xVel, double yVel);


    @Override
    public double getX() {
        return x + (diam / 2f);
    }

    @Override
    public double getY() {
        return y + (diam / 2f);
    }

    public int getDPS() {
        return DPS;
    }

    public void setDPS(int DPS) {
        this.DPS = DPS;
    }

    public void setNormalDPS(int normalDPS) {
        this.normalDPS = normalDPS;
    }

    public int getNormalDPS() {
        return normalDPS;
    }

    public void addAmmo() {
        ammo = (int) maxAmmo;
    }

    public int getAmmo() {
        return ammo;
    }

    public double getMaxAmmo() {
        return maxAmmo;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public ImageIcon getBasicPistolIcon() {
        return basicPistolIcon;
    }

    public ImageIcon getBurstSMGIcon() {
        return burstSMGIcon;
    }

    public ImageIcon getDesertEagleIcon() {
        return desertEagleIcon;
    }

    public ImageIcon getMachineGunIcon() {
        return machineGunIcon;
    }

    public ImageIcon getShotgunIcon() {
        return shotgunIcon;
    }

    public ImageIcon getSniperIcon() {
        return sniperIcon;
    }
}
