package com.ebicep.undeadstorm;

import com.ebicep.undeadstorm.Entities.Entity;
import com.ebicep.undeadstorm.Entities.guns.GunBarrel;
import com.ebicep.undeadstorm.Entities.items.ItemBarrel;
import com.ebicep.undeadstorm.Entities.zombies.types.*;

import java.util.ArrayList;

public class Level {
    private Map map;
    private final int level;
    private int numberOfNormalZombies;
    private int numberOfFatZombies;
    private int numberOfDogZombies;
    private int numberOfMercyZombies;


    public Level(Map map, int level) {
        this.map = map;
        this.level = level;
        /*
        level 1 = 40
        level 2 = 60
        level 3 = 90
        level 4 = 130
        level 5 = 150

         */
        if (level == 2) {
            numberOfNormalZombies = 30;
            numberOfFatZombies = 15;
            numberOfDogZombies = 15;
        } else if (level == 3) {
            numberOfNormalZombies = 40;
            numberOfFatZombies = 30;
            numberOfDogZombies = 20;
        } else if (level == 4) {
            numberOfNormalZombies = 60;
            numberOfFatZombies = 30;
            numberOfDogZombies = 30;
            numberOfMercyZombies = 10;
        } else if (level == 5) {
            Game.setPlayBackgroundMusic(false);
            Game.setBossLevel(true);
            numberOfNormalZombies = 60;
            numberOfFatZombies = 40;
            numberOfDogZombies = 30;
            numberOfMercyZombies = 20;
            map.getEntities().add(new BossZombie(map, 2070, 1335));
        } else if (level > 5) {
            Game.setPlayBackgroundMusic(true);
            Game.setBossLevel(false);
            numberOfNormalZombies = level * 10;
            numberOfFatZombies = (level - 1) * 10;
            numberOfDogZombies = (level - 2) * 10;
            numberOfMercyZombies = (level - 3) * 10;
            if (level % 5 == 0)
                map.getEntities().add(new BossZombie(map, 2070, 1335));
        }
    }

    public void spawnZombies() {
        ArrayList<Entity> entities = map.getEntities();

        for (int i = 0; i < numberOfNormalZombies; i++) {
            int x = (int) (Math.random() * 3900);
            int y = (int) (Math.random() * 2900);
            if (spawnable(x, y)) {
                i++;
                BasicZombie tempZomb = new BasicZombie(map, x, y);
                tempZomb.setHealth((int) (tempZomb.getMaxHealth() + 2.5 * level));
                entities.add(tempZomb);
            }
        }
        for (int i = 0; i < numberOfFatZombies; i++) {
            int x = (int) (Math.random() * 3900);
            int y = (int) (Math.random() * 2900);
            if (spawnable(x, y)) {
                i++;
                FatZombie tempZomb = new FatZombie(map, x, y);
                tempZomb.setHealth((int) (tempZomb.getMaxHealth() + 2.5 * level));
                entities.add(tempZomb);
            }
        }
        for (int i = 0; i < numberOfDogZombies; i++) {
            int x = (int) (Math.random() * 3900);
            int y = (int) (Math.random() * 2900);
            if (spawnable(x, y)) {
                i++;
                DogZombie tempZomb = new DogZombie(map, x, y);
                tempZomb.setHealth((int) (tempZomb.getMaxHealth() + 2.5 * level));
                entities.add(tempZomb);
            }
        }
        for (int i = 0; i < numberOfMercyZombies; i++) {
            int x = (int) (Math.random() * 3900);
            int y = (int) (Math.random() * 2900);
            if (spawnable(x, y)) {
                i++;
                MercyZombie tempZomb = new MercyZombie(map, x, y);
                tempZomb.setHealth((int) (tempZomb.getMaxHealth() + 2.5 * level));
                entities.add(tempZomb);
            }
        }
        //spawning in new barrels
        for (int i = 0; i < level + 19; ) {
            int x = (int) (Math.random() * 3900);
            int y = (int) (Math.random() * 2900);
            boolean spawn = true;
            for (Wall w : map.getWalls()) {
                if ((w.inside(x - 35, y - 35) || w.inside(x + 85, y + 85) || w.inside(x + 85, y - 35) || w.inside(x - 35, y + 85) || map.getPlayer().getHitbox().contains(x, y))) {
                    spawn = false;
                    break;
                }
            }
            if (spawn) {
                i++;
                double random = Math.random();
                if (random < .58)
                    map.getEntities().add(new ItemBarrel(map, x, y, 50));
                else
                    map.getEntities().add(new GunBarrel(map, x, y, 50));
            }
        }
    }

    private boolean spawnable(int x, int y) {
        for (Wall w : map.getWalls()) {
            if ((w.inside(x - 10, y - 20) || w.inside(x + 60, y + 60) || w.inside(x + 60, y - 20) || w.inside(x - 10, y + 60))) {
                return false;
            }
        }
        return true;
    }
}
