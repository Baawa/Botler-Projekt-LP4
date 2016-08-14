package com.chicken.invasion.Weapons;

import com.badlogic.gdx.physics.box2d.World;
import com.chicken.invasion.Enemy_Throwable.Throwable_Object;
import com.chicken.invasion.Store.StoreItem;

/**
 * Created by Albin on 2016-08-14.
 */
public class CIWeapon extends Throwable_Object implements StoreItem {
    private String name;

    private boolean purchased = false;

    public CIWeapon(String name, String imageURL, World world){
        super(imageURL,world);
        this.name = name;
    }

    @Override
    public boolean isPurchased() {
        return this.purchased;
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
        return (int)(this.getDamage() * this.getSpeed() * 100);
    }
}
