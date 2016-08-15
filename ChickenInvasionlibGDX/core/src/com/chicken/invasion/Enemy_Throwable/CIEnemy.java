package com.chicken.invasion.Enemy_Throwable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.chicken.invasion.Helpers.CICollisionRect;
import com.chicken.invasion.Helpers.CollisionRect;

import java.util.Random;

/**
 * Created by Albin on 2016-08-14.
 */
public class CIEnemy implements Enemy, Cloneable {
    private float x, y, height, width, hp, speed, stateTime;

    private CICollisionRect collisionRect;

    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 2;

    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion currentFrame;

    private String imageURL;

    private Boolean pushable = true;

    public CIEnemy(CIEnemy e){
        width = 2.0f;
        height = 2.0f;

        hp = 1;

        pushable = true;
    }

    public CIEnemy(String image, float health, boolean pushable){
        this.imageURL = image;

        walkSheet = new Texture(image);
        TextureRegion[][] tmp = TextureRegion.split(this.walkSheet, this.walkSheet.getWidth()/FRAME_COLS, this.walkSheet.getHeight()/FRAME_ROWS);
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.09f, walkFrames);
        stateTime = 0f;

        this.hp = health;

        this.pushable = pushable;

        Random rand = new Random();
        x = (rand.nextFloat()*(Gdx.graphics.getWidth()/120) + 0.5f);
        if (x>12){ x-=1; }
        y = Gdx.graphics.getHeight() / 190;

        width = 2.0f;
        height = 2.0f;

        this.collisionRect = new CICollisionRect(new Rectangle(x,y,width,height));
    }


    @Override
    public void decHP(float amount) {
        this.hp -= amount;
    }

    @Override
    public float getHP() {
        return this.hp;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public void setSpeed(float speed){
        this.speed = speed;
    }

    @Override
    public float getSpeed(){
        return this.speed;
    }

    @Override
    public Object getSprite() {
        return this.currentFrame;
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        x += -dt/7;
        y += -dt/1.2f;

        width += dt/3f;
        height += dt/3f;

        collisionRect = new CICollisionRect(new Rectangle(x, y, width, height));
    }

    @Override
    public CollisionRect getCollisionRect() {
        return this.collisionRect;
    }

    @Override
    public void pushBack() {
        if (this.pushable){
            this.y ++;
        }
    }

    private String getImageURL(){
        return this.imageURL;
    }

    private boolean isPushable(){
        return this.pushable;
    }

    public CIEnemy clone(){
        CIEnemy e = new CIEnemy(this.getImageURL(), this.getHP(), this.isPushable());

        e.setSpeed(this.getSpeed());

        return e;
    }

    @Override
    public void dispose(){
        this.walkSheet.dispose();
    }
}