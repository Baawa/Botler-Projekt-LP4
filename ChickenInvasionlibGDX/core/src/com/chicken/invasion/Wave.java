package com.chicken.invasion;

import java.util.ArrayList;

/**
 * Created by Kristoffer on 2016-05-02.
 */
public class Wave implements Runnable{
    private ArrayList<Enemy> enemies;
    private Thread thread;
    private String level;
    private int totalEnemies;
    private int nbrOfSent = 1;

    public Wave(String level,int totalEnemies){
        this.level = level;
        this.totalEnemies = totalEnemies;

        enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(this));

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        while(nbrOfSent<totalEnemies){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            enemies.add(new Enemy(this));
            nbrOfSent++;
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
