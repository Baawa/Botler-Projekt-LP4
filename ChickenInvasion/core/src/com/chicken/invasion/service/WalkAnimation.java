package com.chicken.invasion.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Kristoffer on 2016-08-17.
 */

public class WalkAnimation {

    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 2;

    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion currentFrame;

    private float stateTime;

    public WalkAnimation(String imgURL){
        walkSheet = new Texture(imgURL);
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
    }

    public void update(){
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
    }

    public TextureRegion getCurrentFrame(){
        return currentFrame;
    }

}
