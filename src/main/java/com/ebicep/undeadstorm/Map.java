package com.ebicep.undeadstorm;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Entities.Player;
import com.ebicep.undeadstorm.Entities.Projectile;
import com.ebicep.undeadstorm.Entities.grenades.AbstractGrenade;
import com.ebicep.undeadstorm.Entities.grenades.types.FireBomb;
import com.ebicep.undeadstorm.Entities.grenades.types.Frag;
import com.ebicep.undeadstorm.Entities.grenades.types.Sticky;
import com.ebicep.undeadstorm.Entities.grenades.types.Stun;
import com.ebicep.undeadstorm.Entities.guns.GunBarrel;
import com.ebicep.undeadstorm.Entities.guns.types.BasicPistol;
import com.ebicep.undeadstorm.Entities.items.ItemBarrel;
import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;
import com.ebicep.undeadstorm.Entities.zombies.types.BasicZombie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Scanner;


public class Map implements KeyListener, MouseListener, MouseMotionListener {
    private final int width;
    private final int height;
    private int difficultyLevel;

    private ArrayList<Entity> entities;
    private ArrayList<Entity> entitiesToRemove;
    private int totalKills;

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Entity> getEntitiesToRemove() {
        return entitiesToRemove;
    }

    private ArrayList<Wall> walls;

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    private Camera gameCamera;

    public Camera getGameCamera() {
        return gameCamera;
    }

    private String[][] gridData = new String[375][500];

    private ImageIcon mapIcon = new ImageIcon(getClass().getResource("/images/map.jpg"));
    private ImageIcon lowHealthIcon = new ImageIcon(getClass().getResource("/images/lowHealth.png"));
    private ImageIcon playerIcon = new ImageIcon(getClass().getResource("/images/playerSmallGun.png"));
    private ImageIcon player2Icon = new ImageIcon(getClass().getResource("/images/playerBigGun.png"));
    private ImageIcon fireIcon = new ImageIcon(getClass().getResource("/images/fire.png"));

    private UpgradeMenu upgradeMenu;
    private boolean drawUpgradeMenu = false;
    private boolean endlessMode;

    private boolean playLowHealthSound = true;

    public boolean isDrawUpgradeMenu() {
        return drawUpgradeMenu;
    }

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        difficultyLevel = 1;

        player = new Player(this, 750 - 25, 375 - 25, 100);

        walls = new ArrayList<>();
        generateWalls();

        entities = new ArrayList<>();
        entitiesToRemove = new ArrayList<>();
        entities.add(player);
        entities.add(new BasicPistol(this, 725, 350));
        entities.add(new FireBomb(this, 725, 350));
        totalKills = 0;

        for (int i = 0; i < 20; ) {
            int x = (int) (Math.random() * 3950);
            int y = (int) (Math.random() * 2950);
            boolean spawn = true;
            for (Wall w : walls) {
                if ((w.inside(x - 20, y - 20) || w.inside(x + 70, y + 70) || w.inside(x + 70, y - 20) || w.inside(x - 20, y + 70) || player.getHitbox().contains(x, y))) {
                    spawn = false;
                    break;
                }
            }
            if (spawn) {
                i++;
                double random = Math.random();
                if (random < .58)
                    entities.add(new ItemBarrel(this, x, y, 50));
                else
                    entities.add(new GunBarrel(this, x, y, 50));
            }
        }

        for (int i = 0; i < 40; ) {
            int x = (int) (Math.random() * 3950);
            int y = (int) (Math.random() * 2950);
            boolean spawn = true;
            for (Wall w : walls) {
                //spawn if i am NOT inside ALL the walls
                if ((w.inside(x - 10, y - 20) || w.inside(x + 60, y + 60) || w.inside(x + 60, y - 20) || w.inside(x - 10, y + 60))) {
                    spawn = false;
                    break;
                }
            }
            if (spawn) {
                i++;
                entities.add(new BasicZombie(this, x, y));
            }
        }
        upgradeMenu = new UpgradeMenu(this);
        endlessMode = false;
        gameCamera = new Camera(this, player.getCenterX(), player.getCenterY());
    }

    public void reset() {
        difficultyLevel = 1;

        player = new Player(this, 750 - 25, 375 - 25, 100);

        walls = new ArrayList<>();
        generateWalls();

        entities = new ArrayList<>();
        entitiesToRemove = new ArrayList<>();
        entities.add(player);
        entities.add(new BasicPistol(this, 725, 350));
        entities.add(new FireBomb(this, 725, 350));
        totalKills = 0;

        for (int i = 0; i < 20; ) {
            int x = (int) (Math.random() * 3950);
            int y = (int) (Math.random() * 2950);
            boolean spawn = true;
            for (Wall w : walls) {
                if ((w.inside(x - 20, y - 20) || w.inside(x + 70, y + 70) || w.inside(x + 70, y - 20) || w.inside(x - 20, y + 70) || player.getHitbox().contains(x, y))) {
                    spawn = false;
                    break;
                }
            }
            if (spawn) {
                i++;
                double random = Math.random();
                if (random < .58)
                    entities.add(new ItemBarrel(this, x, y, 50));
                else
                    entities.add(new GunBarrel(this, x, y, 50));
            }
        }

        for (int i = 0; i < 40; ) {
            int x = (int) (Math.random() * 3950);
            int y = (int) (Math.random() * 2950);
            boolean spawn = true;
            for (Wall w : walls) {
                //spawn if i am NOT inside ALL the walls
                if ((w.inside(x - 10, y - 20) || w.inside(x + 60, y + 60) || w.inside(x + 60, y - 20) || w.inside(x - 10, y + 60) || player.getHitbox().contains(x, y))) {
                    spawn = false;
                    break;
                }
            }
            if (spawn) {
                i++;
                entities.add(new BasicZombie(this, x, y));
            }
        }
        upgradeMenu = new UpgradeMenu(this);
        endlessMode = false;
        gameCamera = new Camera(this, player.getCenterX(), player.getCenterY());
    }

    public void paintComponent(Graphics g) {
        gameCamera.setTranslateX(!(player.getX() <= 750) && !(player.getX() >= 3250));
        gameCamera.setTranslateY(!(player.getY() <= 375) && !(player.getY() >= 2625));
        gameCamera.center(player);
        drawCamera(g);

        g.drawImage(mapIcon.getImage(), 0, 0, 4000, 3000, null);


        int currXSec = calcXSection((int) player.getX());
        int currYSec = calcYSection((int) player.getY());

        for (Entity e : new ArrayList<>(entities)) {
            int entityXSec = calcXSection((int) e.getX());
            int entityYSec = calcYSection((int) e.getY());
            if (entityXSec >= currXSec - 3 && entityXSec <= currXSec + 3 && entityYSec >= currYSec - 3 && entityYSec <= currYSec + 3) {
                e.drawSelf(g);
            }
        }
        for (Wall w : walls) {
            int wallX = calcXSection(w.getX());
            int wallY = calcYSection(w.getY());
            if (wallX >= currXSec - 3 && wallX <= currXSec + 3 && wallY >= currYSec - 3 && wallY <= currYSec + 3) {
                w.drawSelf(g);
            }
        }

        drawPlayer(g);

        int xOff = getOffsets()[0];
        int yOff = getOffsets()[1];
        if (player.getHealth() < 20) {
            g.drawImage(lowHealthIcon.getImage(), -xOff, -yOff, 1500, 750, null);
            if (playLowHealthSound) {
                Game.changeSoundEffect(7);
                playLowHealthSound = false;
            }
        }
        //GUI
        gameCamera.drawSelf(g, xOff, yOff);

        if (drawUpgradeMenu) {
            upgradeMenu.drawSelf(g);
            if (upgradeMenu.isDoneUpgrading())
                drawUpgradeMenu = false;
        }
    }

    public boolean act() {
        for (Entity entity : new ArrayList<>(entities)) {
            if (entity.act()) {
                if (entity instanceof AbstractZombie) {
                    totalKills++;
                    int random = (int) (Math.random() * 40);
                    if (random < 2)
                        entities.add(new FireBomb(this, (int) entity.getX(), (int) entity.getY()));
                    else if (random < 5)
                        entities.add(new Frag(this, (int) entity.getX(), (int) entity.getY()));
                    else if (random < 8)
                        entities.add(new Sticky(this, (int) entity.getX(), (int) entity.getY()));
                    else if (random < 11)
                        entities.add(new Stun(this, (int) entity.getX(), (int) entity.getY()));
                }
                entitiesToRemove.add(entity);
            }
            for (Wall w : walls) {
                boolean entityIntersectsHorizontal = entity.getBoundsHorizontal().intersects(w.hitbox);
                boolean entityIntersectsVertical = entity.getBoundsVertical().intersects(w.hitbox);
                if (entity instanceof Projectile) {
                    if (entity instanceof AbstractGrenade) {
                        if (entityIntersectsHorizontal) {
                            entity.setxVel(entity.getxVel() * -.3);
                        }
                        if (entityIntersectsVertical) {
                            entity.setyVel(entity.getyVel() * -.3);
                        }
                    } else if (entityIntersectsVertical || entityIntersectsHorizontal) {
                        entitiesToRemove.add(entity);
                    }
                } else if (entity instanceof AbstractZombie) {
                    if (entityIntersectsHorizontal) {
                        entity.setX(entity.getX() - entity.getxVel() * ((AbstractZombie) entity).getSpeed());
                    }
                    if (entityIntersectsVertical) {
                        entity.setY(entity.getY() + (entity.getyVel() * ((AbstractZombie) entity).getSpeed()));
                    }


                } else {
                    if (entity instanceof Player && entityIntersectsHorizontal) {
                        //shows the rectangle made by two intersecting rectangles
                        Rectangle intersections = entity.getBoundsHorizontal().intersection(w.hitbox);
                        //checking if moving in x and rectangle width not equal to player width
                        if (entity.getxVel() != 0 && intersections.width != entity.getHitBoxWidth()) {
                            entity.setX(entity.getX() - entity.getxVel());
                        }
                        //check if moving in y and rectangle width not equal to player width
                        else if (entity.getyVel() != 0 && intersections.height != entity.getHitBoxHeight()) {
                            entity.setY(entity.getY() - entity.getyVel());
                        }
                    } else {
                        if (entityIntersectsHorizontal) {
                            entity.setX(entity.getX() - entity.getxVel());
                        } else if (entityIntersectsVertical) {
                            entity.setY(entity.getY() - entity.getyVel());
                        }
                    }
                }


            }
        }

        entities.removeAll(entitiesToRemove);

        if (!areZombiesAlive()) {
            for (Entity e : entities) {
                if (e instanceof ItemBarrel || e instanceof GunBarrel)
                    entitiesToRemove.add(e);
            }
            player.setX(725);
            player.setY(350);
            drawUpgradeMenu = true;
            upgradeMenu.act();
            if (difficultyLevel > 5) {
                if (!endlessMode) {
                    JPanel panel = new JPanel();
                    Object[] options = {"YES", "NO"};
                    int result = JOptionPane.showOptionDialog(panel, "Endless Mode?", "Choose", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (result == JOptionPane.YES_OPTION) {
                        endlessMode = true;
                    } else {
                        return true;
                    }
                }
            }
            if (upgradeMenu.isDoneUpgrading()) {
                drawUpgradeMenu = false;
                upgradeMenu.applyUpgrades();
                upgradeMenu.applyGunUpgrades();
                upgradeMenu.resetUpgrades();
                player.addHealth(player.getMaxHealth());

                difficultyLevel++;
                Level level = new Level(this, difficultyLevel);
                level.spawnZombies();
            }
        }
        return player.isDead();
    }

    public boolean areZombiesAlive() {
        for (Entity e : entities) {
            if (e instanceof AbstractZombie) {
                return true;
            }
        }
        return false;
    }

    public UpgradeMenu getUpgradeMenu() {
        return upgradeMenu;
    }

    public int calcXSection(int x) {
        return x / (4000 / 10);
    }

    public int calcYSection(int y) {
        return y / (3000 / 10);
    }

    public void drawPlayer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(player.getMouseAngle(
                (int) MouseInfo.getPointerInfo().getLocation().getX() - 210 - getOffsets()[0],
                (int) MouseInfo.getPointerInfo().getLocation().getY() - 170 - getOffsets()[1]),
                player.getCenterX(), player.getCenterY());
        if (player.getHotbarPosition() == 1 && player.getGunslot1() instanceof BasicPistol) {
            g2d.drawImage(playerIcon.getImage(), (int) player.getX() - 10, (int) player.getY() - 25, 75, 75, null);
        } else if(player.getHotbarPosition() == 1 && !(player.getGunslot1() instanceof BasicPistol)) {
            g2d.drawImage(player2Icon.getImage(), (int) player.getX() - 10, (int) player.getY() - 25, 90, 75, null);
        } else if (player.getGunslot2() == null || (player.getGunslot2() != null && player.getHotbarPosition() == 2 && player.getGunslot2() instanceof BasicPistol)) {
            g2d.drawImage(playerIcon.getImage(), (int) player.getX() - 10, (int) player.getY() - 25, 75, 75, null);
        } else {
            g2d.drawImage(player2Icon.getImage(), (int) player.getX() - 10, (int) player.getY() - 25, 90, 75, null);
        }
        g2d.setTransform(old);
        if (player.isOnFire())
            g2d.drawImage(fireIcon.getImage(), (int) player.getX(), (int) player.getY(), 40, 40, null);

    }

    public void drawCamera(Graphics g) {
        int xOff = getOffsets()[0];
        int yOff = getOffsets()[1];
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(xOff, yOff);
    }

    public int[] getOffsets() {
        int[] output = new int[2];
        if (!gameCamera.isTranslateX() && !gameCamera.isTranslateY()) {
            if (!(player.getX() <= 750)) {
                output[0] = -2500;
            }
            if (player.getY() > 375) {
                output[1] = -2250;
            }
        } else if (!gameCamera.isTranslateX()) {
            output[1] = (int) gameCamera.getyOffset();
            if (player.getX() > 750) {
                output[0] = -2500;
            }
        } else if (!gameCamera.isTranslateY()) {
            output[0] = (int) gameCamera.getxOffset();
            if (player.getY() > 375) {
                output[1] = -2250;
            }
        } else {
            output[0] = (int) gameCamera.getxOffset();
            output[1] = (int) gameCamera.getyOffset();
        }
        return output;
    }


    public void generateWalls() {
        walls.add(new Wall(this, 0, -10, 4000, 10));
        walls.add(new Wall(this, 0, 3000, 4000, 10));
        walls.add(new Wall(this, -10, 0, 10, 3000));
        walls.add(new Wall(this, 4000, 0, 10, 3000));

        //adding walls
        try {
            Scanner myReader = new Scanner(getClass().getResourceAsStream("/grid"));
            int counter = 0;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    gridData[counter][i] = line.charAt(i) + "";
                }
                counter++;
            }
            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //horizontal walls
        int widthCounter = 0;
        for (int row = 0; row < gridData.length; row++) {
            for (int col = 0; col < gridData[0].length; col++) {
                if (gridData[row][col].equals("1")) {
                    widthCounter++;
                } else {
                    if (widthCounter > 0) {
                        walls.add(new Wall(this, (col - widthCounter) * 8, (row) * 8, widthCounter * 8, 8));
                    }
                    widthCounter = 0;
                }
            }
        }

        //vertical walls
        int heightCounter = 0;
        for (int col = 0; col < gridData[0].length; col++) {
            for (int row = 0; row < gridData.length; row++) {
                if (gridData[row][col].equals("2")) {
                    heightCounter++;
                } else {
                    if (heightCounter > 0) {
                        walls.add(new Wall(this, col * 8, (row - heightCounter) * 8, 8, heightCounter * 8));
                    }
                    heightCounter = 0;
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        gameCamera.mousePressed(e);
        player.mousePressed(e);
        if (drawUpgradeMenu)
            upgradeMenu.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {

    }
}


