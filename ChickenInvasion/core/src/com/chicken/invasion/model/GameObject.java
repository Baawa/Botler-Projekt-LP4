package com.chicken.invasion.model;

import com.chicken.invasion.physics.ICollisionRect;

/**
 * Created by Kristoffer on 2016-08-17.
 */

abstract class GameObject {

    private ICollisionRect rectangle;

    GameObject(ICollisionRect rectangle){
        this.rectangle = rectangle;
    }

    float x, y, height, width, speed;
    int hp;
    String imageURL;

    void decHP(int amount) {
        this.hp -= amount;
    }

    int getHP(){ return hp;}

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return this.speed;
    }

    public String getImageURL(){ return this.imageURL; }

    public ICollisionRect getCollisionRect(){ return rectangle; }

    public void update(float dt){
        rectangle.update(x,y,width,height);
    }



}
