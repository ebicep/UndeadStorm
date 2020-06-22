package com.ebicep.undeadstorm.Entities.zombies;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Entities.Player;
import com.ebicep.undeadstorm.Entities.zombies.types.*;
import com.ebicep.undeadstorm.Game;
import com.ebicep.undeadstorm.Map;
import com.ebicep.undeadstorm.MathUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public abstract class AbstractZombie extends Entity {
    protected Map map;
    protected int health;
    protected int maxHealth;
    protected double speed;
    protected double normalSpeed;
    protected double speedModifier;
    protected int damage;
    private boolean takeStunDamage = true;
    private long stunned = -1;
    private boolean facePlayer = false;
    private double lastPlayerX;
    private double lastPlayerY;

    private boolean mercyZombieDead = false;

    private long lastAttack = -1;
    private long attackCooldown;
    private long specialAttackCooldown = System.currentTimeMillis() + 10000;
    private boolean specialAttack = true;
    private ArrayList<Fire> trail = new ArrayList<>();
    private long removeImage = -1;
    private ArrayList<BossProjectile> absorbedBullets = new ArrayList<>();
    private ArrayList<BossProjectile> absorbedBulletsToRemove = new ArrayList<>();

    private double lastX = -1;
    private double lastY = -1;

    protected ImageIcon basicZombieIcon = new ImageIcon(Map.class.getResource("/images/basicZombie.png"));
    protected ImageIcon dogZombieIcon = new ImageIcon(Map.class.getResource("/images/dogZombie.png"));
    protected ImageIcon fatZombieIcon = new ImageIcon(Map.class.getResource("/images/fatZombie.png"));
    protected ImageIcon mercyZombieIcon1 = new ImageIcon(Map.class.getResource("/images/mercyZombie1.png"));
    protected ImageIcon mercyZombieIcon2 = new ImageIcon(Map.class.getResource("/images/mercyZombie2.png"));
    protected ImageIcon bossZombieIcon = new ImageIcon(Map.class.getResource("/images/bossZombie.png"));

    protected ImageIcon basicZombieHitIcon = new ImageIcon(Map.class.getResource("/images/basicZombieHit.png"));
    protected ImageIcon dogZombieHitIcon = new ImageIcon(Map.class.getResource("/images/dogZombieHit.png"));
    protected ImageIcon fatZombieHitIcon = new ImageIcon(Map.class.getResource("/images/fatZombieHit.png"));
    protected ImageIcon mercyZombieHitIcon1 = new ImageIcon(Map.class.getResource("/images/mercyZombie1Hit.png"));
    protected ImageIcon mercyZombieHitIcon2 = new ImageIcon(Map.class.getResource("/images/mercyZombie2Hit.png"));
    protected ImageIcon bossZombieHitIcon = new ImageIcon(Map.class.getResource("/images/bossZombieHit.png"));

    protected ImageIcon stunnedIcon = new ImageIcon(Map.class.getResource("/images/stunnedStar.png"));

    protected long hitCooldown = -1;

    public AbstractZombie(Map map, int x, int y, int size, int health, double speed, double speedModifier, int attackCooldown, int damage) {
        super(x, y, size, 0, 0);

        this.map = map;
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.normalSpeed = speed;
        this.speedModifier = speedModifier;
        this.attackCooldown = attackCooldown;
        this.damage = damage;
    }

    public void drawSelf(Graphics g) {

        g.setColor(Color.gray);
        g.drawRect((int) x, (int) y + diam + 2, diam, 10);
        g.setColor(Color.black);
        g.fillRect((int) x + 1, (int) y + diam + 3, diam - 1, 9);
        g.setColor(Color.GREEN);
        if (health >= 0)
            g.fillRect((int) x + 1, (int) y + diam + 3, (int) ((diam * (health / (double) maxHealth)) - 1), 9);


//        Graphics2D g2d = (Graphics2D) g;
//        g.setColor(Color.RED);
//        g2d.fill(getBoundsHorizontal());
//        g.setColor(Color.blue);
//        g2d.fill(getBoundsVertical());

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        if (stunned == -1) {
            g2d.rotate(getMouseAngle(
                    (int) (map.getPlayer().getCenterX()),
                    (int) map.getPlayer().getCenterY()),
                    this.getCenterX(), this.getCenterY());
        } else {
            if (facePlayer) {
                lastPlayerX = map.getPlayer().getCenterX();
                lastPlayerY = map.getPlayer().getCenterY();
                facePlayer = false;
            }
            g2d.rotate(getMouseAngle(
                    (int) lastPlayerX,
                    (int) lastPlayerY),
                    this.getCenterX(), this.getCenterY());
            g2d.drawImage(stunnedIcon.getImage(), (int) x - 10, (int) y - 15, 30, 30, null);
            g2d.drawImage(stunnedIcon.getImage(), (int) x + diam, (int) y + diam, 30, 30, null);
            if (System.currentTimeMillis() > stunned)
                stunned = -1;
        }
        if (this instanceof BasicZombie) {
            if (System.currentTimeMillis() < hitCooldown) {
                g2d.drawImage(basicZombieHitIcon.getImage(), (int) x, (int) y, diam, diam, null);
            } else {
                g2d.drawImage(basicZombieIcon.getImage(), (int) x, (int) y, diam, diam, null);
            }
        } else if (this instanceof DogZombie) {
            if (System.currentTimeMillis() < hitCooldown) {
                g2d.drawImage(dogZombieHitIcon.getImage(), (int) x - 15, (int) y - 15, diam + 30, diam + 30, null);
            } else {
                g2d.drawImage(dogZombieIcon.getImage(), (int) x - 15, (int) y - 15, diam + 30, diam + 30, null);
            }
        } else if (this instanceof FatZombie) {
            if (System.currentTimeMillis() < hitCooldown) {
                g2d.drawImage(fatZombieHitIcon.getImage(), (int) x, (int) y, diam, diam, null);
            } else {
                g2d.drawImage(fatZombieIcon.getImage(), (int) x, (int) y, diam, diam, null);
            }
        } else if (this instanceof MercyZombie) {
            if (!mercyZombieDead) {
                if (System.currentTimeMillis() < hitCooldown) {
                    g2d.drawImage(mercyZombieHitIcon1.getImage(), (int) x, (int) y, diam, diam, null);
                } else {
                    g2d.drawImage(mercyZombieIcon1.getImage(), (int) x, (int) y, diam, diam, null);
                }
            } else {
                if (System.currentTimeMillis() < hitCooldown) {
                    g2d.drawImage(mercyZombieHitIcon2.getImage(), (int) x, (int) y, diam + 10, diam, null);
                } else {
                    g2d.drawImage(mercyZombieIcon2.getImage(), (int) x, (int) y, diam + 10, diam, null);
                }
            }
        } else if (this instanceof BossZombie) {
            if (System.currentTimeMillis() < hitCooldown) {
                g2d.drawImage(bossZombieHitIcon.getImage(), (int) x, (int) y, diam, diam, null);
            } else {
                g2d.drawImage(bossZombieIcon.getImage(), (int) x, (int) y, diam, diam, null);
            }
        }

        g2d.setTransform(old);


        if (specialAttack) {
            if (this instanceof BossZombie) {
                g.setColor(Color.red);
                int offset = 0;
                for (int i = 0; i < 10; i++) {
                    trail.add(new Fire((int) (getCenterX() + xVel * (140 + offset)), (int) (getCenterY() - yVel * (140 + offset))));
                    offset += 65;
                }
                removeImage = System.currentTimeMillis() + 4000;
                specialAttack = false;
            }
        }


        for (Fire f : trail) {
            f.drawSelf(g);
            f.act(map.getPlayer());
        }

        if (System.currentTimeMillis() > removeImage && removeImage != -1) {
            removeImage = -1;
            trail.clear();
        }

        for (BossProjectile projectile : absorbedBullets) {
            projectile.drawSelf(g);
        }
    }

    @Override
    public boolean act() {

        setHitBoxX((int) x);
        setHitBoxY((int) y);

        Player p = Game.getMap().getPlayer();

        double xPos = p.getCenterX() - diam / 2;
        double yPos = p.getCenterY() - diam / 2;
        double[] velocities = getVelocities((int) xPos, (int) yPos);
        xVel = velocities[0];
        yVel = velocities[1];

        if (this instanceof BossZombie) {
            for (BossProjectile projectile : new ArrayList<>(absorbedBullets)) {
                if (projectile.act()) {
                    absorbedBulletsToRemove.add(projectile);
                }
            }
        }
        absorbedBullets.removeAll(absorbedBulletsToRemove);
        if (!(this instanceof BossZombie)) {
            if (lastX == x) {
                if (yVel < 0) {
                    yVel = -1.5;
                } else {
                    yVel = 1.5;
                }
                if (xVel < 0) {
                    xVel = 0.001;
                } else {
                    xVel = -0.001;
                }


            }
            if (lastY == y) {
                if (xVel < 0) {
                    xVel = -1.5;
                } else {
                    xVel = 1.5;
                }
                if (yVel < 0) {
                    yVel = 0.001;
                } else {
                    yVel = -0.001;
                }
            }
        }

        lastX = x;
        lastY = y;

        if (distance(xPos, yPos, x, y) < 2000 && !(this instanceof BossZombie)) {
            this.x += xVel * speed;
            this.y += -1 * yVel * speed;
        }
        if (this instanceof BossZombie) {
            if (distance(xPos, yPos, x, y) < 1000) {
                this.x += xVel * speed;
                this.y += -1 * yVel * speed;
            }

            if (x <= 1650) {
                x = 1650;
            } else if (x >= 2700) {
                x = 2700;
            } else if (y >= 1500) {
                y = 1500;
            } else if (y <= 800) {
                y = 800;
            }
        }

        if (attacked(p) && System.currentTimeMillis() > lastAttack) {
            lastAttack = System.currentTimeMillis() + attackCooldown;
            p.addHealth(-damage);
        }


        if (this instanceof BossZombie) {
            if (System.currentTimeMillis() > specialAttackCooldown) {
                specialAttackCooldown = System.currentTimeMillis() + 8000;
                specialAttack = true;
            }
        }

        if (health <= 0 && this instanceof MercyZombie) {
            if (!mercyZombieDead) {
                setHealth(maxHealth);
                setSpeed(speed / 2);
            }
            mercyZombieDead = true;
        }
        return this.health <= 0 || getX() < 0 || getX() > 4000 || getY() < 0 || getY() > 3000;
    }

    public boolean attacked(Player p) {
        return MathUtil.distance(p, this) <= ((double) diam / 2) + ((double) p.getDiam() / 2);
    }

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));
    }

    public double[] getVelocities(int xPos, int yPos) {
        double[] output = new double[2];
        //first displaying what angle you are shooting at
        double angleRAD = Math.atan(Math.abs(yPos - 25 - this.getY()) / Math.abs((double) xPos - this.getX()));
        double angleDEG = (angleRAD * 180) / Math.PI;
        //fixing if angle is positive in different quadrants
        //2nd quad
        if (xPos < this.getX() && yPos < this.getY()) {
            angleDEG *= -1;
            angleDEG += 180;
        }
        //3rd and 4th
        if (yPos > this.getY()) {
            if (xPos < this.getX())
                angleDEG += 180;
            else
                angleDEG = 360 - angleDEG;
        }
        double xVel = (Math.cos(Math.toRadians(angleDEG)));
        double yVel = (Math.sin(Math.toRadians(angleDEG)));
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

    public void setHitCooldown(long hitCooldown) {
        this.hitCooldown = hitCooldown;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void damage(int damage) {
        if (damage != 1 && damage != -2 && System.currentTimeMillis() > stunned)
            Game.changeSoundEffect(3);
        if (damage == -2) {
            setSpeed(speedModifier);
        } else if (damage == 5) {
            setSpeed(0);
            if (takeStunDamage) {
                facePlayer = true;
                stunned = System.currentTimeMillis() + 3200;
                this.health -= 5;
                takeStunDamage = false;
            }
        } else if (this instanceof BossZombie) {
            if (damage == 20) {
                health -= 10;
                BossProjectile tempProjectile = new BossProjectile(map, getCenterX(), getCenterY(), 4, 5);
                tempProjectile.setxVel(xVel * 10);
                tempProjectile.setyVel(yVel * 10);
                absorbedBullets.add(tempProjectile);
            } else if (damage == 15) {
                health -= 7;
                double spaceBetweenBullets = 1;
                for (int i = 0; i < 4; i++) {
                    BossProjectile tempProjectile = new BossProjectile(map, getCenterX(), getCenterY(), 3, 4);
                    tempProjectile.setxVel(xVel * (10 + spaceBetweenBullets));
                    tempProjectile.setyVel(yVel * (10 + spaceBetweenBullets));
                    absorbedBullets.add(tempProjectile);
                    spaceBetweenBullets += .2;
                }
            } else if (damage == 60) {
                health -= 30;
                BossProjectile tempProjectile = new BossProjectile(map, getCenterX(), getCenterY(), 6, 15);
                tempProjectile.setxVel(xVel * 10);
                tempProjectile.setyVel(yVel * 10);
                absorbedBullets.add(tempProjectile);
            } else if (damage == 25) {
                health -= 13;
                BossProjectile tempProjectile = new BossProjectile(map, getCenterX(), getCenterY(), 5, 6);
                tempProjectile.setxVel(xVel * 10);
                tempProjectile.setyVel(yVel * 10);
                absorbedBullets.add(tempProjectile);
            } else if (damage == 18) {
                health -= 9;
                BossProjectile tempProjectile = new BossProjectile(map, getCenterX(), getCenterY(), 5, 4);
                tempProjectile.setxVel(xVel * 10);
                tempProjectile.setyVel(yVel * 10);
                absorbedBullets.add(tempProjectile);
            } else if (damage == 150) {
                health -= 75;
                BossProjectile tempProjectile = new BossProjectile(map, getCenterX(), getCenterY(), 7, 20);
                tempProjectile.setxVel(xVel * 10);
                tempProjectile.setyVel(yVel * 10);
                absorbedBullets.add(tempProjectile);
            }

            if (damage == -250) {
                if (map.getPlayer().getHotbarPosition() == 1)
                    this.health -= map.getPlayer().getGunslot1().getNormalDPS();
                else
                    this.health -= map.getPlayer().getGunslot2().getNormalDPS();
            }
        } else {
            this.health -= damage;
        }
    }

    public double getNormalSpeed() {
        return normalSpeed;
    }
}
