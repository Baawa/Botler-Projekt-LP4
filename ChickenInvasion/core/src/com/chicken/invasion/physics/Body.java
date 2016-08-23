package com.chicken.invasion.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.chicken.invasion.model.Weapon;

/**
 * Created by Kristoffer on 2016-08-17.
 */

public class Body implements IBody {

    private com.badlogic.gdx.physics.box2d.Body body;
    private World world;

    FixtureDef fixtureDef;
    BodyDef bodyDef;

    public Body (World world){
        this.world = world;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(Gdx.graphics.getWidth()/(2*100f),0);
        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(3f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);
    }

    @Override
    public void setLinearVelocity(float x, float y) {
        body.setLinearVelocity(x,y);
    }

    @Override
    public float getX() {
        return body.getPosition().x;
    }

    @Override
    public float getY() {
        return body.getPosition().y;
    }

    @Override
    public void resetBody() {
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
    }

    @Override
    public IBody clone(){
        try{
            super.clone();
        } catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return new Body(this.world);
    }
}
