package com.chicken.invasion.model;

import java.util.concurrent.Callable;

/**
 * Created by Kristoffer on 2016-05-02.
 */
public class Button {
    private Callable<Void> func;
    private float x, y, width, height;
    private String imgURL;

    public Button(Callable<Void> func, float width, float height, String imgURL){
        this.func = func;
        this.width = width;
        this.height = height;
        this.imgURL = imgURL;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float x) { this.x = x; }

    public void setY(float y) {
        this.y = y;
    }

    public String getImgURL() { return imgURL; }

    public void clicked(float x, float y){
        if ((x >= this.getX() && x <= this.getX()+getWidth()) && (y >= this.getY() && y <= this.getY()+this.getHeight())){
            System.out.println("Clicked!");
            touchFunc();
        }
    }

    private void touchFunc(){
        try {
            this.func.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
