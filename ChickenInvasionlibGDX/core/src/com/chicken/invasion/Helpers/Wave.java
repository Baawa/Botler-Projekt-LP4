package com.chicken.invasion.Helpers;

import com.chicken.invasion.GameModel;

/**
 * Created by Albin on 2016-08-14.
 */
public class Wave implements Runnable {
    private Thread thread;

    public Wave(){
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(GameModel.getInstance().enemiesLeft()){
            try {
                thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (GameModel.getInstance().getState() == GameModel.State.RUNNING){
                GameModel.getInstance().spawnEnemy();
            }

        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}