package com.chicken.invasion.Helpers;

import com.chicken.invasion.Enemy_Throwable.CIEnemy;
import com.chicken.invasion.Enemy_Throwable.Enemy;
import com.chicken.invasion.Enemy_Throwable.EnemyCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Albin on 2016-08-13.
 */
public class CIEnemyCollection implements EnemyCollection {
    private ArrayList<CIEnemy> enemies = new ArrayList<CIEnemy>();

    public CIEnemyCollection(){
        enemies.add(enemies.size() ,new CIEnemy("ekak-spritesheet.png", 1, true));
        enemies.add(enemies.size() ,new CIEnemy("army-spritesheet.png", 2, true));
        enemies.add(enemies.size() ,new CIEnemy("tramp-spritesheet.png", 4, true));
        enemies.add(enemies.size() ,new CIEnemy("robo-sheet.png", 10, false));
    }

    @Override
    public ArrayList<CIEnemy> getEnemies() {
        return enemies;
    }

    @Override
    public ArrayList<CIEnemy> getRandomSetOfEnemies(int amount) {
        int tmp = amount;

        ArrayList<CIEnemy> tmp_array = new ArrayList<CIEnemy>();

        Random rand = new Random();

        while (tmp > 0 && enemies.size() > 0){
            int tmp_rand = rand.nextInt(enemies.size());

            if (tmp >= enemies.get(tmp_rand).getHP()){
                tmp_array.add(enemies.get(tmp_rand).clone());

                tmp -= enemies.get(tmp_rand).getHP();
            }
        }

        return tmp_array;
    }
}
