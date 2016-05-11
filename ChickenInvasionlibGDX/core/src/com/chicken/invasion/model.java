package com.chicken.invasion;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by Albin on 2016-05-03.
 */
public class Model {

    //Lol
    private static Model instance = null;

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

    private State state = State.STOPPED;
    private int currentWave = 1;

    private Boolean spawnObject = false;

    public void nextWave(){
        currentWave += 1;
    }

    public int getNumberOfThrowables(){
        int tmp = (currentWave/2 * 4) + 1;
        return tmp;
    }

    public int getNumberOfEnemies(){
        int tmp = currentWave/2 * 4;
        return tmp;
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

    public void setSpawnObject(Boolean b){
        this.spawnObject = b;
    }

    public Boolean shouldSpawnObject(){
        return this.spawnObject;
    }

}
