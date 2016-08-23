package com.chicken.invasion.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.chicken.invasion.model.Button;

/**
 * Created by Kristoffer on 2016-08-21.
 */

public class ButtonController {

    public Button button;
    public Sprite sprite;

    public ButtonController(Button b){
        button = b;
        sprite = new Sprite(new Texture(b.getImgURL()));
        sprite.setX(b.getX());
        sprite.setY(b.getY());
        sprite.setSize(b.getWidth(),b.getHeight());
    }

    public Sprite getSprite(){
        return sprite;
    }
    public Button getButton(){ return button; }
}
