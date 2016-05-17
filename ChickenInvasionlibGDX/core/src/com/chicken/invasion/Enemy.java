package com.chicken.invasion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by Kristoffer on 2016-05-02.
 */
public class Enemy {
    private float                   x,y, width, height;
    private int                     health;
    private float                   speed;
    private Rectangle               collideRect;

    private static final int        FRAME_COLS = 2;
    private static final int        FRAME_ROWS = 2;

    private Animation               walkAnimation;
    private static Texture          walkSheet = new Texture("chickenlargeanim.png");
    private TextureRegion           currentFrame;

    float stateTime;

    public Enemy(){
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.09f, walkFrames);
        stateTime = 0f;

        Random rand = new Random();
        x = (rand.nextFloat()*(Gdx.graphics.getWidth()/120) + 0.5f);
        if (x>12){ x-=1; }
        y = Gdx.graphics.getHeight() / 190;

        width = 2.0f;
        height = 2.0f;

        collideRect = new Rectangle(x,y,width,height);

        health = 10;
    }

    public void update(float dt){
        stateTime += dt;
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        x += -dt/7;
        y += -dt/1.2f;

        width += dt/3f;
        height += dt/3f;

        collideRect.set(x,y,width,height);


        /*
        sprite.setSize(sprite.getWidth()+(dt/3.5f), sprite.getHeight()+(dt/3.5f));
        sprite.setX(sprite.getX()-dt/7);
        sprite.setY(sprite.getY() - dt/1.2f);
        collideRect = sprite.getBoundingRectangle();
        */
    }

    public Rectangle getCollideRect() { return collideRect; }

    public TextureRegion getTextureRegion() { return currentFrame; }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getHealth(){
        return this.health;
    }
}
