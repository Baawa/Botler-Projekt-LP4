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

    public Enemy(){

        sprite = new Sprite(IMAGE);
        sprite.setSize(0.8f,1.2f);

        Random rand = new Random();
        float x = rand.nextFloat()*(Gdx.graphics.getWidth()/120) + 0.5f;
        sprite.setPosition(x, Gdx.graphics.getHeight() / 190);

        collideRect = sprite.getBoundingRectangle();

        health = 10;
    }

    public void update(float dt){
        sprite.setSize(sprite.getWidth()+(dt/3.5f), sprite.getHeight()+(dt/3.5f));
        sprite.setX(sprite.getX()-dt/7);
        sprite.setY(sprite.getY() - dt/1.2f);
        collideRect = sprite.getBoundingRectangle();
    }

    public Rectangle getCollideRect() { return collideRect; }

    public Sprite getSprite() { return sprite; }
}
