package com.chicken.invasion.Weapons;

import com.badlogic.gdx.physics.box2d.World;
import com.chicken.invasion.Enemy_Throwable.*;
import com.chicken.invasion.Enemy_Throwable.Throwable;
import com.chicken.invasion.Store.StoreCollection;
import com.chicken.invasion.Store.StoreItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albin on 2016-08-06.
 */
public class WeaponCollection extends CIWeaponCollection {
    private ArrayList<CIWeapon> weapons = new ArrayList<CIWeapon>();

    public WeaponCollection(World world){
        //Beach ball
        CIWeapon beachBall = new CIWeapon("Gatsby's beach ball","beachball200x200",world);
        beachBall.setDamage(1);
        beachBall.setSpeed(1.2f);
        beachBall.setPurchased(true);
        weapons.add(beachBall);

        //light bulb
        CIWeapon lightbulb = new CIWeapon("Edison's light bulb","lightbulb300x300",world);
        lightbulb.setDamage(2);
        lightbulb.setSpeed(0.9f);
        lightbulb.setPurchased(false);
        weapons.add(lightbulb);

        //Bat
        CIWeapon bat = new CIWeapon("Babe Ruth's bat","bat300x300",world);
        bat.setDamage(4);
        bat.setSpeed(1.2f);
        bat.setPurchased(false);
        weapons.add(bat);

        //knife
        CIWeapon knife = new CIWeapon("Miyagi's Knife","sword",world);
        knife.setDamage(4);
        knife.setSpeed(2);
        knife.setPurchased(false);
        weapons.add(knife);

        //pistol
        CIWeapon pistol = new CIWeapon("Clint's favorite","pistol200x200",world);
        pistol.setDamage(5);
        pistol.setSpeed(1.5f);
        pistol.setPurchased(false);
        weapons.add(pistol);

        //spikey
        CIWeapon spike = new CIWeapon("Carl XVI's nr. 1 toy","spikeball",world);
        spike.setDamage(7);
        spike.setSpeed(0.3f);
        spike.setPurchased(false);
        weapons.add(spike);
    }

    @Override
    public ArrayList<CIWeapon> getThrowables() {
        return weapons;
    }

    @Override
    public ArrayList<CIWeapon> getStoreItems() {
        return weapons;
    }
}
