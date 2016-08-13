package com.chicken.invasion.Helpers;

import com.chicken.invasion.Enemy_Throwable.Enemy;
import com.chicken.invasion.Enemy_Throwable.EnemyCollection;

import java.util.List;

/**
 * Created by Albin on 2016-08-13.
 */
public class CIEnemyCollection implements EnemyCollection {
    <T extends Enemy> List<T> getEnemies(){return null;}

    <T extends Enemy> List<T> getRandomSetOfEnemies(int amount){return null;} //Return a random set of enemies, amount == amount of enemies in list
}
