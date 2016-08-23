package com.chicken.invasion.controller.collections;

import com.badlogic.gdx.math.Rectangle;
import com.chicken.invasion.model.Enemy;
import com.chicken.invasion.physics.CollisionRect;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kristoffer on 2016-08-13.
 */
public class EnemyCollection {
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public EnemyCollection(){
        enemies.add(enemies.size() ,new Enemy(new CollisionRect(new Rectangle()), "ekak-spritesheet.png", 1, true));
        enemies.add(enemies.size() ,new Enemy(new CollisionRect(new Rectangle()), "army-spritesheet.png", 2, true));
        enemies.add(enemies.size() ,new Enemy(new CollisionRect(new Rectangle()), "tramp-spritesheet.png", 4, true));
        enemies.add(enemies.size() ,new Enemy(new CollisionRect(new Rectangle()), "robo-sheet.png", 10, false));
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
