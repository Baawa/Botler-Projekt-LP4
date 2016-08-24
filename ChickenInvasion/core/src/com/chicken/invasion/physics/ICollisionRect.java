package com.chicken.invasion.physics;

/**
 * Created by Albin on 2016-08-06.
 */
public interface ICollisionRect {

    void update(float x, float y, float width, float height);

    boolean overlaps(ICollisionRect r);

    ICollisionRect clone();
}
