package com.chicken.invasion.model;

import com.chicken.invasion.physics.ICollisionRect;
import com.chicken.invasion.service.MusicPlayer;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * Created by Albin on 2016-08-06.
 */
public class GameModel {
    private int currentWave = 0;
    private int width, height;

    private ArrayList<Weapon> weapons;
    private ArrayList<Enemy> allEnemies;
    private ArrayList<Enemy> enemies;
    private ArrayList<Enemy> activeEnemies;
    private ArrayList<Enemy> enemiesToRemove;

    private IPlayer player;
    private ICollisionRect bottomRect;
    private Background currentBackground;

    private boolean musicOn = true;
    private boolean showSettings = false;

    //------------
    private static GameModel instance = null;

    private GameModel(){
        weapons = new ArrayList<Weapon>();
        allEnemies = new ArrayList<Enemy>();
        enemies = new ArrayList<Enemy>();
        activeEnemies = new ArrayList<Enemy>();
        enemiesToRemove = new ArrayList<Enemy>();
    }

    public static GameModel getInstance(){
        if (instance == null){
            instance = new GameModel();
        }
        return instance;
    }
    //------------

    public void setScreenSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void updateGameObjects(float dt){
        //update objects if game is running
        if (getState() == GameState.RUNNING) {
            if (getCurrentWeapon() != null){ getCurrentWeapon().update(dt); }
            for (Enemy e : getActiveEnemies()) {
                e.update(dt);
            }
            checkCollisions();
        }
    }

    public void startNewGame(){
        MusicPlayer.playBgMusic("gamemusic/ChickenInvasion-BackgroundMusic.mp3");
        currentWave = 0;
        nextWave();
        player.setScore(0);
    }

    private void nextWave() {
        currentWave += 1;
        fillUpThrowables();
        activeEnemies.clear();
        enemies.clear();
        enemies = getRandomSetOfEnemies(getDifficulty());
        System.out.println("TBX/ Våg: " + currentWave + " antal fiender: " + enemies.size());
        new Wave();
    }

    public Weapon getCurrentWeapon(){
        if (weapons.size() > 0){
            return weapons.get(0);
        } else{
            return null;
        }
    }

    public ArrayList<Enemy> getActiveEnemies(){
        return this.activeEnemies;
    }

    private int getDifficulty(){
        if (currentWave > 2){
            return (int)(2 * Math.pow(currentWave, 2) / 3);
        } else {
            return currentWave * 2;
        }
    }

    private int getNumberOfWeapons(){
        return getDifficulty() + 2;
    }
    private void fillUpThrowables(){
        weapons.clear();
        for (int i = 0; i < getNumberOfWeapons(); i++){
            weapons.add(player.getEquippedWeapon().clone());
        }
    }

    void spawnEnemy(){
        if (enemiesLeft()) {
            activeEnemies.add(enemies.get(0));
            enemies.remove(0);
        }
    }

    boolean enemiesLeft(){
        return enemies.size() > 0;
    }

    public void setPlayer(IPlayer player){
        this.player = player;
    }

    public IPlayer getPlayer(){
        return this.player;
    }

    public void setAllEnemies(ArrayList<Enemy> enemies){ this.allEnemies = enemies;}

    public void setCurrentBackground(Background bg){
        this.currentBackground = bg;
    }

    public Background getCurrentBackground(){
        return this.currentBackground;
    }

    public void setBottomRect(ICollisionRect rect){
        this.bottomRect = rect;
    }

    private void checkCollisions(){
        try {
            for (Enemy e : activeEnemies) {
                if (weapons.size() != 0) {
                    if (weapons.get(0).getCollisionRect().overlaps(e.getCollisionRect())
                            && getCurrentWeapon().isThrown()) {
                        //A chicken is hit
                        MusicPlayer.playMusic("gamemusic/ChickenSound.mp3");

                        //Lower weapons damage
                        if (getCurrentWeapon().getDamage() <= 1) {
                            getCurrentWeapon().setCollided(true);
                        } else {
                            getCurrentWeapon().decHP(1);
                        }
                        enemyIsHit(e);
                        checkWeapon();
                    }
                }
                //If the enemy has reached the player
                if (e.getCollisionRect().overlaps(bottomRect)) {
                    gameOver();
                    break;
                    //Do some other stuff
                }
            }
        } catch (ConcurrentModificationException e){ e.printStackTrace();}
        activeEnemies.removeAll(enemiesToRemove);
        enemiesToRemove.clear();
        checkWeapon();

        //New wave?
        if (activeEnemies.size() == 0 && enemies.size() == 0){
            weapons.clear();
            nextWave();
        }
    }

    private void enemyIsHit(Enemy e){
        if (getCurrentWeapon().getDamage() >= e.getHP()){
            enemiesToRemove.add(e);
            player.setScore(player.getScore() + e.getPointsForKilling());
            player.setChickenLegs(player.getChickenLegs() + e.getPointsForKilling());
        } else{
            e.pushBack();
            e.decHP(this.getCurrentWeapon().getDamage());
        }
    }

    private void checkWeapon(){
        if (getCurrentWeapon() != null) {
            if (getCurrentWeapon().hasCollided()) {
                weapons.remove(0);
                if (!weapons.isEmpty()) {
                    weapons.get(0).resetBody();
                }
            }
        }
    }

    private ArrayList<Enemy> getRandomSetOfEnemies(int amount) {
        int tmp = amount;
        ArrayList<Enemy> tmp_array = new ArrayList<Enemy>();

        Random rand = new Random();
        while (tmp > 0 && allEnemies.size() > 0){
            int tmp_rand = rand.nextInt(allEnemies.size());

            if (tmp >= allEnemies.get(tmp_rand).getHP()){
                tmp_array.add(allEnemies.get(tmp_rand).clone());

                tmp -= allEnemies.get(tmp_rand).getHP();
            }
        }
        return tmp_array;
    }

    public void toggleMusic(){
        if (musicOn){
            musicOn = false;
            MusicPlayer.stopBgMusic();
        } else{
            musicOn = true;
            MusicPlayer.playBgMusic("gamemusic/ChickenInvasion-BackgroundMusic.mp3");
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

    private GameState state = GameState.STOPPED;

    public GameState getState(){
        return this.state;
    }

    public void startGame(){
        this.state = GameState.RUNNING;
    }

    public void pauseGame(){
        this.state = GameState.PAUSED;
    }

    public void stopGame(){
        this.state = GameState.STOPPED;
    }

    public void gameOver() { this.state = GameState.GAMEOVER; }

}