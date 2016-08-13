package com.chicken.invasion.oldstuff;

/**
 * Created by Albin on 2016-05-03.
 */
public class Model {

    private static Model instance = null;
    private State state = State.STOPPED;
    private int currentWave = 1;

    private boolean spawnObject = false;

    private Model(){}

    public static Model getInstance(){
        if (instance == null){
            instance = new Model();
        }
        return instance;
    }

    public enum State{
        PAUSED, RUNNING, STOPPED, GAMEOVER
    }

    public void nextWave(){
        currentWave += 1;
    }

    public void restartWaves() { currentWave = 1; }

    public int getNumberOfThrowables(){
        int tmp = getDifficulty() + 2;
        return tmp;
    }

    public int getDifficulty(){
        if (currentWave > 2){
            int tmp = (int)(2 * Math.pow(currentWave, 2) / 3);
            return tmp;
        } else {
            return currentWave * 2;
        }
    }

    public State getState(){
        return this.state;
    }

    public void startGame(){
        this.state = State.RUNNING;
    }

    public void pauseGame(){
        this.state = State.PAUSED;
    }

    public void stopGame(){
        this.state = State.STOPPED;
    }

    public void gameOver() { this.state = State.GAMEOVER; }

    public void setSpawnObject(boolean b){
        this.spawnObject = b;
    }

    public boolean shouldSpawnObject(){
        return this.spawnObject;
    }

    public void getThrowables(){

    }

}
