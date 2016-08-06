package com.chicken.invasion.Helpers;

import com.chicken.invasion.Enemy_Throwable.*;
import com.chicken.invasion.Enemy_Throwable.Throwable;

/**
 * Created by Albin on 2016-08-06.
 */
public interface Player {
    void setScore(int score);
    int getScore();

    void setChickenWings(int chickenWings);
    int getChickenWings(int chickenWings);

    void setEquippedThrowable(Throwable throwable);
    Throwable getEquippedThrowable(); // Remember to return a copied version of the Throwable!
}