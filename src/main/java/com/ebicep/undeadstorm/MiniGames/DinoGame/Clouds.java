package com.ebicep.undeadstorm.MiniGames.DinoGame;

import javax.swing.*;
import java.awt.*;

public class Clouds {
    private int x;
    private int y;
    private double xVel;
    private int typeOfCloud;
    private ImageIcon cloud1Icon = new ImageIcon(Clouds.class.getResource("/images/cloud1.png"));
    private ImageIcon cloud2Icon = new ImageIcon(Clouds.class.getResource("/images/cloud2.png"));
    private ImageIcon cloud3Icon = new ImageIcon(Clouds.class.getResource("/images/cloud3.png"));

    public Clouds(int x, int y) {
        this.x = x;
        this.y = y;
        xVel = -7;
        typeOfCloud = (int)(Math.random() * 3);
    }

    public void drawSelf(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(typeOfCloud == 0) {
            g2d.drawImage(cloud1Icon.getImage(), x, y, cloud1Icon.getIconWidth(), cloud1Icon.getIconHeight(), null);
        }
        else if(typeOfCloud == 1) {
            g2d.drawImage(cloud2Icon.getImage(), x, y, cloud2Icon.getIconWidth(), cloud2Icon.getIconHeight(), null);
        }
        else if(typeOfCloud == 2) {
            g2d.drawImage(cloud3Icon.getImage(), x, y, cloud3Icon.getIconWidth(), cloud3Icon.getIconHeight(), null);
        }
    }

    public boolean act() {
        x += xVel;
        return x <= -10;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }
}
