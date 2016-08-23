package com.chicken.invasion.model;
import com.chicken.invasion.physics.IBody;
import com.chicken.invasion.physics.ICollisionRect;

/**
 * Created by Kristoffer on 2016-08-17.
 */

public class Weapon extends GameObject implements IStoreItem{
    private float scale = 100f;
    private float orgWidth, orgHeight;
    private int damage;
    private Boolean thrown = false;
    private Boolean collided = false;
    private IBody body;
    private String name;
    private boolean purchased = false;

    public Weapon(ICollisionRect rectangle, String name, String imageURL, IBody body) {
        super(rectangle);
        this.imageURL = imageURL;
        this.width = orgWidth = 3.0f;
        this.height = orgHeight =3.0f;
        this.body = body;
        this.name = name;
        this.x = body.getX();
        this.y = body.getY();
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public int getDamage(){
        return this.damage;
    }

    @Override
    public boolean isPurchased() {
        return purchased;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Override
    public int getPrice() {
        return (int)(this.getDamage() * this.getSpeed() * 100);
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public boolean hasCollided() {
        return collided;
    }

    public void throwToPoint(float x, float y) {
        if (!thrown){
            float velocityX = (speed * x * 10);
            float velocityY = (speed * y * 10);

            body.setLinearVelocity(velocityX,velocityY);
            thrown = true;
        }
    }

    public boolean isThrown(){
        return thrown;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.x = body.getX();
        this.y = body.getY();
        //System.out.println("TBX/ y: " + y);
        //Check if weapon is outside the screen
        if(this.body.getY() > GameModel.getInstance().getHeight()/100 || this.body.getX() < 0 || this.body.getX() > GameModel.getInstance().getWidth()/100){
            collided = true;
        }
    }

    public void resetBody(){
        body.resetBody();
    }
    public void setSpeed(float speed){
        this.speed = speed;
    }
    public float getSpeed(){
        return speed;
    }
    public float getScale(){ return scale;}
    public void setScale(float scale){
        this.scale = scale;
    }
    public float getOrgWidth(){
        return orgWidth;
    }
    public float getOrgHeight(){
        return orgHeight;
    }
    public void setSize(float width, float height){
        this.width = width;
        this.height = height;
    }

    public Weapon clone(){
        Weapon to = new Weapon(getCollisionRect(), this.name, this.imageURL, this.body);
        to.setSpeed(this.getSpeed());
        to.setDamage(this.getDamage());
        to.setScale(this.scale);
        return to;
    }
}
