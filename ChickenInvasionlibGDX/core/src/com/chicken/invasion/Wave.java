package com.chicken.invasion;

import java.util.ArrayList;

/**
 * Created by Kristoffer on 2016-05-02.
 */
public class Wave implements Runnable{
    private ArrayList<Enemy> enemies;
    private Thread thread;
    private int level;
    private int totalEnemies;
    private int nbrOfSent = 0;

    public Wave(int level,int totalEnemies){
        this.level = level;
        this.totalEnemies = totalEnemies;

        enemies = new ArrayList<Enemy>();
        new Enemy();

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

            if (Model.getInstance().getState() == Model.State.RUNNING) {
                enemies.add(new Enemy());
                nbrOfSent++;
            }
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getLevel() {
        return level;
    }
}
