package com.ebicep.undeadstorm.MiniGames.PongGame;

import java.awt.*;
import java.util.ArrayList;

public class Pong {

    private Paddle botPaddle;
    private Paddle playerPaddle;
    private ArrayList<Ball> gameBalls;

    private int botPoints;
    private int playerPoints;
    private String score;

    private long future;

    public Pong() {
        botPaddle = new Paddle(100, 375);
        playerPaddle = new Paddle(1400, 375);
        gameBalls = new ArrayList<>();
        gameBalls.add(new Ball());
        botPoints = 0;
        playerPoints = 0;
        future = System.currentTimeMillis() + 5000;
    }

    public void act() {
        for (Ball b : gameBalls) {
            b.act();
        }
        if (System.currentTimeMillis() > future) {
            gameBalls.add(new Ball());
            future = System.currentTimeMillis() + 5000;
        }
        moveBotPaddle();
        movePaddle();
        collision();
        updateScore();
    }

    public void drawSelf(Graphics g) {
        botPaddle.drawSelf(g);
        playerPaddle.drawSelf(g);
        for (Ball b : gameBalls) {
            b.drawSelf(g);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 50));
        score = botPoints + " - " + playerPoints;
        int stringWidth = g.getFontMetrics().stringWidth(score);
        g.drawString(score, 750 - stringWidth / 2, 50);
    }

    public void movePaddle() {
        int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY() - 170;
        if (mouseY <= 50)
            playerPaddle.setY(0);
        else if (mouseY >= 700)
            playerPaddle.setY(650);
        else
            playerPaddle.setY(mouseY - 50);
    }

    public void moveBotPaddle() {
        for (Ball b : gameBalls) {
            if (b.getX() <= 130) {
            /* BAD AI
            if (b.getY() + b.getDiam() / 2 <= botPaddle.getY() + 50) {
                botPaddle.setY((botPaddle.getY() - 15));
            } else {
                botPaddle.setY((botPaddle.getY() + 15));
            }
             */
                // GOOD AI
                botPaddle.setY(b.getY() + b.getDiam() / 2 - 50);
                if (botPaddle.getY() <= 0)
                    botPaddle.setY(0);
                else if (botPaddle.getY() + botPaddle.getHeight() >= 750)
                    botPaddle.setY(650);
            }
        }
    }

    public void collision() {
        for (Ball b : gameBalls) {
            if (b.getY() <= 0 || b.getY() + b.getDiam() >= 750) {
                b.setyVel(b.getyVel() * -1);
            } else if (b.getX() <= 130 && b.getX() > 80) {
                if (b.getY() >= botPaddle.getY() - 5 && b.getY() + b.getDiam() <= botPaddle.getY() + botPaddle.getHeight() + 5) {
                    b.setxVel(b.getxVel() * -1.1);
                    b.setyVel(b.getyVel() * -1.1);
                }
            } else if (b.getX() + b.getDiam() >= 1390 && b.getX() + b.getDiam() < 1440) {
                if (b.getY() >= playerPaddle.getY() - 5 && b.getY() + b.getDiam() <= playerPaddle.getY() + playerPaddle.getHeight() + 5) {
                    b.setxVel(b.getxVel() * -1.1);
                    b.setyVel(b.getyVel() * -1.1);
                }
            }
        }
    }

    public void updateScore() {
        for (Ball b : gameBalls) {
            if (b.getX() <= -5) {
                playerPoints++;
                b.reset();
            } else if (b.getX() + b.getDiam() / 2 >= 1505) {
                botPoints++;
                b.reset();
            }
        }
    }
}
