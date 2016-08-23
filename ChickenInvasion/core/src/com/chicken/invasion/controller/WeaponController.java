package com.chicken.invasion.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.chicken.invasion.model.GameModel;
import com.chicken.invasion.model.Weapon;

/**
 * Created by Kristoffer on 2016-08-21.
 */

public class WeaponController {

    private Weapon weapon;
    private Sprite sprite;

    public WeaponController(Weapon w){
        weapon = w;
        sprite = new Sprite(new Texture(weapon.getImageURL() + ".png"));
        sprite.setSize(weapon.getWidth(),weapon.getWidth());
    }

    public Sprite getSprite(){
        //update data from model
        if (GameModel.getInstance().getCurrentWeapon()!=null) {
            weapon = GameModel.getInstance().getCurrentWeapon();
            sprite.setSize(weapon.getWidth(),weapon.getWidth());
        } else { return null; }

        if (weapon.isThrown()) {
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            float scale = (1 / (5 * weapon.getScale() * (weapon.getY() / GameModel.getInstance().getHeight()) + 1));
            sprite.setSize(weapon.getOrgWidth() * scale, weapon.getOrgHeight() * scale);
            weapon.setSize(sprite.getWidth(), sprite.getHeight());
        }
        sprite.setX(weapon.getX());
        sprite.setY(weapon.getY());
        return sprite;
    }
}
