package com.chicken.invasion.Enemy_Throwable;

/**
 * Created by Albin on 2016-08-06.
 */
public interface Enemy {
    void decHP(float amount);
    float getHP();

    float getX();
    float getY();

    Object getImage();
}
