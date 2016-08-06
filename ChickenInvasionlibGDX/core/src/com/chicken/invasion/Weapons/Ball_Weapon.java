package com.chicken.invasion.Weapons;

import com.badlogic.gdx.physics.box2d.World;
import com.chicken.invasion.Enemy_Throwable.Throwable_Object;
import com.chicken.invasion.Store.StoreItem;

/**
 * Created by Albin on 2016-08-06.
 */
public class Ball_Weapon extends Throwable_Object implements StoreItem{
    private String name = "Gatsby's Beach Ball";

    private boolean purchased = false;

    public Ball_Weapon(World world) {
        super("beachball200x200", world);

        this.setDamage(1);
        this.setSpeed(1.2f);
    }

    public String getName(){
        return this.name;
    }

    public void setPurchased(boolean purchased){
        this.purchased = purchased;
    }

    public boolean isPurchased(){
        return this.purchased;
    }

    public int getPrice(){
        return (int)(this.getDamage() * this.getSpeed() * 100);
    }
}
