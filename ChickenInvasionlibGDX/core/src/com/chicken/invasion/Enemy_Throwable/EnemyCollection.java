package com.chicken.invasion.Enemy_Throwable;

import java.util.List;

/**
 * Created by Albin on 2016-08-13.
 */
public interface EnemyCollection {
    <T extends Enemy> List<T> getEnemies();

    <T extends Enemy> List<T> getRandomSetOfEnemies(int amount); //Return a random set of enemies, amount == amount of enemies in list
}
