package com.chicken.invasion.Enemy_Throwable;

import com.chicken.invasion.Helpers.CollisionRect;

/**
 * Created by Albin on 2016-08-06.
 */
public interface Enemy {
    void decHP(float amount);
    float getHP();

    float getX();
    float getY();

    float getWidth();
    float getHeight();

    void setSpeed(float speed);
    float getSpeed();

    Object getSprite();

    void update(float dt);

    CollisionRect getCollisionRect();

    void pushBack();

    void dispose();
}
