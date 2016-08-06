package com.chicken.invasion.Helpers;

import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

/**
 * Created by Albin on 2016-08-06.
 */
public class CICollisionRect implements CollisionRect{
    private Rectangle rect;

    public void setCollisionRect(Rectangle rect){
        this.rect = rect;
    }

    public Rectangle getCollisionRect(){
        return this.rect;
    }

    public Boolean overlaps(CollisionRect r){
        if (r.getClass() == this.getClass()) {
            CICollisionRect tmp = (CICollisionRect) r;
            return this.rect.overlaps(tmp.getCollisionRect());
        }

        return false;
    }
}
