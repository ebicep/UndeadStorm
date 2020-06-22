package com.ebicep.undeadstorm;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Entities.Player;
import com.ebicep.undeadstorm.Entities.grenades.AbstractGrenade;
import com.ebicep.undeadstorm.Entities.grenades.types.FireBomb;
import com.ebicep.undeadstorm.Entities.grenades.types.Frag;
import com.ebicep.undeadstorm.Entities.grenades.types.Sticky;
import com.ebicep.undeadstorm.Entities.grenades.types.Stun;
import com.ebicep.undeadstorm.Entities.guns.AbstractGun;
import com.ebicep.undeadstorm.Entities.guns.types.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Camera implements MouseListener {
    private double xOffset;
    private double yOffset;
    private boolean translateX;
    private boolean translateY;
    private Map map;

    private double x = -10;
    private double y = -10;

    private Rectangle easterEgg = new Rectangle(1488, 735, 10, 13);

    public Camera(Map map, double xOffset, double yOffset) {
        this.map = map;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        translateX = true;
        translateY = true;
    }

    public void drawSelf(Graphics g, double x, double y) {
        this.x = x;
        this.y = y;

        easterEgg.setLocation(1488 - (int) x, 735 - (int) y);

        g.setColor(Color.gray);
        g.fillRect(map.getWidth() / 80 * 35 - (int) x, map.getHeight() / 20 * 18 - (int) y, 80, 60);
        g.fillRect(map.getWidth() / 80 * 40 - (int) x, map.getHeight() / 20 * 18 - (int) y, 80, 60);
        g.fillRect(map.getWidth() / 80 * 45 - (int) x, map.getHeight() / 20 * 18 - (int) y, 80, 60);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6.5f));
        g2d.setColor(Color.black);
        g2d.drawRect(map.getWidth() / 80 * 35 - (int) x, map.getHeight() / 20 * 18 - (int) y, 80, 60);
        g2d.drawRect(map.getWidth() / 80 * 40 - (int) x, map.getHeight() / 20 * 18 - (int) y, 80, 60);
        g2d.drawRect(map.getWidth() / 80 * 45 - (int) x, map.getHeight() / 20 * 18 - (int) y, 80, 60);

        g2d.setColor(Color.YELLOW);
        if (map.getPlayer().getHotbarPosition() == 1)
            g2d.drawRect(map.getWidth() / 80 * 35 - (int) x, map.getHeight() / 20 * 18 - (int) y, 80, 60);
        else if (map.getPlayer().getHotbarPosition() == 2)
            g2d.drawRect(map.getWidth() / 80 * 40 - (int) x, map.getHeight() / 20 * 18 - (int) y, 80, 60);
        else if (map.getPlayer().getHotbarPosition() == 3)
            g2d.drawRect(map.getWidth() / 80 * 45 - (int) x, map.getHeight() / 20 * 18 - (int) y, 80, 60);
        g2d.setStroke(new BasicStroke(1));

        if(map.getPlayer().getGunslot1() != null && map.getPlayer().getGunslot1().getAmmo() == 0) {
            if(map.getPlayer().getGunslot2() == null || map.getPlayer().getGunslot2().getAmmo() == 0) {
                g2d.setColor(Color.white);
                g2d.fillRect(map.getWidth() - 170 - (int)x, map.getHeight() - 740 - (int) y, 160, 40);
                g2d.setColor(Color.black);
                g2d.setFont(new Font("Helvetica", Font.BOLD, 13));
                g2d.drawString("You have no ammo!", map.getWidth() - 163 - (int)x,map.getHeight() - 723 - (int) y);
                g2d.drawString("Press F next to a barrel", map.getWidth() - 163 - (int)x,map.getHeight() - 707 - (int) y);

            }
        }

        //easter egg :)
        g2d.drawString(":)", 1490 - (int) x, 745 - (int) y);
        drawGuns(g);
    }

    public void center(Entity e) {
        xOffset = -e.getX() + (double) 1500 / 2;
        yOffset = -e.getY() + (double) 750 / 2;
    }

    public void drawGuns(Graphics g) {
        Player p = map.getPlayer();
        if (p.getGunslot1() != null) {
            AbstractGun slot1 = p.getGunslot1();
            if (slot1 instanceof BasicPistol)
                g.drawImage(slot1.getBasicPistolIcon().getImage(), map.getWidth() / 80 * 35 - (int) x, map.getHeight() / 20 * 18 - (int) y, slot1.getBasicPistolIcon().getIconWidth() * 2, slot1.getBasicPistolIcon().getIconHeight() * 2, null);
            else if (slot1 instanceof BurstSMG)
                g.drawImage(slot1.getBurstSMGIcon().getImage(), map.getWidth() / 80 * 35 - (int) x, map.getHeight() / 20 * 18 - (int) y, (int) (slot1.getBurstSMGIcon().getIconWidth() * 1.45), (int) (slot1.getBurstSMGIcon().getIconHeight() * 1.3), null);
            else if (slot1 instanceof DesertEagle)
                g.drawImage(slot1.getDesertEagleIcon().getImage(), map.getWidth() / 80 * 35 - (int) x, map.getHeight() / 20 * 18 - (int) y, (int) (slot1.getDesertEagleIcon().getIconWidth() * 1.3), (int) (slot1.getDesertEagleIcon().getIconHeight() * 1.5), null);
            else if (slot1 instanceof MachineGun)
                g.drawImage(slot1.getMachineGunIcon().getImage(), map.getWidth() / 80 * 35 - (int) x, map.getHeight() / 20 * 18 - (int) y + 9, (int) (slot1.getMachineGunIcon().getIconWidth() * .65), (int) (slot1.getMachineGunIcon().getIconHeight() * 1.4), null);
            else if (slot1 instanceof Shotgun)
                g.drawImage(slot1.getShotgunIcon().getImage(), map.getWidth() / 80 * 35 - (int) x, map.getHeight() / 20 * 18 - (int) y + 10, (int) (slot1.getShotgunIcon().getIconWidth() * .97), (int) (slot1.getShotgunIcon().getIconHeight() * 1.25), null);
            else if (slot1 instanceof Sniper)
                g.drawImage(slot1.getSniperIcon().getImage(), map.getWidth() / 80 * 35 - (int) x + 2, map.getHeight() / 20 * 18 - (int) y + 3, (int) (slot1.getSniperIcon().getIconWidth() * .465), (int) (slot1.getSniperIcon().getIconHeight() * .85), null);
        }
        if (p.getGunslot2() != null) {
            AbstractGun slot2 = p.getGunslot2();
            if (slot2 instanceof BasicPistol)
                g.drawImage(slot2.getBasicPistolIcon().getImage(), map.getWidth() / 80 * 40 - (int) x, map.getHeight() / 20 * 18 - (int) y, slot2.getBasicPistolIcon().getIconWidth() * 2, slot2.getBasicPistolIcon().getIconHeight() * 2, null);
            else if (slot2 instanceof BurstSMG)
                g.drawImage(slot2.getBurstSMGIcon().getImage(), map.getWidth() / 80 * 40 - (int) x, map.getHeight() / 20 * 18 - (int) y, (int) (slot2.getBurstSMGIcon().getIconWidth() * 1.45), (int) (slot2.getBurstSMGIcon().getIconHeight() * 1.3), null);
            else if (slot2 instanceof DesertEagle)
                g.drawImage(slot2.getDesertEagleIcon().getImage(), map.getWidth() / 80 * 40 - (int) x, map.getHeight() / 20 * 18 - (int) y, (int) (slot2.getDesertEagleIcon().getIconWidth() * 1.3), (int) (slot2.getDesertEagleIcon().getIconHeight() * 1.5), null);
            else if (slot2 instanceof MachineGun)
                g.drawImage(slot2.getMachineGunIcon().getImage(), map.getWidth() / 80 * 40 - (int) x, map.getHeight() / 20 * 18 - (int) y + 9, (int) (slot2.getMachineGunIcon().getIconWidth() * .65), (int) (slot2.getMachineGunIcon().getIconHeight() * 1.4), null);
            else if (slot2 instanceof Shotgun)
                g.drawImage(slot2.getShotgunIcon().getImage(), map.getWidth() / 80 * 40 - (int) x, map.getHeight() / 20 * 18 - (int) y + 10, (int) (slot2.getShotgunIcon().getIconWidth() * .97), (int) (slot2.getShotgunIcon().getIconHeight() * 1.25), null);
            else if (slot2 instanceof Sniper)
                g.drawImage(slot2.getSniperIcon().getImage(), map.getWidth() / 80 * 40 - (int) x + 2, map.getHeight() / 20 * 18 - (int) y + 3, (int) (slot2.getSniperIcon().getIconWidth() * .465), (int) (slot2.getSniperIcon().getIconHeight() * .85), null);
        }
        if (p.getGrenadeslot() != null) {
            AbstractGrenade slot3 = p.getGrenadeslot();
            if (slot3 instanceof FireBomb) {
                g.drawImage(slot3.getFireBombIcon().getImage(), map.getWidth() / 80 * 45 - (int) x + 11, map.getHeight() / 20 * 18 - (int) y + 2, (int) (slot3.getFireBombIcon().getIconWidth() * .6), (int) (slot3.getFireBombIcon().getIconHeight() * .45), null);
            } else if (slot3 instanceof Frag) {
                g.drawImage(slot3.getFragIcon().getImage(), map.getWidth() / 80 * 45 - (int) x + 11, map.getHeight() / 20 * 18 - (int) y + 2, (int) (slot3.getFragIcon().getIconWidth() * .5), (int) (slot3.getFragIcon().getIconHeight() * .47), null);
            } else if (slot3 instanceof Sticky) {
                g.drawImage(slot3.getStickyIcon().getImage(), map.getWidth() / 80 * 45 - (int) x + 14, map.getHeight() / 20 * 18 - (int) y + 2, (int) (slot3.getStickyIcon().getIconWidth() * .6), (int) (slot3.getStickyIcon().getIconHeight() * .48), null);
            } else if (slot3 instanceof Stun) {
                g.drawImage(slot3.getStunIcon().getImage(), map.getWidth() / 80 * 45 - (int) x + 14, map.getHeight() / 20 * 18 - (int) y + 2, (int) (slot3.getStunIcon().getIconWidth() * .6), (int) (slot3.getStunIcon().getIconHeight() * .45), null);

            }
        }

    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public boolean isTranslateX() {
        return translateX;
    }

    public void setTranslateX(boolean translateX) {
        this.translateX = translateX;
    }

    public boolean isTranslateY() {
        return translateY;
    }

    public void setTranslateY(boolean translateY) {
        this.translateY = translateY;
    }

    public boolean userInCenter() {
        return translateX && translateY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (easterEgg.contains((int) MouseInfo.getPointerInfo().getLocation().getX() - 210 - map.getOffsets()[0], (int) MouseInfo.getPointerInfo().getLocation().getY() - 170 - map.getOffsets()[1])) {
            map.getPlayer().setAutoFire(!map.getPlayer().isAutoFire());
            map.getPlayer().setPlayAutoFireSound(true);
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
