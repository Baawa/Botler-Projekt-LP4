package com.chicken.invasion;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Albin on 2016-04-26.
 */
public class ThrowableObject {
    private Texture image;
    private String name;
    private double speed = 1;
    private int damage;
    private Boolean collided = false;

    private double x;
    private double y;
    private double width = 100;
    private double height = 100;
    private int screenWidth;
    private int screenHeight;

    public ThrowableObject(int x, int y, int screenWidth, int screenHeight, String name, Texture image, double speed, int damage) {
        this.x = x;
        this.y = y;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.name = name;
        this.image = image;
        this.speed = speed;
        this.damage = damage;
    }

    public void throwToPoint(final double x, final double y) {
        Thread throwThread = new Thread() {
            private int time = 0;
            private double scale = 1.0;
            /*private double angle = Math.atan(y / x);
            private double gravity = 10.0;*/
            private double totalLength = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            private double initialVelocity = 200.0 * getSpeed();
            private double VelocityX = initialVelocity * (x / totalLength);
            private double VelocityY = initialVelocity * (y / totalLength);
            private int sleepTime = 10; //millis

            public void run() {
                while (true) {
                    time += sleepTime;
                    setX(VelocityX * time / 1000);
                    setY(VelocityY * time / 1000);

                    scale = (getScreenHeight() - getY()) / getScreenHeight();
                    setWidth(getWidth() * scale);
                    setHeight(getHeight() * scale);


                    System.out.println("TIME:" + time);
                    System.out.println("X:" + getX());
                    System.out.println("Y:" + getY());
                    System.out.println("_________________");

                    try {
                        sleep(sleepTime, 0);
                    } catch (InterruptedException e) {
                        //Log.e("","interrupted");
                    }
                    if (getHasCollided() || getX() > getScreenWidth() || getY() > getScreenHeight())
                        break;
                }

            }
        };
        throwThread.run();
        try {
            throwThread.join();
        } catch (InterruptedException e) {
        }
    }

    public String getName(){
        return name;
    }

    public double getSpeed(){
        return speed;
    }
    public int getDamage(){
        return damage;
    }

    public Texture getImage(){
        return this.image;
    }

    public void setCollided(Boolean collided){
        this.collided = collided;
    }

    public Boolean hasCollided(){
        return this.collided;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public void setWidth(double width){
        this.width = width;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public double getWidth(){
        return this.width;
    }

    public double getHeight(){
        return this.height;
    }

    public int getScreenWidth(){
        return this.screenWidth;
    }

    public int getScreenHeight(){
        return this.screenHeight;
    }

}
