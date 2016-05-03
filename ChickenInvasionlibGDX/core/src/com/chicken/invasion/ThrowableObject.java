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
    private float scale = 1.0f;
    private float orgWidth;
    private float orgHeight;
    private Rectangle collideRect;

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

    public ThrowableObject(int x, int y, float scale, String name, Texture image, double speed, int damage, World world, ArrayList<ThrowableObject> parentArray) {
        //this.x = x;
        //this.y = y;
        //this.screenWidth = screenWidth;
        //this.screenHeight = screenHeight;

        this.name = name;
        this.image = image;
        this.speed = speed;
        this.damage = damage;
        this.scale = scale;

        this.sprite = new Sprite(image);
        this.sprite.setSize(this.sprite.getWidth()/this.scale,this.sprite.getHeight()/this.scale);

        this.orgWidth = this.sprite.getWidth();
        this.orgHeight = this.sprite.getHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);

        this.world = world;

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(this.sprite.getWidth());

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

        collideRect = sprite.getBoundingRectangle();

    }

    public void currPoss(){
        System.out.println(body.getPosition());
    }

    public void throwToPoint(float x, float y){
        if (!thrown){
            int velocityX = (int)(speed * x * 1000);
            int velocityY = (int)(speed * y * 1000);

            body.applyForceToCenter(velocityX, velocityY, true);

            //body.setLinearVelocity(velocityX,velocityY);

            thrown = true;
        }
    }

    public void drawOnly(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void updateGraphics(SpriteBatch batch){
        this.sprite.setPosition(this.body.getPosition().x, this.body.getPosition().y);

        if (thrown) {
            //this.sprite.setRotation(this.sprite.getRotation() + 1);
            this.sprite.rotate(this.rotation);
            this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);

            int height = Gdx.graphics.getHeight();

            float scale = (float) (1 / (5 * this.scale * (this.sprite.getY() / height) + 1));
            this.sprite.setSize(this.orgWidth * scale, this.orgHeight * scale);

            collideRect = sprite.getBoundingRectangle();
        }

        this.sprite.draw(batch);

        if(this.body.getPosition().y > Gdx.graphics.getHeight() || collided){
            this.parentArray.remove(this);
            thrown = false;
            this.world.destroyBody(this.body);
            sprite = null;
            image.dispose();
        }
    }

    public Rectangle getCollideRect() {
        return collideRect;
    }

    public void onCollison(){
        if (collided){
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