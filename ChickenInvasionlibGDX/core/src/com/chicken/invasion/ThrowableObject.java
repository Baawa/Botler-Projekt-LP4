package com.chicken.invasion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Albin on 2016-04-26.
 */
public class ThrowableObject{
    private Texture image;
    private Sprite sprite;
    private String name;
    private int price = 0;
    private double speed = 1;
    private double damage = 1;
    private float rotation = 1.0f;
    private Boolean collided = false;
    private Boolean thrown = false;
    private float scale = 1.0f;
    private float orgWidth;
    private float orgHeight;
    private Rectangle collideRect;

    private Player player;

    private Body body;
    private World world;

    public ThrowableObject(ThrowableObject to){
        this.player = to.getPlayer();

        this.name = to.getName();
        this.speed = to.getSpeed();
        this.damage = to.getDamage();
        this.scale = to.getScale();
        this.sprite = to.copySprite();
        this.sprite.setSize(to.getOriginalWidth(), to.getOriginalHeight());
        this.image = this.sprite.getTexture();

        this.orgWidth = to.getOriginalWidth();
        this.orgHeight = to.getOriginalHeight();

        this.sprite.setRotation(0);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Gdx.graphics.getWidth()/200,0);

        this.world = to.getWorld();

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(this.sprite.getWidth()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);

        circle.dispose();

        body.setUserData(this.sprite);

        this.sprite.setPosition(this.body.getPosition().x, this.body.getPosition().y);

        collideRect = this.sprite.getBoundingRectangle();
    }

    public ThrowableObject(float scale, String name, Texture image, World world, Player player) {

        this.player = player;

        this.name = name;
        this.image = image;
        this.scale = scale;

        this.sprite = new Sprite(image);
        this.sprite.setSize(this.sprite.getWidth() / this.scale, this.sprite.getHeight() / this.scale);

        this.orgWidth = this.sprite.getWidth();
        this.orgHeight = this.sprite.getHeight();

        this.world = world;

        this.body = null;

    }

    public ThrowableObject(int x, int y, float scale, String name, Texture image, World world, Player player) {

        this.player = player;

        this.name = name;
        this.image = image;
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

        body.createFixture(fixtureDef);

        circle.dispose();

        body.setUserData(this.sprite);

        collideRect = sprite.getBoundingRectangle();

    }

    public String currPoss(){
        String tmp = "Current Position: " + body.getPosition();
        return tmp;
    }

    public void throwToPoint(float x, float y){
        if (!thrown){
            int velocityX = (int)(speed * x * 1000);
            int velocityY = (int)(speed * y * 1000);

            body.applyForceToCenter(velocityX, velocityY, true);

            thrown = true;
        }
    }

    public void drawOnly(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void updateGraphics(SpriteBatch batch){
        this.sprite.setPosition(this.body.getPosition().x, this.body.getPosition().y);

        if (thrown) {
            this.sprite.rotate(this.rotation);
            this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);

            int height = Gdx.graphics.getHeight();

            float scale = (float) (1 / (5 * this.scale * (this.sprite.getY() / height) + 1));
            this.sprite.setSize(this.orgWidth * scale, this.orgHeight * scale);

            collideRect = sprite.getBoundingRectangle();
        }

        this.sprite.draw(batch);

        if(this.body.getPosition().y > Gdx.graphics.getHeight()/100 || this.body.getPosition().x < 0 || this.body.getPosition().x > Gdx.graphics.getWidth()/100 || collided){
            thrown = false;
            this.world.destroyBody(this.body);
            player.removeTO();
            sprite = null;
        }
    }

    public void onCollison(){
        if (damage <= 1){
            setCollided(true);
        } else{
            damage -= 1;
        }
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public void setDamage(double damage){
        this.damage = damage;
    }

    public void setRotationSpeed(float rotationSpeed){
        this.rotation = rotationSpeed;
    }

    public String getName(){
        return name;
    }

    public double getSpeed(){
        return speed;
    }

    public double getDamage(){
        return damage;
    }

    public float getRotationSpeed(){
        return this.rotation;
    }

    public Rectangle getCollideRect() {
        return collideRect;
    }

    public Texture getImage(){
        return this.image;
    }

    public void setCollided(Boolean collided){
        this.collided = collided;
    }

    public float getX(){
        return this.body.getPosition().x;
    }

    public float getY(){
        return this.body.getPosition().y;
    }

    public float getOriginalWidth(){
        return this.orgWidth;
    }

    public float getOriginalHeight(){
        return this.orgHeight;
    }

    public float getScale(){
        return this.scale;
    }

    public World getWorld(){
        return this.world;
    }

    public Player getPlayer(){
        return this.player;
    }

    public boolean isThrown(){ return thrown; }

    public Sprite copySprite(){
        return new Sprite(this.sprite);
    }

    public int getPrice(){
        int tmp = (int)(this.damage * this.speed * 100);
        return tmp;
    }

    public void dispose(){
        image.dispose();
    }

}
