package com.chicken.invasion.model;

import com.chicken.invasion.physics.ICollisionRect;

import java.util.Random;

/**
 * Created by Kristoffer on 2016-08-17.
 */

public class Enemy extends GameObject {

    private boolean pushable = false;
    private int pointsForKilling;


    public Enemy(ICollisionRect rectangle, String image, int health, boolean pushable) {
        super(rectangle);
        this.imageURL = image;
        this.hp = pointsForKilling = health;
        this.pushable = pushable;
        //Place enemy random on the x-axis
        Random rand = new Random();
        x = (rand.nextFloat()*(GameModel.getInstance().getWidth()/120) + 0.5f);
        if (x>12){ x-=1; }
        y = GameModel.getInstance().getHeight() / 190;

        width = 2.0f;
        height = 2.0f;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        x += -dt/7;
        y += -dt/1.2f;

        width += dt/3f;
        height += dt/3f;
    }

    void pushBack() {
        if (this.pushable){
            this.y ++;
        }
    }

    public int getPointsForKilling(){ return pointsForKilling; }

    public Enemy clone(){
        Enemy e = new Enemy(this.getCollisionRect().clone(), this.getImageURL(), this.getHP(), pushable);
        e.setSpeed(this.getSpeed());

        return e;
    }
}
