package com.chicken.invasion.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chicken.invasion.GameController;

/**
 * Created by Kristoffer on 2016-08-20.
 */

public class BackgroundScreen implements IScreen {

    protected SpriteBatch batch;
    protected GameController controller;
    private final static int SCALE = 100;
    private Texture wingsIcon = new Texture("chickenleg-skevsomfan.png");

    public BackgroundScreen(SpriteBatch batch, GameController controller){
        this.batch = batch;
        this.controller = controller;
    }

    @Override
    public void render(float delta) {
        batch.draw(controller.getBackground(), 0,0, (Gdx.graphics.getWidth() / SCALE)+0.5f,
                Gdx.graphics.getHeight() / SCALE);


        batch.draw(wingsIcon, 0.1f, Gdx.graphics.getHeight() / 100 - 1f, 1, 1);

        //Draw chicken wings
        controller.getFontWings().draw(batch, String.valueOf(controller.getPlayersWings()), 1.5f,
                Gdx.graphics.getHeight()/SCALE - 0.2f);
    }

    @Override
    public void dispose() {
        wingsIcon.dispose();
    }
}
