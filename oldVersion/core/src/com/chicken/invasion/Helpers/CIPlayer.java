package com.chicken.invasion.Helpers;

import com.chicken.invasion.*;
import com.chicken.invasion.Enemy_Throwable.*;
import com.chicken.invasion.Enemy_Throwable.Throwable;
import com.chicken.invasion.Weapons.WeaponCollection;

/**
 * Created by Albin on 2016-08-13.
 */
public class CIPlayer implements Player {
    private int chickenWings;
    private int score;
    private Throwable_Object throwable;

    public CIPlayer(Throwable_Object throwable){
        this.throwable = throwable;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void setChickenLegs(int chickenWings) {
        this.chickenWings = chickenWings;
    }

    @Override
    public int getChickenLegs() {
        return this.chickenWings;
    }

    @Override
    public void setEquippedThrowable(Throwable throwable) {
        if (throwable.getClass() == Throwable_Object.class){
            Throwable_Object tmp = (Throwable_Object)throwable;
            this.throwable = tmp.clone();
        }
    }

    @Override
    public Throwable getEquippedThrowable() {
        return this.throwable.clone();
    }
}
