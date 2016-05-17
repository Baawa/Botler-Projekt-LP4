package com.chicken.invasion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Kristoffer on 2016-05-02.
 */
public class Wave implements Runnable{
    private ArrayList<Enemy> enemies;
    private Thread thread;
    private int level;
    private int totalEnemies;
    private int nbrOfSent = 0;
    private BitmapFont waveFont;
    private boolean displayWaveFont = false;
    private float fontX, fontY;

    private static long startWaveTime;

    public Wave(int level,int totalEnemies){
        this.level = level;
        this.totalEnemies = totalEnemies;

        enemies = new ArrayList<Enemy>();
        new Enemy();

        initNewWaveFont();

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

    public void displayWaveFont() { startWaveTime = System.currentTimeMillis(); }

    public boolean isDisplayWaveFont() { return (startWaveTime + 2000)>System.currentTimeMillis();}

    public int getLevel() { return level; }

    public String getWaveString(){ return "Wave " + getLevel(); }

    public BitmapFont getWaveFont(){ return waveFont; }

    public float getFontX() {
        return fontX;
    }

    public float getFontY() {
        return fontY;
    }

    private void initNewWaveFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ChunkfiveEx.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;

        waveFont = generator.generateFont(parameter);
        waveFont.getData().setScale(0.02f);
        waveFont.setColor(Color.WHITE);


        fontX = Gdx.graphics.getWidth() / 200 - 0.5f;
        fontY = Gdx.graphics.getHeight() / 100 - 7;

        generator.dispose();
    }
}
