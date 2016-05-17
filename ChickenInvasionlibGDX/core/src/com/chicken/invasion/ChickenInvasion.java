package com.chicken.invasion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.concurrent.Callable;

import java.util.ArrayList;
import java.util.Iterator;

public class ChickenInvasion extends ApplicationAdapter implements GestureDetector.GestureListener{
	private Model model;
	private SpriteBatch batch;
	private Sprite backgroundimg, gameOver, startBanner;
	private World world;
	private Player player;
    private Wave wave;
    private Rectangle bottom;
    private float scoreWidth, scoreHeight;
    private BitmapFont fontScore;

	private GameButton startBtn;
	private GameButton pauseBtn;
    private GameButton storeBtn;
    private GameButton highscoreBtn;

	private Camera camera;
	
	@Override
	public void create () {
		model = Model.getInstance();

		this.world = new World(new Vector2(0, 0), true);

		player = new Player();
		player.setEquippedTO(new ThrowableObject(100,"Pan",new Texture("bat300x300.png"),3.0,1, this.world, player));

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

        initFonts();

		Gdx.input.setInputProcessor(new GestureDetector(this));
        Gdx.gl.glClearColor(1, 1, 1, 1);
	}

	@Override
	public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        switch (model.getState()) {
            case RUNNING:

                if (model.shouldSpawnObject()) {
                    spawnThrowable();
                    model.setSpawnObject(false);
                }

                batch.begin();

                backgroundimg.draw(batch);
                drawEnemies();
                checkCollision();
                player.draw(batch);
                pauseBtn.draw(batch);
                drawFonts();

                batch.end();

                world.step(1 / 60f, 6, 2);
                break;

            case PAUSED:
                batch.begin();

                backgroundimg.draw(batch);

                drawEnemies();
                player.drawOnly(batch);
                startBtn.draw(batch);
                drawFonts();

                batch.end();
                break;

            case STOPPED:
                batch.begin();

                backgroundimg.draw(batch);
                drawEnemies();
                player.drawOnly(batch);
                startBtn.draw(batch);
                storeBtn.draw(batch);
                highscoreBtn.draw(batch);
                startBanner.draw(batch);

                batch.end();
                break;

            case GAMEOVER:
                batch.begin();

                backgroundimg.draw(batch);
                drawEnemies();
                player.drawOnly(batch);
                startBtn.draw(batch);
                gameOver.draw(batch);
                drawFonts();

                batch.end();

                break;

            default:
                System.out.print("Nu blev det galet");
                break;
        }
	}

    private void drawFonts(){
        //draw score
        fontScore.draw(batch,
                String.valueOf(player.getScore()),
                (float) Gdx.graphics.getWidth() / 200- 0.5f,
                (float) Gdx.graphics.getHeight() / 100 - 2,
                0.1f,
                Align.center,
                false);

        //Show new wave?
        if (wave.isDisplayWaveFont()){
            wave.getWaveFont().draw(batch,
                    wave.getWaveString(),
                    wave.getFontX(),
                    wave.getFontY(),
                    0f,
                    Align.center,
                    false);
        }
    }

	public void startGame(){
		if (model.getState() == Model.State.PAUSED || model.getState() == Model.State.STOPPED || model.getState() == Model.State.GAMEOVER){

            //restart?
            if (model.getState() == Model.State.GAMEOVER){
                model.restartWaves();
                wave = new Wave(1,model.getNumberOfEnemies());
            }

            spawnThrowable();
			model.startGame();
            wave.displayWaveFont();
		}
	}

    public void goToStore(){
    }

    public void goToHighscore(){

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
        }, new Texture("play220x220.png"));
        startBtn.setSize(220 / 100, 220 / 100);
        startBtn.setX(Gdx.graphics.getWidth() / 200 - startBtn.getWidth()/2);
        startBtn.setY(startBtn.getHeight()/2 + 0.1f);

        storeBtn = new GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                goToStore();
                return null;
            }
        }, new Texture("store200x200.png"));
        storeBtn.setSize(200 / 100, 200 / 100);
        storeBtn.setX(Gdx.graphics.getWidth() / 200 - startBtn.getWidth() / 2 - 0.5f - storeBtn.getWidth());
        storeBtn.setY(startBtn.getY() - 0.1f);

        highscoreBtn = new GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                goToHighscore();
                return null;
            }
        }, new Texture("highscore200x200.png"));
        highscoreBtn.setSize(200 / 100, 200 / 100);
        highscoreBtn.setX(Gdx.graphics.getWidth() / 200 + startBtn.getWidth() / 2 + 0.5f);
        highscoreBtn.setY(startBtn.getY() - 0.1f);

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
        gameOver.setSize(400/50,237f/50);
        gameOver.setX(Gdx.graphics.getWidth() / 200 - 4);
        gameOver.setY(Gdx.graphics.getWidth() / 200 + 4f);

        startBanner = new Sprite(new Texture("startBanner.png"));
        startBanner.setSize(350/50,250f/50);
        startBanner.setX(Gdx.graphics.getWidth() / 200 - startBanner.getWidth()/2);
        startBanner.setY(Gdx.graphics.getWidth() / 200 + 4f);
    }

    private void initFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;

        //score
        fontScore = generator.generateFont(parameter);
        fontScore.getData().setScale(0.03f);
        fontScore.setColor(Color.WHITE);

        //chicken wings
        //TODO

        generator.dispose();
    }

	private void spawnThrowable(){
		player.addThrowables(model.getNumberOfThrowables());
	}

    private void checkCollision(){
        for (Iterator<Enemy> iterEnemies = wave.getEnemies().iterator(); iterEnemies.hasNext(); ) {
            Enemy e = iterEnemies.next();
            if (player.getThrowables().size() != 0) {
                if (player.getThrowables().get(0).getCollideRect().overlaps(e.getCollideRect()) && player.getCurrentTO().isThrown()) {
                    player.incScore();
                    player.getCurrentTO().onCollison();
                    iterEnemies.remove();

                    if (wave.getEnemies().size() == 0) {
                        model.nextWave();
                        wave = new Wave(wave.getLevel() + 1, model.getNumberOfEnemies());
                        player.addThrowables(model.getNumberOfThrowables());

                        wave.displayWaveFont();
                        System.out.println("Level: " + wave.getLevel());
                    }
                    break;
                }
            }
            //Check if player lost
            if (e.getCollideRect().overlaps(bottom)) {
                model.gameOver();
                player.saveScore();
                break;
            }
        }
    }

    private void drawEnemies(){
        ListIterator<Enemy> li = wave.getEnemies().listIterator(wave.getEnemies().size());
        // Iterate in reverse.
        while(li.hasPrevious()) {
            Enemy e = li.previous();
            //update if not paused
            if (model.getState() == Model.State.RUNNING) {
                e.update(Gdx.graphics.getDeltaTime());
            }
            //draw
            batch.draw(e.getTextureRegion(), e.getX(), e.getY(), e.getWidth(), e.getHeight());
        }
    }


    @Override
    public void dispose(){
        batch.dispose();
        fontScore.dispose();
        world.dispose();
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
        this.storeBtn.clicked(coords2.x,coords2.y);
        this.highscoreBtn.clicked(coords2.x, coords2.y);
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

        if (model.getState() == Model.State.RUNNING && player.getThrowables().size() != 0) {
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
