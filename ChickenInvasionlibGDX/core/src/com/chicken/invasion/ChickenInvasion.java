package com.chicken.invasion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	private Model model;
	private SpriteBatch batch;
	private Sprite backgroundimg, gameOver;
	private World world;
	private Player player;
    private Wave wave;
    private Rectangle bottom;
    private BitmapFont font;

	private GameButton startBtn;
	private GameButton pauseBtn;

	private Camera camera;
	
	@Override
	public void create () {
		model = Model.getInstance();

		this.world = new World(new Vector2(0, 0), true);

		player = new Player();
		player.setEquippedTO(new ThrowableObject((int)Gdx.graphics.getWidth()/200,0,100,"Pan",new Texture("bat300x300.png"),3.0,1, this.world, player));

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

        initSymbols();

        wave = new Wave(1,model.getNumberOfEnemies());

        bottom = new Rectangle(0f,0f,25f,0.1f);

        font = new BitmapFont();
        font.setColor(Color.WHITE);

		Gdx.input.setInputProcessor(new GestureDetector(this));
        Gdx.gl.glClearColor(1, 1, 1, 1);
	}

	@Override
	public void render() {
        switch (model.getState()) {
            case RUNNING:
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                if (model.shouldSpawnObject()) {
                    spawnThrowable();
                    model.setSpawnObject(false);
                }

                batch.begin();

                backgroundimg.draw(batch);
                checkCollision();
                drawEnemies();
                player.draw(batch);
                pauseBtn.draw(batch);
                font.draw(batch, String.valueOf(player.getScore()), 0.5f, (float)Gdx.graphics.getHeight() / 100 - 1,3,0,false);

                batch.end();

                world.step(1 / 60f, 6, 2);
                break;

            case PAUSED:
                batch.begin();

                backgroundimg.draw(batch);

                drawEnemies();
                player.drawOnly(batch);
                startBtn.draw(batch);

                batch.end();
                break;

            case STOPPED:
                batch.begin();

                backgroundimg.draw(batch);
                drawEnemies();
                player.drawOnly(batch);
                startBtn.draw(batch);

                batch.end();
                break;

            case GAMEOVER:
                batch.begin();

                backgroundimg.draw(batch);
                drawEnemies();
                player.drawOnly(batch);
                startBtn.draw(batch);
                gameOver.draw(batch);

                batch.end();

                break;

            default:
                System.out.print("Nu blev det galet");

                break;
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

    private void initSymbols() {
        startBtn = new GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                startGame();
                return null;
            }
        }, new Texture("play200x200.png"));
        startBtn.setSize(200 / 100, 200 / 100);
        startBtn.setX(Gdx.graphics.getWidth() / 200 - 1);
        startBtn.setY(Gdx.graphics.getHeight() / 200 - 1);

        pauseBtn = new GameButton((new Callable<Void>() {
            public Void call() throws Exception {
                pauseGame();
                return null;
            }
        }), new Texture("pause200x200.png"));
        pauseBtn.setSize(200 / 200, 200 / 200);
        pauseBtn.setX(Gdx.graphics.getWidth() / 100 - 2);
        pauseBtn.setY(Gdx.graphics.getHeight() / 100 - 1);

        gameOver = new Sprite(new Texture("gameover.png"));
        gameOver.setSize(8,8);
        gameOver.setX(Gdx.graphics.getWidth() / 200 - 4);
        gameOver.setY(Gdx.graphics.getWidth() / 200 + 7);
    }

	private void spawnThrowable(){
		player.addThrowables(model.getNumberOfThrowables());
	}

    private void checkCollision(){
        for (Iterator<Enemy> iterEnemies = wave.getEnemies().iterator(); iterEnemies.hasNext(); ) {
            Enemy e = iterEnemies.next();
            if (player.getThrowables().get(0).getCollideRect().overlaps(e.getCollideRect())) {
                player.incScore();
                player.getCurrentTO().onCollison();
                iterEnemies.remove();

                if (wave.getEnemies().size()==0){
                    model.nextWave();
                    wave = new Wave(wave.getLevel()+1, model.getNumberOfEnemies());
                    player.addThrowables(model.getNumberOfThrowables());

                    System.out.println("Level: " + wave.getLevel());
                }
                break;
            }
            //Check if player lost
            if (e.getCollideRect().overlaps(bottom)) {
                model.gameOver();
            }
        }
    }

    private void drawEnemies(){
        ListIterator<Enemy> iter = wave.getEnemies().listIterator(wave.getEnemies().size());
        while (iter.hasPrevious()){
            Enemy e = iter.previous();

            //update if not paused
            if (model.getState() == Model.State.RUNNING) {
                e.update(Gdx.graphics.getDeltaTime());
            }
            //draw
            e.getSprite().draw(batch);
        }
    }

    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
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

        if (model.getState() == Model.State.RUNNING) {
            if ((x >= player.getCurrentTO().getX() || x <= player.getCurrentTO().getX() + 100) && (y >= player.getCurrentTO().getY() || y <= player.getCurrentTO().getY() + 100) && -deltaY > 0) {
                float throwX = deltaX;
                float throwY = -deltaY;
                float totalLength = (float) (Math.sqrt(Math.pow(throwX, 2) + Math.pow(throwY, 2)));
                if (throwY > 0) {
                    player.getCurrentTO().throwToPoint(throwX / totalLength, throwY / totalLength);
                }

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
