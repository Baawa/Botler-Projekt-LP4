package com.chicken.invasion.Enemy_Throwable;

import com.chicken.invasion.Helpers.CollisionRect;

/**
 * Created by Albin on 2016-08-06.
 */
public interface Throwable {
    void decHP(float amount);
    float getHP();

    void setX(float x);
    void setY(float y);

    float getX();
    float getY();

    float getRotation();

    float getDamage();

    Object getSprite();

    void setCollided(Boolean collided);
    Boolean hasCollided();

    void ThrowToPoint(float x, float y);

    void updatePosition();

    float getScale();

    CollisionRect getCollisionRect();

    void removeFromWorld();

    void removeSprite();
}
