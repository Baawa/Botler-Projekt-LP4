package com.chicken.invasion;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Albin on 2016-05-06.
 */
public class Player {
    private ArrayList<ThrowableObject> throwables = new java.util.ArrayList<ThrowableObject>();
    private int score= 0;
    private int chickenWings = 0;
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

        if (throwables.size() > 0){
            this.throwables.remove(0);
            throwablesAmount -= 1;
            if (throwablesAmount > 0){
                ThrowableObject to = new ThrowableObject(this.equippedTO);
                throwables.add(0, to);
                System.out.println("TO removed. new Position: " + this.throwables.get(0).getX() + " , " + this.throwables.get(0).getY());
            }

        }
    }

    public void incScore(int points){
        score += points;
        addChickenWings(points);
    }
    public int getScore(){ return score; }

    public void addChickenWings(int amount){ chickenWings += amount; }

    public int getChickenWings(){
        return chickenWings;
    }

    public void saveScore(){
        //TODO spara om det är nytt highscore

    }

    public void resetScore(){ score = 0; }

    public void dispose(){
        for (ThrowableObject to : throwables){
            to.dispose();
        }
    }

}
