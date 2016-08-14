package com.chicken.invasion.Helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.chicken.invasion.Store.Background;
import com.chicken.invasion.Store.StoreItem;

/**
 * Created by Albin on 2016-08-13.
 */
public class CIBackground extends Background_Object {
    private Sprite sprite;

    private int price;

    private String name;

    private boolean purchased = false;

    public CIBackground(String ImageURL, String name, int price){
        this.sprite = new Sprite(new Texture(ImageURL));

        this.name = name;

        this.price = price;
    }

    @Override
    public Object getSprite() {
        return sprite;
    }

    @Override
    public boolean isPurchased() {
        return purchased;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Override
    public int getPrice() {
        return this.price;
    }
}
