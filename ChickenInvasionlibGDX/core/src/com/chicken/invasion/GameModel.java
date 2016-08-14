package com.chicken.invasion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.chicken.invasion.Enemy_Throwable.*;
import com.chicken.invasion.Enemy_Throwable.Throwable;
import com.chicken.invasion.Enemy_Throwable.Enemy;
import com.chicken.invasion.Helpers.Background_Object;
import com.chicken.invasion.Helpers.CIMusicPlayer;
import com.chicken.invasion.Helpers.CollisionRect;
import com.chicken.invasion.Helpers.MusicPlayer;
import com.chicken.invasion.Helpers.Player;
import com.chicken.invasion.Store.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Albin on 2016-08-06.
 */
public class GameModel {
    private int currentWave = 0;

    private ArrayList<Throwable> throwables = new ArrayList<Throwable>();

    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    private ArrayList<Enemy> activeEnemies = new ArrayList<Enemy>();

    private StoreCollection backgrounds;

    private Background_Object currentBackground;

    private Player player;

    private MusicPlayer musicPlayer;

    private CollisionRect bottomRect;

    private boolean musicOn = true;

    private boolean showSettings = false;

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

    public void startNewGame(){
        musicPlayer.playBgMusic("gamemusic/ChickenInvasion-BackgroundMusic.mp3");

        restartWaves();

        player.setScore(0);
    }

    public void restartGame(){
        restartWaves();

        player.setScore(0);
    }

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

    public ArrayList<Enemy> getActiveEnemies(){
        return this.activeEnemies;
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

    public void setCurrentBackground(Background_Object bg){
        this.currentBackground = bg;
    }

    public Background_Object getCurrentBackground(){
        return this.currentBackground;
    }

    public void setMusicPlayer(MusicPlayer musicPlayer){
        this.musicPlayer = musicPlayer;
    }

    public MusicPlayer getMusicPlayer(){
        return this.musicPlayer;
    }

    public void setBottomRect(CollisionRect rect){
        this.bottomRect = rect;
    }

    public CollisionRect getBottomRect(){
        return this.bottomRect;
    }

    public void checkCollisions(){
        for (Iterator<Enemy> iterEnemies = this.enemies.iterator(); iterEnemies.hasNext(); ){
            Enemy e = iterEnemies.next();
            if (this.throwables.size() != 0){
                if (this.throwables.get(0).getCollisionRect().overlaps(e.getCollisionRect()) && this.getCurrentThrowable().isThrown()) {
                    //Adjust throwable
                    if (this.getCurrentThrowable().getDamage() <= 1){
                        this.getCurrentThrowable().setCollided(true);
                    } else{
                        this.getCurrentThrowable().decHP(1);
                    }

                    //Adjust enemy
                    if (this.getCurrentThrowable().getDamage() >= e.getHP()){
                        iterEnemies.remove();
                        this.player.setScore((int)(this.player.getScore()+e.getHP()));
                    } else{
                        e.pushBack();
                        e.decHP(this.getCurrentThrowable().getDamage());
                    }

                    this.musicPlayer.playMusic("gamemusic/ChickenSound.mp3");

                    if (this.enemies.size() == 0 && this.getDifficulty() == 0){
                        this.nextWave();
                    }

                }
            }

            //If the enemy has reached the player
            if (e.getCollisionRect().overlaps(bottomRect)){
                this.gameOver();
                //Do some other stuff
            }
        }
    }

    public void toggleMusic(){
        if (musicOn){
            musicOn = false;
            musicPlayer.stopBgMusic();
        } else{
            musicOn = true;
            musicPlayer.playBgMusic("gamemusic/ChickenInvasion-BackgroundMusic.mp3");
        }
    }

    public boolean isMusicOn(){
        return musicOn;
    }

    public void toggleSettingsView(){
        showSettings = !showSettings;
    }

    public boolean isShowSettings(){
        return showSettings;
    }

    //State-------------------

    public enum State{
        PAUSED, RUNNING, STOPPED, GAMEOVER
    }

    private State state = State.STOPPED;

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