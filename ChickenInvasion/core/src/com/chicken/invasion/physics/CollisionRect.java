package com.chicken.invasion.physics;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Albin on 2016-08-06.
 */
public class CollisionRect implements ICollisionRect {
    private Rectangle rect;

    public CollisionRect(Rectangle rect){
        this.rect = rect;
    }

    public void update(float x, float y, float width, float height){
        rect.set(x,y,width,height);
    }

    public Rectangle getCollisionRect(){
        return this.rect;
    }

    public boolean overlaps(ICollisionRect r){
        if (r.getClass() == this.getClass()) {
            CollisionRect tmp = (CollisionRect) r;
            return this.rect.overlaps(tmp.getCollisionRect());
        }
        return false;
    }

    @Override
    public ICollisionRect clone() {
        return new CollisionRect(new Rectangle(this.rect.getX(),this.rect.getY(),this.rect.getWidth(),this.rect.getHeight()));
    }
}
