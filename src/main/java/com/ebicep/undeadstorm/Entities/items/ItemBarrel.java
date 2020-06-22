package com.ebicep.undeadstorm.Entities.items;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Entities.Projectile;
import com.ebicep.undeadstorm.Entities.items.types.HealingItem;
import com.ebicep.undeadstorm.Entities.items.types.InstaKillItem;
import com.ebicep.undeadstorm.Entities.items.types.RefillAmmoItem;
import com.ebicep.undeadstorm.Entities.items.types.SpeedItem;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;
import com.ebicep.undeadstorm.MathUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ItemBarrel extends Entity {

    private Map map;
    private double health;
    private Rectangle hitbox;
    private ImageIcon itemBarrelIcon = new ImageIcon(Map.class.getResource("/images/itemBarrel.png"));

    public ItemBarrel(Map map, double x, double y, int diam) {
        super(x, y, diam, 0, 0);
        health = 30;
        this.map = map;
        hitbox = new Rectangle((int) x, (int) y, diam, diam);
    }

    @Override
    public void drawSelf(Graphics g) {
        g.drawImage(itemBarrelIcon.getImage(), (int) x, (int) y, diam, diam, null);
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
                    int random = (int) (Math.random() * 6);
                    if (random == 0)
                        map.getEntities().add(new HealingItem(map, x + diam / 4, y + diam / 4, diam / 2));
                    else if (random == 1)
                        map.getEntities().add(new InstaKillItem(map, x + diam / 4, y + diam / 4, diam / 2));
                    else if (random == 2)
                        map.getEntities().add(new RefillAmmoItem(map, x + diam / 4, y + diam / 4, diam / 2));
                    else if (random == 3)
                        map.getEntities().add(new SpeedItem(map, x + diam / 4, y + diam / 4, diam / 2));
                    return true;
                }
            }
        }
        for (Entity e : new ArrayList<>(Game.getMap().getEntities())) {
            if (e instanceof Projectile) {
                if (MathUtil.distance(this, e) <= ((double) this.getDiam() + e.getDiam()) / 2) {
                    map.getEntitiesToRemove().add(e);
                    this.damage(((Projectile) e).getDamage());
                    if (health <= 0) {
                        int random = (int) (Math.random() * 8);
                        if (random == 0)
                            map.getEntities().add(new HealingItem(map, x + diam / 4, y + diam / 4, diam / 2));
                        else if (random == 1)
                            map.getEntities().add(new InstaKillItem(map, x + diam / 4, y + diam / 4, diam / 2));
                        else if (random == 2)
                            map.getEntities().add(new RefillAmmoItem(map, x + diam / 4, y + diam / 4, diam / 2));
                        else if (random == 3)
                            map.getEntities().add(new SpeedItem(map, x + diam / 4, y + diam / 4, diam / 2));
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
