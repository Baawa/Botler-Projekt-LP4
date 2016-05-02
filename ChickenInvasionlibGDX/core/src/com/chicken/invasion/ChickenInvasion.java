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

import java.util.ArrayList;

public class ChickenInvasion extends ApplicationAdapter implements GestureDetector.GestureListener{
	SpriteBatch batch;
	Sprite backgroundimg;
	ThrowableObject pan;
	World world;
	ArrayList<ThrowableObject> throwables = new ArrayList<ThrowableObject>();
    ArrayList<Chicken> chickens;

	Camera camera;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth()/100,Gdx.graphics.getHeight()/100);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Texture backgroundtexture = new Texture("desertbackground500x900.png");
		backgroundimg = new Sprite(backgroundtexture);
		backgroundimg.setPosition(0, 0);
		backgroundimg.setSize(Gdx.graphics.getWidth() / 100, Gdx.graphics.getHeight() / 100);

		this.world = new World(new Vector2(0, 0), true);

		pan = new ThrowableObject(Gdx.graphics.getWidth()/200,0,100,"Pan",
                new Texture("bat300x300.png"),3.0,1, this.world, this.throwables);

        chickens = new ArrayList<Chicken>();
        chickens.add(new Chicken());


		Gdx.input.setInputProcessor(new GestureDetector(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		backgroundimg.draw(batch);

		for (ThrowableObject t : throwables){
			t.updateGraphics(batch);
		}

        for (Chicken c : chickens){
            c.draw(batch);
        }

		batch.end();

		world.step(1 / 60f, 6, 2);
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

		if ((x >= pan.getX() || x <= pan.getX()+100) && (y >= pan.getY() || y <= pan.getY()+100) && -deltaY > 0){
			float throwX = deltaX;
			float throwY = -deltaY;
			float totalLength = (float)(Math.sqrt(Math.pow(throwX,2)+Math.pow(throwY,2)));
				if (throwY > 0){
				pan.throwToPoint(throwX/totalLength,throwY/totalLength);
			}

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
