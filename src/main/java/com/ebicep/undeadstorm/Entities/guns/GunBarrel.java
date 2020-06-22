package com.ebicep.undeadstorm.Entities.guns;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Entities.Projectile;
import com.ebicep.undeadstorm.Entities.guns.types.*;
import com.ebicep.undeadstorm.Map;
import com.ebicep.undeadstorm.MathUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GunBarrel extends Entity {

    private Map map;
    private double health;
    private Rectangle hitbox;
    private ImageIcon gunBarrelIcon = new ImageIcon(Map.class.getResource("/images/gunBarrel.png"));

    public GunBarrel(Map map, double x, double y, int diam) {
        super(x, y, diam, 0, 0);
        health = 30;
        this.map = map;
        hitbox = new Rectangle((int) x, (int) y, diam, diam);
    }

    @Override
    public void drawSelf(Graphics g) {
        g.drawImage(gunBarrelIcon.getImage(), (int) x, (int) y, diam, diam, null);
        g.setColor(Color.white);
        g.drawString((int)health + "",(int)(x + diam/3 + 2),(int)(y + diam/2 + 3));
    }

    @Override
    public boolean act() {
        boolean playerIntersectsHorizontal = map.getPlayer().getBoundsHorizontal().intersects(hitbox);
        boolean playerIntersectsVertical = map.getPlayer().getBoundsVertical().intersects(hitbox);
        if (playerIntersectsHorizontal) {
            map.getPlayer().setX(map.getPlayer().getX() - map.getPlayer().getxVel());
        }
        if (playerIntersectsVertical) {
            map.getPlayer().setY(map.getPlayer().getY() - map.getPlayer().getyVel());
        }
        if (playerIntersectsHorizontal || playerIntersectsVertical) {
            if (map.getPlayer().isPressedF()) {
                health -= .5;
                if (health <= 0) {
                    int random = (int) (Math.random() * 25);
                    if (random < 6)
                        map.getEntities().add(new BasicPistol(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                    else if (random < 9)
                        map.getEntities().add(new BurstSMG(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                    else if (random < 12)
                        map.getEntities().add(new DesertEagle(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                    else if (random < 16)
                        map.getEntities().add(new MachineGun(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                    else if (random < 18)
                        map.getEntities().add(new Shotgun(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                    else if (random <= 20)
                        map.getEntities().add(new Sniper(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                    return true;
                }
            }
        }
        for (Entity e : new ArrayList<>(map.getEntities())) {
            if (e instanceof Projectile) {
                if (MathUtil.distance(this, e) <= ((double) this.getDiam() + e.getDiam()) / 2) {
                    map.getEntitiesToRemove().add(e);
                    this.damage(((Projectile) e).getDamage());
                    if (health <= 0) {
                        int random = (int) (Math.random() * 40);
                        if (random < 6)
                            map.getEntities().add(new BasicPistol(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                        else if (random < 9)
                            map.getEntities().add(new BurstSMG(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                        else if (random < 12)
                            map.getEntities().add(new DesertEagle(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                        else if (random < 16)
                            map.getEntities().add(new MachineGun(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                        else if (random < 18)
                            map.getEntities().add(new Shotgun(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                        else if (random <= 20)
                            map.getEntities().add(new Sniper(map, (int) (x + diam / 4), (int) (y + diam / 4)));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void damage(int damage) {
        this.health -= damage;
    }

}
