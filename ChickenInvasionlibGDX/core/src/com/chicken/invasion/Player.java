package com.chicken.invasion;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Albin on 2016-05-06.
 */
public class Player {
    private ArrayList<ThrowableObject> throwables = new java.util.ArrayList<ThrowableObject>();
    private int score;
    private int chickenWings;
    private ThrowableObject equippedTO;

    public void addThrowables(int amount){
        for (int i = 0; i < amount; i++){
            ThrowableObject to = new ThrowableObject(this.equippedTO);
            throwables.add(to);
        }
    }

    public void draw(SpriteBatch batch){
        if (throwables.size() > 0){
            throwables.get(0).updateGraphics(batch);
        }
    }

    public void drawOnly(SpriteBatch batch){
        if (throwables.size() > 0){
            throwables.get(0).drawOnly(batch);
        }
    }

    public void setEquippedTO(ThrowableObject to){
        this.equippedTO = to;
    }

    public ArrayList<ThrowableObject> getThrowables(){
        return this.throwables;
    }

    public ThrowableObject getCurrentTO(){
        if (throwables.size() > 0){
            return throwables.get(0);
        } else{
            return null;
        }
    }

    public void removeTO(){
        this.throwables.remove(0);
    }

}
