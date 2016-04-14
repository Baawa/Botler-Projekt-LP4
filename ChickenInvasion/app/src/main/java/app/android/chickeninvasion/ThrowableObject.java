package app.android.chickeninvasion;

import android.media.Image;
import android.util.Log;
import android.view.MotionEvent;

public class ThrowableObject {
    private String name;
    private double speed = 1; //Speed 1 -> Normal speed, 2 -> Double Normal speed
    private int damage;
    private Image image;
    private Boolean hasCollided = false;

    private double x;
    private double y;
    private double width = 100;
    private double height = 100;
    private int screenWidth;
    private int screenHeight;

    public ThrowableObject(int x, int y, int screenWidth, int screenHeight){
        this.x = x;
        this.y = y;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void throwToPoint(final double x, final double y){
        Thread throwThread = new Thread(){
            private int time = 0;
            private double scale = 1.0;
            /*private double angle = Math.atan(y / x);
            private double gravity = 10.0;*/
            private double totalLength = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
            private double initialVelocity = 200.0*getSpeed();
            private double VelocityX = initialVelocity * (x/totalLength);
            private double VelocityY = initialVelocity * (y/totalLength);
            private int sleepTime = 10; //millis
            public void run(){
                while(true){
                    time += sleepTime;
                    setX(VelocityX * time / 1000);
                    setY(VelocityY * time / 1000);

                    scale = (getScreenHeight()-getY())/getScreenHeight();
                    setWidth(getWidth() * scale);
                    setHeight(getHeight() * scale);

                    /*
                    System.out.println("TIME:" + time);
                    System.out.println("X:" + getX());
                    System.out.println("Y:" + getY());
                    System.out.println("_________________");
                    */
                    try{
                        sleep(sleepTime,0);
                    } catch (InterruptedException e){

                        Log.e("","interrupted");
                    }
                    if (getHasCollided()|| getX() > getScreenWidth()|| getY() > getScreenHeight()) break;
                }

            }
        };
        throwThread.run();
        try{
            throwThread.join();
        } catch (InterruptedException e){}
    }

    public void onTouchEvent(MotionEvent event){
        double x = event.getX();
        double y = event.getY();
        double total = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        x = x + 100*x/total;
        y = y + 100*y/total;

        x = x * this.speed * x / total;
        y = y * this.speed * y / total;
        this.throwToPoint(x,y);
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

    public Image getImage(){
        return image;
    }

    public void setCollided(Boolean collided){
        this.hasCollided = collided;
    }

    public Boolean getHasCollided(){
        return this.hasCollided;
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