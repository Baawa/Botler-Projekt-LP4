package com.chicken.invasion;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kristoffer on 2016-05-02.
 */
public class Wave implements Runnable{
    private ArrayList<Enemy> enemies;
    private ArrayList<Enemy> potentialEnemies;
    private Thread thread;
    private int level;
    private int difficulty;
    private int nbrOfSent = 0;

    public Wave(int level,int difficulty){
        this.level = level;
        this.difficulty = difficulty;

        enemies = new ArrayList<Enemy>();

        potentialEnemies = new ArrayList<Enemy>();
        potentialEnemies.add(new Enemy());

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        while(difficulty != 0){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Model.getInstance().getState() == Model.State.RUNNING) {
                spawn();
            }
        }
    }

    private Enemy getRandomEnemy(){
        Random rand = new Random();

        int tmp = 0;

        if (potentialEnemies.size() > 1){
            tmp = rand.nextInt(potentialEnemies.size());
        }

        return potentialEnemies.get(tmp);
    }

    private void spawn(){
        while (true){
            Enemy e = getRandomEnemy();
            if (e.getHealth() <= this.difficulty){
                spawnEnemy(e);
                difficulty -= e.getHealth();
                break;
            }
        }
    }

    private void spawnEnemy(Enemy e){
        enemies.add(enemies.size(), e);
        nbrOfSent++;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getLevel() {
        return level;
    }
}
