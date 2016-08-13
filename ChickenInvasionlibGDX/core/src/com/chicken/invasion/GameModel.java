package com.chicken.invasion;

import com.chicken.invasion.Enemy_Throwable.*;
import com.chicken.invasion.Enemy_Throwable.Throwable;
import com.chicken.invasion.Helpers.Player;
import com.chicken.invasion.Store.StoreCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albin on 2016-08-06.
 */
public class GameModel {
    public enum State{
        PAUSED, RUNNING, STOPPED, GAMEOVER
    }

    private State state = State.STOPPED;

    private int currentWave = 0;

    private ArrayList<Throwable> throwables = new ArrayList<Throwable>();

    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    private StoreCollection backgrounds;

    private Player player;

    //------------
    private static GameModel instance = null;

    private GameModel(){}

    public static GameModel getInstance(){
        if (instance == null){
            instance = new GameModel();
        }
        return instance;
    }
    //------------

    public void nextWave() {
        currentWave += 1;

        fillUpThrowables();
    }

    public void restartWaves() {
        currentWave = 1;
    }

    public Throwable getCurrentThrowable(){
        if (throwables.size() > 0){
            return throwables.get(0);
        } else{
            return null;
        }
    }

    private int getDifficulty(){
        if (currentWave > 2){
            int tmp = (int)(2 * Math.pow(currentWave, 2) / 3);
            return tmp;
        } else {
            return currentWave * 2;
        }
    }

    private int getNumberOfThrowables(){
        int tmp = getDifficulty() + 2;
        return tmp;
    }

    private void fillUpThrowables(){
        while (throwables.size() > 0){
            throwables.remove(0);
        }

        for (int i = 0; i < getNumberOfThrowables(); i++){
            throwables.add(player.getEquippedThrowable());
        }
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void setBackgrounds(StoreCollection backgrounds){
        this.backgrounds = backgrounds;
    }

    public StoreCollection getBackgrounds(){
        return this.backgrounds;
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

}