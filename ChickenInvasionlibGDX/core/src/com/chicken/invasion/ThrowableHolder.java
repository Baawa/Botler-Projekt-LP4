package com.chicken.invasion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kristoffer on 2016-05-22.
 */
public class ThrowableHolder {
    private List<ThrowableObject> throwables;

    public ThrowableHolder(ChickenInvasion controller, World world){
        throwables = new ArrayList<ThrowableObject>();

        //Beach ball
        ThrowableObject beachBall = new ThrowableObject("Beach Ball","beachball200x200",world, controller.getPlayer());
        beachBall.setDamage(1);
        beachBall.setSpeed(1.2);
        beachBall.setRotationSpeed(0.5f);
        beachBall.setPurchased(true);
        throwables.add(beachBall);

        //Pan
        ThrowableObject stekpanna = new ThrowableObject("Pan","pan100x100",world,controller.getPlayer());
        stekpanna.setDamage(2);
        stekpanna.setSpeed(0.9);
        stekpanna.setRotationSpeed(0.5f);
        stekpanna.setPurchased(false);
        throwables.add(stekpanna);

        //Bat
        ThrowableObject bat = new ThrowableObject("Bat","bat300x300",world,controller.getPlayer());
        bat.setDamage(4);
        bat.setSpeed(1.2);
        bat.setRotationSpeed(0.5f);
        bat.setPurchased(false);
        throwables.add(bat);
    }

    public List<ThrowableObject> getThrowables() {
        return throwables;
    }
}
