package com.chicken.invasion.Enemy_Throwable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.chicken.invasion.Helpers.CICollisionRect;
import com.chicken.invasion.Helpers.CollisionRect;

/**
 * Created by Albin on 2016-08-06.
 */
public class Throwable_Object implements Throwable, Cloneable{
    private float x;
    private float y;

    private float hp;

    private Sprite sprite;

    private float rotation;

    private float speed;

    private float scale = 100f;

    private float damage;

    private Body body;

    private World world;

    private Boolean thrown = false;

    private Boolean collided = false;

    private CICollisionRect collRect;

    private float orgWidth;

    private float orgHeight;

    private String imageURL;

    public Throwable_Object(String imageURL, World world) {
        this.imageURL = imageURL;

        this.sprite = new Sprite(new Texture(imageURL+".png"));

        this.sprite.setSize(this.sprite.getWidth() / this.scale, this.sprite.getHeight() / this.scale);

        this.orgWidth = this.sprite.getWidth();
        this.orgHeight = this.sprite.getHeight();

        this.world = world;

        BodyDef bodyDef = new BodyDef();
        //bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Gdx.graphics.getWidth()/(2*this.scale),0);

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(this.sprite.getWidth());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);

        circle.dispose();

        body.setUserData(this.sprite);

        this.collRect = new CICollisionRect(sprite.getBoundingRectangle());
    }

    public void decHP(float amount) {
        hp -= amount;
    }

    public float getHP() {
        return hp;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return this.rotation;
    }

    public float getScale(){
        return this.getScale();
    }

    public void setDamage(float damage){
        this.damage = damage;
    }

    public float getDamage(){
        return this.damage;
    }

    public Object getSprite() {
        return this.sprite;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public void setCollided(Boolean collided) {
        this.collided = collided;
    }

    public Boolean hasCollided() {
        return this.collided;
    }

    public void throwToPoint(float x, float y) {
        if (!thrown){
            int velocityX = (int)(speed * x * 10);
            int velocityY = (int)(speed * y * 10);

            body.setLinearVelocity(velocityX,velocityY);

            //body.applyForceToCenter(velocityX, velocityY, true);

            thrown = true;
        }
    }

    public boolean isThrown(){
        return this.thrown;
    }

    public void updatePosition() {
        this.sprite.setPosition(this.body.getPosition().x, this.body.getPosition().y);
        this.x = this.sprite.getX();
        this.y = this.sprite.getY();

        if (thrown){
            this.sprite.rotate(this.rotation);
            this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);

            int height = Gdx.graphics.getHeight();

            float scale = (float) (1 / (5 * this.scale * (this.sprite.getY() / height) + 1));
            this.sprite.setSize(this.orgWidth * scale, this.orgHeight * scale);

            this.collRect.setCollisionRect(this.sprite.getBoundingRectangle());

        }

        if(this.body.getPosition().y > Gdx.graphics.getHeight()/100 || this.body.getPosition().x < 0 || this.body.getPosition().x > Gdx.graphics.getWidth()/100){
            collided = true;
        }
    }

    public CollisionRect getCollisionRect(){
        return this.collRect;
    }

    public void removeFromWorld(){
        this.world.destroyBody(this.body);
    }

    public void removeSprite(){
        sprite = null;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return this.speed;
    }

    public Throwable_Object clone(){
        Throwable_Object to = new Throwable_Object(this.imageURL, this.world);

        to.setSpeed(this.getSpeed());

        to.setDamage(this.getDamage());

        to.scale = this.scale;

        to.sprite.setPosition(this.getX(),this.getY());

        return to;
    }

    public void dispose(){
        this.sprite.getTexture().dispose();
        removeFromWorld();
        thrown = false;
        sprite = null;
    }

}