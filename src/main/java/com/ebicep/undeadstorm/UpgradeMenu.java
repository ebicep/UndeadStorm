package com.ebicep.undeadstorm;

import com.ebicep.undeadstorm.Entities.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UpgradeMenu implements MouseListener {

    private Map map;
    private int upgradesLeft;
    private boolean doneUpgrading;
    private int maxHealth;
    private int firerate;
    private int gunDamage;
    private int powerupEffect;

    private Rectangle healthSubtract = new Rectangle(75, 410, 40, 30);
    private Rectangle healthAdd = new Rectangle(295, 393, 50, 50);
    private Rectangle firerateSubtract = new Rectangle(75, 650, 40, 30);
    private Rectangle firerateAdd = new Rectangle(295, 635, 50, 50);
    private Rectangle gunDamageSubtract = new Rectangle(1115, 413, 40, 30);
    private Rectangle gunDamageAdd = new Rectangle(1335, 395, 50, 50);
    private Rectangle powerupEffectSubtract = new Rectangle(1115, 650, 40, 30);
    private Rectangle powerupEffectAdd = new Rectangle(1335, 635, 50, 50);

    private ImageIcon upgradeIcon = new ImageIcon(Map.class.getResource("/images/upgradeMenu.png"));

    public UpgradeMenu(Map map) {
        this.map = map;
        upgradesLeft = 5;
        doneUpgrading = false;
        maxHealth = 0;
        firerate = 0;
        gunDamage = 0;
        powerupEffect = 0;
    }

    public void drawSelf(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(upgradeIcon.getImage(), 0, 0, 1500, 750, null);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Helvetica", Font.BOLD, 60));
        g2d.drawString(maxHealth + "", 190, 435);
        g2d.drawString(firerate + "", 190, 685);
        g2d.drawString(gunDamage + "", 1235, 445);
        g2d.drawString(powerupEffect + "", 1235, 680);
        g2d.setFont(new Font("Helvetica", Font.BOLD, 75));
        if (upgradesLeft < 10)
            g2d.drawString("Points - " + upgradesLeft, 1110, 100);
        else
            g2d.drawString("Points - " + upgradesLeft, 1100, 100);
    }

    public void act() {
        doneUpgrading = upgradesLeft <= 0;
    }

    public void resetUpgrades() {
        upgradesLeft = 5;
    }

    public void applyUpgrades() {
        Player p = map.getPlayer();
        p.setMaxHealth((int) (p.getMaxHealth() + (2.5 * maxHealth)));
        p.setDurationOfPowerup(p.getDurationOfPowerup() + (500 * powerupEffect));
        p.setHealthToAdd(p.getHealthToAdd() + 10);
    }

    public void applyGunUpgrades() {
        Player p = map.getPlayer();
        if (p.getGunslot1() != null) {
            p.getGunslot1().resetStats();
            p.getGunslot1().setCooldown((int) (p.getGunslot1().getCooldown() * (1 - (.025 * firerate))));
            p.getGunslot1().setNormalDPS((int) (p.getGunslot1().getNormalDPS() + (1.5 * gunDamage)));
        }
        if (p.getGunslot2() != null) {
            p.getGunslot2().resetStats();
            p.getGunslot2().setCooldown((int) (p.getGunslot2().getCooldown() * (1 - (.025 * firerate))));
            p.getGunslot2().setNormalDPS((int) (p.getGunslot2().getNormalDPS() + (1.5 * gunDamage)));
        }
    }

    public boolean isDoneUpgrading() {
        return doneUpgrading;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            int x = (int) MouseInfo.getPointerInfo().getLocation().getX() - 210;
            int y = (int) MouseInfo.getPointerInfo().getLocation().getY() - 170;
            if (upgradesLeft > 0) {
                if (healthSubtract.contains(x, y)) {
                    if (maxHealth > 0) {
                        maxHealth--;
                        upgradesLeft++;
                    }
                } else if (firerateSubtract.contains(x, y)) {
                    if (firerate > 0) {
                        firerate--;
                        upgradesLeft++;
                    }
                } else if (gunDamageSubtract.contains(x, y)) {
                    if (gunDamage > 0) {
                        gunDamage--;
                        upgradesLeft++;
                    }
                } else if (powerupEffectSubtract.contains(x, y)) {
                    if (powerupEffect > 0) {
                        powerupEffect--;
                        upgradesLeft++;
                    }
                } else if (healthAdd.contains(x, y)) {
                    maxHealth++;
                    upgradesLeft--;
                } else if (firerateAdd.contains(x, y)) {
                    firerate++;
                    upgradesLeft--;
                } else if (gunDamageAdd.contains(x, y)) {
                    gunDamage++;
                    upgradesLeft--;
                } else if (powerupEffectAdd.contains(x, y)) {
                    powerupEffect++;
                    upgradesLeft--;
                }
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
