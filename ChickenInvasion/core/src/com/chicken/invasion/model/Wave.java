package com.chicken.invasion.model;

/**
 * Created by Albin on 2016-08-14.
 */
class Wave implements Runnable {
    private Thread thread;

    Wave(){
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(GameModel.getInstance().enemiesLeft()){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (GameModel.getInstance().getState() == GameState.RUNNING){
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