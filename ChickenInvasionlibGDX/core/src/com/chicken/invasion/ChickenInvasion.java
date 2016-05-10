package com.chicken.invasion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import java.util.ListIterator;
import java.util.concurrent.Callable;

import java.util.ArrayList;
import java.util.Iterator;

public class ChickenInvasion extends ApplicationAdapter implements GestureDetector.GestureListener{
	Model model;
	SpriteBatch batch;
	Sprite backgroundimg;
	World world;
	Player player;
    Wave wave;
    private Rectangle bottom;

	GameButton startBtn;
	GameButton pauseBtn;

	Camera camera;
	
	@Override
	public void create () {
		model = Model.getInstance();

		this.world = new World(new Vector2(0, 0), true);

		player = new Player();
		player.setEquippedTO(new ThrowableObject((int)Gdx.graphics.getWidth()/200,0,100,"Pan",new Texture("bat300x300.png"),3.0,1, this.world, player.getThrowables(), player));

		batch = new SpriteBatch();

        //Camera
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/100,Gdx.graphics.getHeight()/100);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

        //Background
        Texture backgroundtexture = new Texture("desertbackground500x900.png");
		backgroundimg = new Sprite(backgroundtexture);
		backgroundimg.setPosition(0, 0);
		backgroundimg.setSize(Gdx.graphics.getWidth() / 100, Gdx.graphics.getHeight() / 100);


		startBtn = new GameButton(new Callable<Void>() {
			public Void call() throws Exception {
				startGame();
				return null;
			}
		}, new Texture("play200x200.png"));
		startBtn.setSize(200 / 100, 200 / 100);
		startBtn.setX(Gdx.graphics.getWidth() / 200 - 1);
		startBtn.setY(Gdx.graphics.getHeight() / 200 - 1);

		pauseBtn = new GameButton(new Callable<Void>() {
			public Void call() throws Exception {
				pauseGame();
				return null;
			}
		}, new Texture("pause200x200.png"));
		pauseBtn.setSize(200 / 200, 200 / 200);
		pauseBtn.setX(Gdx.graphics.getWidth() / 100 - 2);
		pauseBtn.setY(Gdx.graphics.getHeight() / 100 - 1);

        wave = new Wave("1",5);

        bottom = new Rectangle(0f,0f,25f,0.1f);

		Gdx.input.setInputProcessor(new GestureDetector(this));
        Gdx.gl.glClearColor(1, 1, 1, 1);
	}

	@Override
	public void render() {
		if (model.getState() == Model.State.RUNNING){
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			if (model.shouldSpawnObject()){
				spawnThrowable();
				model.setSpawnObject(false);
			}

			batch.begin();
			backgroundimg.draw(batch);

			//Check collision
<<<<<<< HEAD
			for (Iterator<ThrowableObject> iterThrow = player.getThrowables().iterator(); iterThrow.hasNext();) {
				ThrowableObject t = iterThrow.next();
				for (Iterator<Enemy> iterEnemies = wave.getEnemies().iterator(); iterEnemies.hasNext(); ) {
					Enemy e = iterEnemies.next();
					if (t.getCollideRect().overlaps(e.getCollideRect())) {
						iterThrow.remove();
						iterEnemies.remove();
						break;
					}
=======
            for (Iterator<Enemy> iterEnemies = wave.getEnemies().iterator(); iterEnemies.hasNext(); ) {
                Enemy e = iterEnemies.next();
			    for (Iterator<ThrowableObject> iterThrow = throwables.iterator(); iterThrow.hasNext();) {
				    ThrowableObject t = iterThrow.next();
                    if (t.isThrown()) {
                        if (t.getCollideRect().overlaps(e.getCollideRect())) {
                            iterThrow.remove();
                            iterEnemies.remove();
                            break;
                        }
                    }
>>>>>>> origin/master
				}
                if (e.getCollideRect().overlaps(bottom)){
                    model.stopGame();
                }
			}

<<<<<<< HEAD
			for (Enemy e : wave.getEnemies()){
				e.draw(batch);
			}
=======
            //Draw throwables
			for (ThrowableObject t : throwables){
				t.updateGraphics(batch);
			}

            ListIterator<Enemy> iter = wave.getEnemies().listIterator(wave.getEnemies().size());
            while (iter.hasPrevious()){
                iter.previous().draw(batch);
            }
>>>>>>> origin/master

			player.draw(batch);

			pauseBtn.draw(batch);

			batch.end();

			world.step(1 / 60f, 6, 2);
		}
		else {
			batch.begin();

			backgroundimg.draw(batch);

            ListIterator<Enemy> iter = wave.getEnemies().listIterator(wave.getEnemies().size());
            while (iter.hasPrevious()){
                iter.previous().drawOnly(batch);
            }

			player.drawOnly(batch);

            startBtn.draw(batch);

			batch.end();
		}

	}

	public void startGame(){
		if (model.getState() == Model.State.PAUSED ||model.getState() == Model.State.STOPPED){
			spawnThrowable();
			model.startGame();
		}
	}

	public void pauseGame(){
		model.pauseGame();
	}

	private void spawnThrowable(){
		player.addThrowables(5);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Vector3 coords=new Vector3(x,y,0);
		Vector3 coords2= camera.unproject(coords);

		this.startBtn.clicked(coords2.x,coords2.y);
		this.pauseBtn.clicked(coords2.x,coords2.y);
		return true;
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

		if ((x >= player.getCurrentTO().getX() || x <= player.getCurrentTO().getX()+100) && (y >= player.getCurrentTO().getY() || y <= player.getCurrentTO().getY()+100) && -deltaY > 0){
			float throwX = deltaX;
			float throwY = -deltaY;
			float totalLength = (float)(Math.sqrt(Math.pow(throwX,2)+Math.pow(throwY,2)));
				if (throwY > 0){
					player.getCurrentTO().throwToPoint(throwX/totalLength,throwY/totalLength);
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
