package com.chicken.invasion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
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
    private GameButton backBtn;
    private GameButton restartBtn;

	private Camera camera;
	
	@Override
	public void create () {

		model = Model.getInstance();

		this.world = new World(new Vector2(0, 0), true);

		player = new Player();
        if (player.getCurrentTO() == null){
            ThrowableObject tmp = new ThrowableObject(100, "Bat", new Texture("bat300x300.png"), this.world, player);
            tmp.setSpeed(1.5);
            player.setEquippedTO(tmp);
        }

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

        wave = new Wave(1,model.getDifficulty());

        bottom = new Rectangle(0f,0f,25f,0.1f);

        initFonts();
        initButtons();

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

                drawRunningGame(batch);

                batch.end();

                world.step(1 / 60f, 6, 2);
                break;

            case PAUSED:
                batch.begin();

                drawPausedGame(batch);

                batch.end();
                break;

            case STOPPED:
                batch.begin();

                drawStartScreen(batch);

                batch.end();
                break;

            case GAMEOVER:
                batch.begin();

                drawGameOverScreen(batch);

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
<<<<<<< HEAD
			if (model.getState() == Model.State.GAMEOVER){
                player.saveScore();
                model.restartWaves();
                wave = new Wave(1,model.getDifficulty());
=======

            //show new wave text?
            if (model.getState() != Model.State.PAUSED){
                wave.displayWaveFont();
>>>>>>> origin/master
            }

            spawnThrowable();
			model.startGame();
		}
	}

    public void goToStore(){
    }

    public void goToHighscore(){
    }

    public void goBack(){
        model.stopGame();
    }

    public void restartGame(){
        model.restartWaves();
        wave = new Wave(1,model.getNumberOfEnemies());
        player.resetScore();
        startGame();
    }

	public void pauseGame(){
		model.pauseGame();
	}

    private void drawRunningGame(SpriteBatch batch){
        backgroundimg.draw(batch);
        drawEnemies();
        checkCollision();
        player.draw(batch);
        drawFonts();

        pauseBtn.setX(Gdx.graphics.getWidth() / 100 - 2);
        pauseBtn.setY(Gdx.graphics.getHeight() / 100 - 1.1f);
        pauseBtn.draw(batch);
    }

    private void drawGameOverScreen(SpriteBatch batch){

        backgroundimg.draw(batch);
        drawEnemies();
        player.drawOnly(batch);
        drawFonts();

        //Game Over specifics
        gameOver = new Sprite(new Texture("gameover.png"));
        gameOver.setSize(400 / 50, 237f / 50);
        gameOver.setX(Gdx.graphics.getWidth() / 200 - 4);
        gameOver.setY(Gdx.graphics.getWidth() / 200 + 4f);
        gameOver.draw(batch);

        restartBtn.setX((Gdx.graphics.getWidth() / 200) + 0.1f + (restartBtn.getWidth() / 2));
        restartBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f);
        restartBtn.draw(batch);

        backBtn.setX((Gdx.graphics.getWidth() / 200) - 0.1f - backBtn.getWidth() - (backBtn.getWidth() / 2));
        backBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f);
        backBtn.draw(batch);

    }

    private void drawStartScreen(SpriteBatch batch){

        backgroundimg.draw(batch);

        startBanner = new Sprite(new Texture("startBanner.png"));
        startBanner.setSize(350 / 50, 250f / 50);
        startBanner.setX(Gdx.graphics.getWidth() / 200 - startBanner.getWidth() / 2);
        startBanner.setY(Gdx.graphics.getWidth() / 200 + 4f);
        startBanner.draw(batch);

        startBtn.setX(Gdx.graphics.getWidth() / 200 - startBtn.getWidth() / 2);
        startBtn.setY(startBtn.getHeight() / 2 + 0.1f);
        startBtn.draw(batch);

        storeBtn.setX(Gdx.graphics.getWidth() / 200 - startBtn.getWidth() / 2 - 0.5f - storeBtn.getWidth());
        storeBtn.setY(startBtn.getY() - 0.1f);
        storeBtn.draw(batch);

        highscoreBtn.setX(Gdx.graphics.getWidth() / 200 + startBtn.getWidth() / 2 + 0.5f);
        highscoreBtn.setY(startBtn.getY() - 0.1f);
        highscoreBtn.draw(batch);
    }

    private void drawPausedGame(SpriteBatch batch){
        backgroundimg.draw(batch);
        drawEnemies();
        player.drawOnly(batch);
        drawFonts();

        //Paused game specifics

        Sprite pauseBanner = new Sprite(new Texture("pausedGame.png"));
        pauseBanner.setSize(400 / 50, 240f / 50);
        pauseBanner.setX(Gdx.graphics.getWidth() / 200 - pauseBanner.getWidth() / 2);
        pauseBanner.setY(Gdx.graphics.getWidth() / 200 + 4f);
        pauseBanner.draw(batch);

        startBtn.setX(Gdx.graphics.getWidth() / 200 - startBtn.getWidth() / 2);
        startBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f);
        startBtn.draw(batch);

        restartBtn.setX((Gdx.graphics.getWidth() / 200) + 0.1f + (restartBtn.getWidth() / 2));
        restartBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f - 1.0f - startBtn.getHeight());
        restartBtn.draw(batch);

        backBtn.setX((Gdx.graphics.getWidth() / 200) - 0.1f - backBtn.getWidth() - (backBtn.getWidth() / 2));
        backBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f - 1.0f - startBtn.getHeight());
        backBtn.draw(batch);

    }

    private void initButtons(){
        startBtn = new GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                startGame();
                return null;
            }
        }, new Texture("play220x220.png"));
        startBtn.setSize(220 / 100, 220 / 100);

        backBtn = new GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                goBack();
                return null;
            }
        }, new Texture("back220x220.png"));
        backBtn.setSize(220 / 100, 220 / 100);

        restartBtn = new GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                restartGame();
                return null;
            }
        }, new Texture("restart220x220.png"));
        restartBtn.setSize(220 / 100, 220 / 100);

        highscoreBtn = new GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                goToHighscore();
                return null;
            }
        }, new Texture("highscore200x200.png"));
        highscoreBtn.setSize(200 / 100, 200 / 100);

        storeBtn = new GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                goToStore();
                return null;
            }
        }, new Texture("store200x200.png"));
        storeBtn.setSize(200 / 100, 200 / 100);

        pauseBtn = new GameButton((new Callable<Void>() {
            public Void call() throws Exception {
                pauseGame();
                return null;
            }
        }), new Texture("pause200x200.png"));
        pauseBtn.setSize(200 / 200, 200 / 200);
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
                        wave = new Wave(wave.getLevel() + 1, model.getDifficulty());
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
        for (Iterator<Enemy> iterEnemies = wave.getEnemies().iterator(); iterEnemies.hasNext(); ) {
            Enemy e = iterEnemies.next();
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


        if (model.getState() == Model.State.RUNNING){
            this.pauseBtn.clicked(coords2.x,coords2.y);
        }

        if (model.getState() == Model.State.PAUSED){
            this.startBtn.clicked(coords2.x,coords2.y);
            this.restartBtn.clicked(coords2.x, coords2.y);
            this.backBtn.clicked(coords2.x, coords2.y);
        }

        if (model.getState() == Model.State.STOPPED){
            this.startBtn.clicked(coords2.x,coords2.y);
            this.storeBtn.clicked(coords2.x,coords2.y);
            this.highscoreBtn.clicked(coords2.x, coords2.y);
        }

        if (model.getState() == Model.State.GAMEOVER){
            this.restartBtn.clicked(coords2.x, coords2.y);
            this.backBtn.clicked(coords2.x, coords2.y);
        }

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
