package com.chicken.invasion.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chicken.invasion.model.Enemy;
import com.chicken.invasion.service.WalkAnimation;

/**
 * Created by Kristoffer on 2016-08-21.
 */

public class EnemyController {

    private Enemy enemy;
    private WalkAnimation animation;

    public EnemyController(Enemy e){
        enemy = e;
        animation = new WalkAnimation(e.getImageURL());
    }

    public TextureRegion getCurrentFrame(){
        return animation.getCurrentFrame();
    }
    public float getEnemyX(){ return enemy.getX();}
    public float getEnemyY(){ return enemy.getY();}
    public float getEnemyWidth(){ return enemy.getWidth();}
    public float getEnemyHeight(){ return enemy.getHeight();}
    public Enemy getEnemy(){ return enemy;}

    public void update(){
        animation.update();
    }

}
