package com.chicken.invasion.physics;

import com.chicken.invasion.model.Weapon;

/**
 * Created by Kristoffer on 2016-08-17.
 */

public interface IBody {

    void setLinearVelocity(float x, float y);

    float getX();
    float getY();

    void resetBody();

    IBody clone();
}
