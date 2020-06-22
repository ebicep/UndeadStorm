package com.ebicep.undeadstorm.MiniGames.DinoGame;

import java.awt.*;
import java.util.ArrayList;

public class DinoGame {

    private Dino player;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Obstacle> obstaclesToRemove;
    private ArrayList<Clouds> clouds;
    private ArrayList<Clouds> cloudsToRemove;
    private long timeToAddObstacle;
    private int score;
    private int highestScore;
    private double newxVel;
    private double newxVel2;

    public DinoGame() {
        player = new Dino();
        obstacles = new ArrayList<>();
        obstaclesToRemove = new ArrayList<>();
        clouds = new ArrayList<>();
        cloudsToRemove = new ArrayList<>();
        addObstacles();
        timeToAddObstacle = System.currentTimeMillis();
        score = 0;
        highestScore = 0;
        newxVel = -10;
        newxVel2 = -7;
    }

    public void drawSelf(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 425, 1500, 500);
        g.setFont(new Font("Helvetica", Font.BOLD, 50));
        String scoreString = "Score: " + score;
        String highestscoreString = "Highest Score: " + highestScore;
        int stringWidth = g.getFontMetrics().stringWidth(scoreString);
        int stringWidth2 = g.getFontMetrics().stringWidth(highestscoreString);
        g.drawString(scoreString, 750 - stringWidth / 2, 100);
        g.drawString(highestscoreString, 750 - stringWidth2 / 2, 50);
        for (Obstacle obstacle : obstacles) {
            obstacle.drawSelf(g);
        }
        for(Clouds cloud : clouds) {
            cloud.drawSelf(g);
        }
        player.drawSelf(g);
    }

    public void act() {
        player.act();
        score++;
        if (score > highestScore)
            highestScore = score;
        if (System.currentTimeMillis() > timeToAddObstacle) {
            clouds.add(new Clouds(1600,100 + (int)(Math.random() * 200 - 100)));
            addObstacles();
            timeToAddObstacle = System.currentTimeMillis() + 1000;
        }
        for (Obstacle obstacle : obstacles) {
            obstacle.setxVel(newxVel);
            if (obstacle.act()) {
                newxVel -= 0.25;
                obstaclesToRemove.add(obstacle);
            }
            if (isCollision(obstacle)) {
                reset();
                score = 0;
                newxVel = -10;
            }
        }
        for (Clouds cloud : clouds) {
            cloud.setxVel(newxVel2);
            if (cloud.act()) {
                newxVel2 -= 0.10;
                cloudsToRemove.add(cloud);
            }
        }
        obstacles.removeAll(obstaclesToRemove);
        clouds.removeAll(cloudsToRemove);
    }

    private void reset() {
        obstaclesToRemove.addAll(obstacles);
        player.setY(200);
        newxVel2 = -7;
    }

    public void addObstacles() {
        int numberOfObstacles = (int) (Math.random() * 4);
        for (int i = 0; i < numberOfObstacles; i++) {
            obstacles.add(new Obstacle(1500 + (i * 50), 425));
        }
    }
    private boolean isCollision(Obstacle obstacle) {
        return player.getHitbox().intersects(obstacle.getHitbox());
    }

    public Dino getPlayer() {
        return player;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
}
