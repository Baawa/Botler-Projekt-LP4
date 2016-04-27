package com.chicken.invasion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by Albin on 2016-04-26.
 */
public class ThrowableObject{
    private Texture image;
    public Sprite sprite;
    private String name;
    private double speed = 1;
    private int damage;
    private Boolean collided = false;
    private Boolean thrown = false;
    private int rotation = 0;

    private Body body;
    private World world;
    private java.util.ArrayList<ThrowableObject> parentArray;

    /*
    private double x;
    private double y;
    private double width = 100;
    private double height = 100;*/
    private int screenWidth;
    private int screenHeight;

    public ThrowableObject(int x, int y, int screenWidth, int screenHeight, String name, Texture image, double speed, int damage, World world, ArrayList<ThrowableObject> parentArray) {
        //this.x = x;
        //this.y = y;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.name = name;
        this.image = image;
        this.speed = speed;
        this.damage = damage;

        this.sprite = new Sprite(image);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);

        this.world = world;

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = body.createFixture(fixtureDef);

        circle.dispose();

        body.setUserData(this.sprite);

        this.parentArray = parentArray;
        this.parentArray.add(this);

        this.rotation = 2;

    }

    public void currPoss(){
        System.out.println(body.getPosition());
    }

    public void throwToPoint(int x, int y){
        if (!thrown){
            int velocityX = (int)(speed * x * 300000);
            int velocityY = (int)(speed * y * 300000);

            body.applyForceToCenter(velocityX,velocityY,true);
            //body.setLinearVelocity(velocityX,velocityY);

            thrown = true;
        }
    }

    public void updateGraphics(SpriteBatch batch){
        this.sprite.setPosition(this.body.getPosition().x, this.body.getPosition().y);

        if (thrown) {
            //this.sprite.setRotation(this.sprite.getRotation() + 1);
            this.sprite.rotate(this.rotation);
            this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);

            int height = Gdx.graphics.getHeight();

            float scale = (float) (1 / (0.1 * (this.sprite.getY() / (height * 10)) + 1));
            this.sprite.setSize(this.sprite.getWidth() * scale, this.sprite.getHeight() * scale);
        }

        this.sprite.draw(batch);

        if(this.body.getPosition().y > Gdx.graphics.getHeight()){
            this.parentArray.remove(this);
            thrown = false;
            this.world.destroyBody(this.body);
            sprite = null;

        }
    }

    public void throwToPoint(final double x, final double y) {
        /*Thread throwThread = new Thread() {
            private int time = 0;
            private double scale = 1.0;
            /*private double angle = Math.atan(y / x);
            private double gravity = 10.0;
            private double totalLength = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            private double initialVelocity = 200.0 * getSpeed();
            private double VelocityX = initialVelocity * (x / totalLength);
            private double VelocityY = initialVelocity * (y / totalLength);
            private int sleepTime = 10; //millis

            public void run() {
                while (true) {
                    time += sleepTime;

                    setPosition((int) VelocityX * time / 1000, (int) VelocityY * time / 1000);

                    scale = (getScreenHeight() - getY()) / getScreenHeight();
                    setWidth((float) ((float)getWidth() * scale));
                    setHeight((float) ((float)getHeight() * scale));

                    System.out.println("TIME:" + time);
                    System.out.println("X:" + getX());
                    System.out.println("Y:" + getY());
                    System.out.println("_________________");

                    try {
                        sleep(sleepTime, 0);
                    } catch (InterruptedException e) {
                        //Log.e("","interrupted");
                    }
                    if (hasCollided() || getX() > getScreenWidth() || getY() > getScreenHeight())
                        break;
                }

            }
        };
        throwThread.run();
        /*try {
            throwThread.join();
        } catch (InterruptedException e) {
        }*/
    }

    public void onCollison(){
        if (hasCollided() == false){
            if (damage == 1){
                setCollided(true);
            } else{
                damage -= 1;
            }
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
    /*
    public void setX(double x){
        this.x = (int)x;
    }

    public void setY(double y){
        this.y = (int)y;
    }
    */
    public float getX(){
        return this.body.getPosition().x;
    }
    public float getY(){
        return this.body.getPosition().y;
    }
    /*
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
*/
    public int getScreenWidth(){
        return this.screenWidth;
    }

    public int getScreenHeight(){
        return this.screenHeight;
    }

}
