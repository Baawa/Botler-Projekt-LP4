package com.chicken.invasion;

import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kristoffer on 2016-05-22.
 */
public class ThrowableHolder implements iHolder {
    private List<ThrowableObject> throwables;

    public ThrowableHolder(ChickenInvasion controller, World world){
        throwables = new ArrayList<ThrowableObject>();

        //Beach ball
        ThrowableObject beachBall = new ThrowableObject("Gatsby's beach ball","beachball200x200",world, controller.getPlayer());
        beachBall.setDamage(1);
        beachBall.setSpeed(1.2);
        beachBall.setRotationSpeed(0.5f);
        beachBall.setPurchased(true);
        throwables.add(beachBall);

        //light bulb
        ThrowableObject lightbulb = new ThrowableObject("Edison's light bulb","lightbulb300x300",world,controller.getPlayer());
        lightbulb.setDamage(2);
        lightbulb.setSpeed(0.9);
        lightbulb.setRotationSpeed(0.5f);
        lightbulb.setPurchased(false);
        throwables.add(lightbulb);

        //Bat
        ThrowableObject bat = new ThrowableObject("Babe Ruth's bat","bat300x300",world,controller.getPlayer());
        bat.setDamage(4);
        bat.setSpeed(1.2);
        bat.setRotationSpeed(0.5f);
        bat.setPurchased(false);
        throwables.add(bat);

        //knife
        ThrowableObject knife = new ThrowableObject("Miyagi's Knife","sword",world,controller.getPlayer());
        knife.setDamage(4);
        knife.setSpeed(2);
        knife.setRotationSpeed(0.5f);
        knife.setPurchased(false);
        throwables.add(knife);

        //pistol
        ThrowableObject pistol = new ThrowableObject("Clint's favorite","pistol200x200",world,controller.getPlayer());
        pistol.setDamage(5);
        pistol.setSpeed(1.5);
        pistol.setRotationSpeed(1.0f);
        pistol.setPurchased(false);
        throwables.add(pistol);

        //spikey
        ThrowableObject spike = new ThrowableObject("Carl XVI's nr. 1 toy","spikeball",world,controller.getPlayer());
        spike.setDamage(7);
        spike.setSpeed(0.3f);
        spike.setRotationSpeed(0.3f);
        spike.setPurchased(false);
        throwables.add(spike);

        //Cash Money
        ThrowableObject cashM = new ThrowableObject("Cash Money","moneybag200x200",world,controller.getPlayer());
        cashM.setDamage(7);
        cashM.setSpeed(1.5f);
        cashM.setRotationSpeed(0.6f);
        cashM.setPurchased(false);
        throwables.add(cashM);

    }

    @Override
    public List<ThrowableObject> getThrowables() {
        return throwables;
    }


}
