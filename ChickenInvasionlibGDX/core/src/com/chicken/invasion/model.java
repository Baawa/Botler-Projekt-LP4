package com.chicken.invasion;

/**
 * Created by Albin on 2016-05-03.
 */
public class model {
    public enum State{
        PAUSED, RUNNING, STOPPED
    }

    private State state = State.STOPPED;
    private int currentWave = 1;

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
}
