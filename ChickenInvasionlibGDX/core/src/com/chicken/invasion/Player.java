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
    private int throwablesAmount = 0;

    public void addThrowables(int amount){
        throwablesAmount += amount;
        if (throwables.size() == 0){
            ThrowableObject to = new ThrowableObject(this.equippedTO);
            throwables.add(0, to);
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

        System.out.println(throwables);
        if (throwables.size() > 0){
            this.throwables.remove(0);
            System.out.println(throwables);
            throwablesAmount -= 1;
            if (throwablesAmount > 0){
                ThrowableObject to = new ThrowableObject(this.equippedTO);
                System.out.println(throwables);
                throwables.add(0, to);
                System.out.println("TO removed. new Position: " + this.throwables.get(0).getX() + " , " + this.throwables.get(0).getY());
            }

        }
    }

}
