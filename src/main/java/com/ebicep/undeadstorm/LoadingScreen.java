package com.ebicep.undeadstorm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class LoadingScreen {

    private int x;
    private int y;
    private ArrayList<Integer> ints;
    private int fillAmount;
    private int counter;

    private long dot1;
    private long dot2;
    private long dot3;
    private long dotDuration;

    private long drawName;
    private int nameToDrawCounter;
    private String[] classNames = new String[]{
            "grenades", "AbstractGrenade", "BlownUp", "Exploding", "IGrenadeState", "Resting", "Thrown", "FireBomb", "Frag", "Sticky", "Stun",
            "guns", "AbstractGun", "GunBarrel", "BasicPistol", "BurstSMG", "DesertEagle", "MachineGun", "Shotgun", "Sniper",
            "items", "AbstractItem", "ItemBarrel", "HealingItem", "InstaKillItem", "RefillAmmoItem", "SpeedItem",
            "zombies", "AbstractZombie", "Fire", "BasicZombie", "BossProjectile", "BossZombie", "DogZombie", "FatZombie", "MercyZombie",
            "Entity", "IPosition", "Player", "Projectile",
            "Dino", "DinoGame", "Obstacle", "Ball", "Paddle", "Pong",
            "Camera", "Game", "GameAudio", "Leaderboard", "Level", "LoadingScreen", "Map", "MathUtil", "UpgradeMenu", "User", "Wall"};

    private long drawEnterToContinue;

    public LoadingScreen() {
        x = 1500 / 2 - 100;
        y = 680;
        ints = new ArrayList<>();
        ints.add(20);
        ints.add(10);
        ints.add(30);
        ints.add(40);
        ints.add(10);
        ints.add(30);
        ints.add(60);
        Collections.shuffle(ints);
        fillAmount = 0;
        counter = 0;
        dot1 = System.currentTimeMillis() + 2000;
        dot2 = System.currentTimeMillis() + 3000;
        dot3 = System.currentTimeMillis() + 4000;
        dotDuration = System.currentTimeMillis() + 5000;
        nameToDrawCounter = 0;
        drawName = System.currentTimeMillis() + 1000;
        drawEnterToContinue = System.currentTimeMillis() + 10000;
    }

    public void drawSelf(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.drawRect(x, y, 200, 35);
        g2d.fillRect(x, y, fillAmount, 35);
        g2d.setColor(Color.gray);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
        g2d.drawString("CONTROLS -",50,650);
        g2d.drawString("Move = WASD",70,670);
        g2d.drawString("Click = Fire",70,690);
        g2d.drawString("Hit Barrel = F, next to barrel",70,710);
        g2d.drawString("Suicide = K",70,730);
        g2d.drawString("TIPS -",900,630);
        g2d.drawString("Brown Barrel = PowerUps",920,650);
        g2d.drawString("Gray Barrel = Guns",920,670);
        g2d.drawString("PowerUp Effectiveness =",920,690);
        g2d.drawString("Gives More Health",940,710);
        g2d.drawString("Longer duration",940,730);
        g2d.setColor(Color.white);
        g2d.drawString("Loading ", 690, y - 10);
        if (System.currentTimeMillis() < dotDuration) {
            if (System.currentTimeMillis() > dot1) {
                g2d.drawOval(775, y - 17, 6, 6);
            }
            if (System.currentTimeMillis() > dot2) {
                g2d.drawOval(790, y - 17, 6, 6);
            }
            if (System.currentTimeMillis() > dot3) {
                g2d.drawOval(805, y - 17, 6, 6);
            }
        } else {
            dotDuration = System.currentTimeMillis() + 5000;
            dot1 = System.currentTimeMillis() + 2000;
            dot2 = System.currentTimeMillis() + 3000;
            dot3 = System.currentTimeMillis() + 4000;
        }
        if (nameToDrawCounter < 57) {
            String className = classNames[nameToDrawCounter];
            String loading = "Loading Class: " + className;
            int stringWidth = g.getFontMetrics().stringWidth(loading);
            g.drawString(loading, 750 - stringWidth / 2, 735);
        } else {
            String done = "DONE";
            int stringWidth = g.getFontMetrics().stringWidth(done);
            g.drawString(done, 750 - stringWidth / 2, 740);
        }
        if (System.currentTimeMillis() > drawName) {
            nameToDrawCounter++;
            drawName = System.currentTimeMillis() + 1000;
        }
        if (System.currentTimeMillis() > drawEnterToContinue) {
            g2d.drawString("Press ENTER to continue OR play", 1155, 740);
        }
    }

    public void load() {
        fillAmount += ints.get(counter);
        counter++;
    }

    public void reset() {
        fillAmount = 0;
        counter = 0;
    }

}

