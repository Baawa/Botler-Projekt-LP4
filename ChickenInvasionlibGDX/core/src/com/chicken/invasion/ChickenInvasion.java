package com.chicken.invasion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

public class ChickenInvasion extends ApplicationAdapter implements GestureDetector.GestureListener{
	SpriteBatch batch;
	Texture img;
	ThrowableObject pan;
	World world;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		this.world = new World(new Vector2(0, 0), true);

		pan = new ThrowableObject(0,0,300,300,"Pan",new Texture("pan100x100.png"),20000000.0,1, this.world);

		Gdx.input.setInputProcessor(new GestureDetector(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		//pan.sprite.draw(batch);
		//batch.draw(pan.getImage(),pan.x,pan.y);
		Array<Body> bodies = new Array<Body>();
		// Now fill the array with all bodies
		world.getBodies(bodies);

		for (Body b : bodies) {
			Sprite s = (Sprite) b.getUserData();

			if (s != null){
				s.setPosition(b.getPosition().x,b.getPosition().y);
				s.draw(batch);
			}
		}
		batch.end();

		world.step(1 / 400f, 6, 2);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {

		if ((x >= pan.getX() || x <= pan.getX()+100) && (y >= pan.getY() || y <= pan.getY()+100)){
			int throwX = (int)deltaX * 1000000000;
			int throwY = (int)-deltaY * 1000000000;
			pan.throwToPoint(throwX,throwY);

		}
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}
