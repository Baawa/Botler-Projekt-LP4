package com.chicken.invasion;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pedramshirmohammad on 16-04-26.
 */
public class Weapon {

    ArrayList<ThrowableObject> vapen;
    HashMap<String,Boolean> availability;

    public Weapon(){
        availability = new HashMap<>();
        ThrowableObject shtekpanna = new ThrowableObject(100,"Pan",new Texture("pan300x300.png"),null,null);
        shtekpanna.setDamage(5);
        ThrowableObject bat2 = new ThrowableObject(100,"Bat",new Texture("bat300x300.png"),null,null);



        vapen = new ArrayList<ThrowableObject>();
        vapen.add(shtekpanna);
        vapen.add(bat2);

        for (ThrowableObject e: vapen){
            availability.put(e.getName(),false);
        }
    }

    public ArrayList<ThrowableObject> getVapen() {
        return vapen;
    }

    public HashMap<String, Boolean> getAvailability() {
        return availability;
    }
}
