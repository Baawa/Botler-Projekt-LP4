package com.chicken.invasion.controller.collections;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.chicken.invasion.model.Weapon;
import com.chicken.invasion.physics.Body;
import com.chicken.invasion.physics.CollisionRect;

import java.util.ArrayList;

/**
 * Created by Albin on 2016-08-06.
 */
public class WeaponCollection {
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();

    public WeaponCollection(World world){
        //Beach ball
        Weapon beachBall = new Weapon(new CollisionRect(new Rectangle()),"Gatsby's beach ball","beachball200x200", new Body(world));
        beachBall.setDamage(1);
        beachBall.setSpeed(1.2f);
        beachBall.setPurchased(true);
        weapons.add(beachBall);

        //light bulb
        Weapon lightbulb = new Weapon(new CollisionRect(new Rectangle()),"Edison's light bulb","lightbulb300x300", new Body(world));
        lightbulb.setDamage(2);
        lightbulb.setSpeed(0.9f);
        lightbulb.setPurchased(false);
        weapons.add(lightbulb);

        //Bat
        Weapon bat = new Weapon(new CollisionRect(new Rectangle()),"Babe Ruth's bat","bat300x300", new Body(world));
        bat.setDamage(4);
        bat.setSpeed(1.2f);
        bat.setPurchased(false);
        weapons.add(bat);

        //knife
        Weapon knife = new Weapon(new CollisionRect(new Rectangle()),"Miyagi's Knife","sword", new Body(world));
        knife.setDamage(4);
        knife.setSpeed(2);
        knife.setPurchased(false);
        weapons.add(knife);

        //pistol
        Weapon pistol = new Weapon(new CollisionRect(new Rectangle()),"Clint's favorite","pistol200x200", new Body(world));
        pistol.setDamage(5);
        pistol.setSpeed(1.5f);
        pistol.setPurchased(false);
        weapons.add(pistol);

        //spikey
        Weapon spike = new Weapon(new CollisionRect(new Rectangle()),"Carl XVI's nr. 1 toy","spikeball", new Body(world));
        spike.setDamage(7);
        spike.setSpeed(0.3f);
        spike.setPurchased(false);
        weapons.add(spike);
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }
}
