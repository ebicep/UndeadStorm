package com.ebicep.undeadstorm.Entities;

import com.ebicep.undeadstorm.Entities.grenades.AbstractGrenade;
import com.ebicep.undeadstorm.Entities.guns.AbstractGun;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Player extends Entity implements MouseListener, KeyListener {
    private int health;
    private int maxHealth;
    private AbstractGun gunslot1;
    private AbstractGun gunslot2;
    private AbstractGrenade grenadeslot;
    private int hotbarPosition;
    private Map map;

    private int speed;

    private long timeToEndInstaKillItem;
    private long timeToEndSpeedItem;
    private int durationOfPowerup = 4000;
    private int healthToAdd = 30;

    private boolean onFire = false;
    private long timeUntilFireEnds = -1;
    private long nextTickDamage = -1;

    private boolean isDead = false;

    private boolean autoFire = false;

    public void setAutoFire(boolean autoFire) {
        this.autoFire = autoFire;
    }

    public boolean isAutoFire() {
        return autoFire;
    }

    private boolean playAutoFireSound = true;

    public void setPlayAutoFireSound(boolean playAutoFireSound) {
        this.playAutoFireSound = playAutoFireSound;
    }

    public Player(Map map, int xCoor, int yCoor, int health) {
        super(xCoor, yCoor, 50, 0, 0);
        this.health = health;
        this.maxHealth = health;
        this.map = map;
        hotbarPosition = 1;
        speed = 5;
    }

    public void drawSelf(Graphics g) {
        g.setColor(Color.GREEN);
        //g.fillOval((int) x, (int) y, diam, diam);
        g.setColor(Color.gray);
        g.drawRect((int) x, (int) y + diam + 2, diam, 11);
        g.setColor(Color.black);
        g.fillRect((int) x + 1, (int) y + diam + 3, diam - 1, 10);
        g.setColor(Color.GREEN);
        g.fillRect((int) x + 1, (int) y + diam + 3, (int) ((diam - 1) * (health / (double) maxHealth)), 6);
        if (gunslot1 != null) {
            if (map.getPlayer().getHotbarPosition() == 1) {
                g.setColor(Color.yellow);
                if (gunslot1.getAmmo() > 0)
                    g.fillRect((int) map.getPlayer().getX() + 1, (int) map.getPlayer().getY() + map.getPlayer().getDiam() + 1 + 8, (int) (map.getPlayer().getDiam() * (gunslot1.getAmmo() / gunslot1.getMaxAmmo())) - 1, 4);
                g.setColor(Color.gray);
                g.fillRect((int) map.getPlayer().getX() + 1, (int) map.getPlayer().getY() + map.getPlayer().getDiam() + 3 + 6, map.getPlayer().getDiam() - 1, 1);
            }
            gunslot1.drawSelf(g);
        }
        if (gunslot2 != null) {
            if (map.getPlayer().getHotbarPosition() == 2) {
                g.setColor(Color.yellow);
                if (gunslot2.getAmmo() > 0)
                    g.fillRect((int) map.getPlayer().getX() + 1, (int) map.getPlayer().getY() + map.getPlayer().getDiam() + 1 + 8, (int) (map.getPlayer().getDiam() * (gunslot2.getAmmo() / gunslot2.getMaxAmmo())) - 1, 4);
                g.setColor(Color.gray);
                g.fillRect((int) map.getPlayer().getX() + 1, (int) map.getPlayer().getY() + map.getPlayer().getDiam() + 3 + 6, map.getPlayer().getDiam() - 1, 1);
            }
            gunslot2.drawSelf(g);
        }
        if (grenadeslot != null) {
            grenadeslot.drawSelf(g);
        }
    }

    public boolean act() {
        setVelocityForCurrentKeysPressed();
        x += xVel;
        y += yVel;
        setHitBoxX((int) x);
        setHitBoxY((int) y);

        if (System.currentTimeMillis() > getTimeToEndInstaKillItem()) {
            if (getGunslot1() != null)
                getGunslot1().setDPS(getGunslot1().getNormalDPS());
            if (getGunslot2() != null)
                getGunslot2().setDPS(getGunslot2().getNormalDPS());
        }
        if (System.currentTimeMillis() > getTimeToEndSpeedItem()) {
            setSpeed(5);
        }

        if (onFire) {
            //take 5 damage for every second for 5 seconds
            if (timeUntilFireEnds != -1 && System.currentTimeMillis() < timeUntilFireEnds) {
                if (nextTickDamage != -1 && System.currentTimeMillis() > nextTickDamage) {
                    nextTickDamage = System.currentTimeMillis() + 1000;
                    health -= 5;
                    Game.changeSoundEffect(8);
                }
            }
        }
        if (System.currentTimeMillis() > timeUntilFireEnds)
            onFire = false;

        if (health <= 0)
            isDead = true;

        if (autoFire) {
            if (playAutoFireSound) {
                Game.changeSoundEffect(10);
                playAutoFireSound = false;
            }
            int mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX() - 210 - map.getOffsets()[0];
            int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY() - 170 - map.getOffsets()[1];
            double[] velocities = getVelocities(mouseX, mouseY);
            double xVel = velocities[0];
            double yVel = velocities[1];
            if (hotbarPosition == 1 && gunslot1 != null) {
                gunslot1.fire(this.getCenterX(), this.getCenterY(), xVel, yVel);
            } else if (hotbarPosition == 2 && gunslot2 != null) {
                gunslot2.fire(this.getCenterX(), this.getCenterY(), xVel, yVel);
            } else if (hotbarPosition == 3 && grenadeslot != null) {
                grenadeslot.throwing(this.getCenterX(), this.getCenterY(), xVel, yVel);
                grenadeslot = null;
            }
        }

        return false;
    }


    public int getHealth() {
        return health;
    }

    public void addHealth(int health) {
        if (health < 0)
            Game.changeSoundEffect(4);
        if (this.health + health > maxHealth)
            this.health = maxHealth;
        else
            this.health += health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public AbstractGun getGunslot1() {
        return gunslot1;
    }

    public void setGunslot1(AbstractGun gunslot1) {
        this.gunslot1 = gunslot1;
    }

    public AbstractGun getGunslot2() {
        return gunslot2;
    }

    public void setGunslot2(AbstractGun gunslot2) {
        this.gunslot2 = gunslot2;
    }

    public AbstractGrenade getGrenadeslot() {
        return grenadeslot;
    }

    public void setGrenadeslot(AbstractGrenade grenadeslot) {
        this.grenadeslot = grenadeslot;
    }

    public void setHotbarPosition(int hotbarPosition) {
        this.hotbarPosition = hotbarPosition;
    }

    public int getHotbarPosition() {
        return hotbarPosition;
    }

    public long getTimeToEndInstaKillItem() {
        return timeToEndInstaKillItem;
    }

    public void setTimeToEndInstaKillItem(long timeToEndInstaKillItem) {
        this.timeToEndInstaKillItem = timeToEndInstaKillItem;
    }

    public long getTimeToEndSpeedItem() {
        return timeToEndSpeedItem;
    }

    public void setTimeToEndSpeedItem(long timeToEndSpeedItem) {
        this.timeToEndSpeedItem = timeToEndSpeedItem;
    }

    public int getDurationOfPowerup() {
        return durationOfPowerup;
    }

    public void setDurationOfPowerup(int durationOfPowerup) {
        this.durationOfPowerup = durationOfPowerup;
    }

    public int getHealthToAdd() {
        return healthToAdd;
    }

    public void setHealthToAdd(int healthToAdd) {
        this.healthToAdd = healthToAdd;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    public boolean isOnFire() {
        return onFire;
    }

    public void setTimeUntilFireEnds(long timeUntilFireEnds) {
        this.timeUntilFireEnds = timeUntilFireEnds;
    }

    public void setNextTickDamage(long nextTickDamage) {
        this.nextTickDamage = nextTickDamage;
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //getting the angle to shoot at
        //player top left corner
        int xPos = e.getX();
        int yPos = e.getY();
        if (map.getGameCamera().userInCenter()) {
            xPos -= (int) (map.getGameCamera().getxOffset());
            yPos -= (int) (map.getGameCamera().getyOffset());
        } else if (!map.getGameCamera().isTranslateX() && map.getGameCamera().isTranslateY()) {
            yPos -= (int) (map.getGameCamera().getyOffset());
            //right wall
            if (x >= 3250)
                xPos += 2500;
        } else if (!map.getGameCamera().isTranslateY() && map.getGameCamera().isTranslateX()) {
            xPos -= (int) (map.getGameCamera().getxOffset());
            //bottom wall
            if (y >= 2250)
                yPos += 2250;
        } else {
            //bottom left
            if (x <= 750 && y >= 2250) {
                yPos += 2250;
            }
            //bottom right
            else if (x >= 3250) {
                if (y <= 375) {
                    xPos += 2500;
                } else {
                    xPos += 2500;
                    yPos += 2250;
                }
            }
        }

        double[] velocities = getVelocities(xPos, yPos);
        double xVel = velocities[0];
        double yVel = velocities[1];

        if (!autoFire) {
            //firing the projectile
            if (hotbarPosition == 1 && gunslot1 != null) {
                gunslot1.fire(this.getCenterX(), this.getCenterY(), xVel, yVel);
            } else if (hotbarPosition == 2 && gunslot2 != null) {
                gunslot2.fire(this.getCenterX(), this.getCenterY(), xVel, yVel);
            } else if (hotbarPosition == 3 && grenadeslot != null) {
                grenadeslot.throwing(this.getCenterX(), this.getCenterY(), xVel, yVel);
                grenadeslot = null;
            }
        }
    }

    public double[] getVelocities(int xPos, int yPos) {
        double[] output = new double[2];
        //first displaying what angle you are shooting at
        double angleRAD = Math.atan(Math.abs(yPos - 25 - this.getCenterY()) / Math.abs((double) xPos - this.getCenterX()));
        double angleDEG = (angleRAD * 180) / Math.PI;
        //fixing if angle is positive in different quadrants
        //2nd quad
        if (xPos < this.getCenterX() && yPos < this.getCenterY()) {
            angleDEG *= -1;
            angleDEG += 180;
        }
        //3rd and 4th
        if (yPos > this.getCenterY()) {
            if (xPos < this.getCenterX())
                angleDEG += 180;
            else
                angleDEG = 360 - angleDEG;
        }
        int bulletsVel = 10;
        double xVel = (bulletsVel * (Math.cos(Math.toRadians(angleDEG))));
        double yVel = (bulletsVel * (Math.sin(Math.toRadians(angleDEG))));
        output[0] = xVel;
        output[1] = yVel;

        return output;
    }

    public double getMouseAngle(int xPos, int yPos) {
        double output = 0;
        //first displaying what angle you are shooting at
        double angleRAD = Math.atan(Math.abs(yPos - 25 - this.getCenterY()) / Math.abs((double) xPos - this.getCenterX()));
        double angleDEG = (angleRAD * 180) / Math.PI;
        if (xPos < getCenterX() && yPos > getCenterY()) {
            angleDEG *= -1;
            angleDEG += 180;
        }
        if (yPos < getCenterY()) {
            if (xPos < getCenterX()) {
                angleDEG += 180;
            } else {
                angleDEG *= -1;
                angleDEG += 360;
            }
        }
        output = Math.toRadians(angleDEG);
        return output;
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


    @Override
    public void keyTyped(KeyEvent e) {

    }

    private boolean isPressedW = false;
    private boolean isPressedA = false;
    private boolean isPressedS = false;
    private boolean isPressedD = false;
    private boolean isPressedF = false;

    public boolean isPressedF() {
        return isPressedF;
    }

    private void setVelocityForCurrentKeysPressed() {
        yVel = 0;
        xVel = 0;

        if (isPressedW) yVel = -speed;
        if (isPressedA) xVel = -speed;
        if (isPressedS) yVel = speed;
        if (isPressedD) xVel = speed;

        if (isPressedW && isPressedS) yVel = 0;
        if (isPressedA && isPressedD) xVel = 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case 87:
                isPressedW = true;
                break;
            case 65:
                isPressedA = true;
                break;
            case 83:
                isPressedS = true;
                break;
            case 68:
                isPressedD = true;
                break;
            case 70:
                isPressedF = true;
                break;
        }


        //1
        if (key == 49)
            setHotbarPosition(1);
        //2
        if (key == 50)
            setHotbarPosition(2);
        //3
        if (key == 51)
            setHotbarPosition(3);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 87:
                isPressedW = false;
                break;
            case 65:
                isPressedA = false;
                break;
            case 83:
                isPressedS = false;
                break;
            case 68:
                isPressedD = false;
                break;
            case 70:
                isPressedF = false;
                break;
        }
    }

}
