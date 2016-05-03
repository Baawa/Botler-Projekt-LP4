package com.chicken.invasion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by Kristoffer on 2016-05-02.
 */
public class Enemy {

    private final static Texture IMAGE = new Texture("chicken.png");
    private Sprite sprite;
    private int health;
    private float speed;
    private Rectangle collideRect;
    private boolean isCollided;
    private Wave wave;

    public Enemy(Wave wave){
        this.wave = wave;

        sprite = new Sprite(IMAGE);
        sprite.setSize(1,1.4f);

        Random rand = new Random();
        float x = rand.nextFloat()*(Gdx.graphics.getWidth()/120) + 0.5f;
        sprite.setPosition(x, Gdx.graphics.getHeight() / 190);

        collideRect = sprite.getBoundingRectangle();

        health = 10;
    }

    public void draw(SpriteBatch sb){
        update(Gdx.graphics.getDeltaTime());
        sprite.draw(sb);
    }

    public void update(float dt){
        sprite.setSize(sprite.getWidth()+(dt/4), sprite.getHeight()+(dt/4));
        sprite.setY(sprite.getY() - dt);
        collideRect = sprite.getBoundingRectangle();
    }

    public Rectangle getCollideRect() { return collideRect; }
}
