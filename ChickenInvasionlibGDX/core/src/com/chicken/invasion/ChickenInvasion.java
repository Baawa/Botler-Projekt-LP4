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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ChickenInvasion extends ApplicationAdapter implements GestureDetector.GestureListener{
	SpriteBatch batch;
	Sprite backgroundimg;
	ThrowableObject pan;
	World world;
	ArrayList<ThrowableObject> throwables = new ArrayList<ThrowableObject>();

	GameButton startBtn;

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

		Gdx.input.setInputProcessor(new GestureDetector(this));

		/*
		ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
		style.up = new SpriteDrawable(new Sprite(new Texture("play200x200.png")));
		style.imageUp = new SpriteDrawable(new Sprite(new Texture("play200x200.png")));
		style.unpressedOffsetY = -20; // to "not" center the icon
		style.unpressedOffsetX = -30; // to "not" center the icon
		*/

		startBtn = new GameButton(new Callable<Void>() {
			public Void call() throws Exception {
				startGame();
				return null;
			}
		}, new Texture("play200x200.png"));
		startBtn.setSize(200/100,200/100);
		startBtn.setX(Gdx.graphics.getWidth() / 200 - 2);
		startBtn.setY(Gdx.graphics.getHeight() / 100 - 2);
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

		startBtn.draw(batch);

		batch.end();

		world.step(1 / 60f, 6, 2);
	}

	public void startGame(){
		spawnThrowable();
	}

	private void spawnThrowable(){
		pan = new ThrowableObject((int)Gdx.graphics.getWidth()/200,0,100,"Pan",new Texture("bat300x300.png"),3.0,1, this.world, this.throwables);
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
			int throwX = (int)deltaX;
			int throwY = (int)-deltaY;
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
