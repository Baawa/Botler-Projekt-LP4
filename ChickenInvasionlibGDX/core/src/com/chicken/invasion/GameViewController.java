package com.chicken.invasion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;
import com.chicken.invasion.CallBack.GameCallback;
import com.chicken.invasion.CallBack.HighscoreCallback;
import com.chicken.invasion.Enemy_Throwable.CIEnemy;
import com.chicken.invasion.Helpers.CIBackground;
import com.chicken.invasion.Helpers.CIBackgroundCollection;
import com.chicken.invasion.Helpers.CICollisionRect;
import com.chicken.invasion.Helpers.CIMusicPlayer;
import com.chicken.invasion.Helpers.CIPlayer;
import com.chicken.invasion.Weapons.CIWeapon;
import com.chicken.invasion.Enemy_Throwable.Enemy;
import com.chicken.invasion.Helpers.GameButton;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.Callable;

/**
 * Created by Albin on 2016-08-13.
 */
public class GameViewController extends ApplicationAdapter implements GestureDetector.GestureListener {
    private GameModel model;

    //Drawing
    private SpriteBatch batch; //used for drawing
    private BitmapFont fontScore, fontWings;
    private Texture chickenLeg;

    //Buttons
    private GameButton startBtn, pauseBtn, storeBtn, highscoreBtn, backBtn, restartBtn, muteBtn, unmuteBtn, settingsBtn;

    //Physics + Camera
    private World world;
    private Camera camera;
    private float scale = 100;

    @Override
    public void create() {
        this.model = GameModel.getInstance();
        this.model.setPlayer(new CIPlayer());
        this.model.setBackgrounds(new CIBackgroundCollection());
        this.model.setBottomRect(new CICollisionRect(new Rectangle(0f, 0f, 25f, 0.1f)));

        batch = new SpriteBatch();

        //Camera
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/100,Gdx.graphics.getHeight()/100);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Background
        model.setCurrentBackground(gameCallback.getEquippedBackground());

        initFonts();
        initButtons();

        //Activate touch
        Gdx.input.setInputProcessor(new GestureDetector(this));

        Gdx.gl.glClearColor(1, 1, 1, 1);


        //Update player
        this.model.getPlayer().setChickenLegs(this.model.getPlayer().getChickenLegs() + gameCallback.getChickenLegs());

        gameCallback.getTOUpgrade();

        if (this.model.getPlayer().getEquippedThrowable() == null){
            CIWeapon temp_weapon = gameCallback.getTO();
            this.model.getPlayer().setEquippedThrowable(temp_weapon);
        }
    }

    @Override
    public void resume() {
        super.resume();
        model.setCurrentBackground(gameCallback.getEquippedBackground());
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        switch (model.getState()) {
            case RUNNING:
                batch.begin();

                //highScoreInput.draw(batch,255);
                updateRunningGame(batch);

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

    //----------------------Drawing
    private void drawFonts() {
        //draw score
        fontScore.draw(batch,
                String.valueOf(model.getPlayer().getScore()),
                (float) Gdx.graphics.getWidth() / 200 - 0.5f,
                (float) Gdx.graphics.getHeight() / 100 - 2,
                0.1f,
                Align.center,
                false);

        //Show new wave?
        /* Ek fixa det här..
        if (wave.isDisplayWaveFont()){
            wave.getWaveFont().draw(batch,
                    wave.getWaveString(),
                    wave.getFontX(),
                    wave.getFontY(),
                    0f,
                    Align.center,
                    false);
        }*/

    }

    public void drawAlways(){
        //Draw bg
        if (model.getCurrentBackground().getSprite().getClass() == Sprite.class){
            Sprite tmp = (Sprite)model.getCurrentBackground().getSprite();

            batch.draw(tmp.getTexture(), 0,0, Gdx.graphics.getWidth() / this.scale, Gdx.graphics.getHeight() / this.scale);
        }

        batch.draw(chickenLeg, 0.1f, Gdx.graphics.getHeight() / 100 - 1f, 1, 1);

        //Draw chicken wings
        fontWings.draw(batch, String.valueOf(model.getPlayer().getChickenLegs()), 1.5f, Gdx.graphics.getHeight()/100 - 0.2f);
    }

    private void drawEnemies(){
        ArrayList<Enemy> tmp = new ArrayList<Enemy>(model.getActiveEnemies().size());
        for (Enemy enemy: model.getActiveEnemies()){
            tmp.add(enemy);
        }

        // Generate an iterator. Start just after the last element.
        ListIterator<Enemy> li = tmp.listIterator(tmp.size());

        // Iterate in reverse.
        while(li.hasPrevious()) {
            Enemy enemy = li.previous();
            //update if not paused
            if (model.getState() == GameModel.State.RUNNING) {
                enemy.update(Gdx.graphics.getDeltaTime());
            }
            //draw
            if (enemy.getSprite().getClass() == TextureRegion.class){
                TextureRegion tmp_sprite = (TextureRegion)enemy.getSprite();
                batch.draw(tmp_sprite, enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
            }
        }
    }

    private void updateRunningGame(SpriteBatch batch){
        drawAlways();
        drawEnemies();
        model.checkCollisions();

        model.getCurrentThrowable().updatePosition();
        if (model.getCurrentThrowable().getSprite().getClass() == Sprite.class){
            Sprite tmp = (Sprite) model.getCurrentThrowable().getSprite();

            batch.draw(tmp.getTexture(), model.getCurrentThrowable().getX(), model.getCurrentThrowable().getY());
        }

        drawFonts();

        //Draw pauseBtn
        pauseBtn.setX(Gdx.graphics.getWidth() / 100 - 2);
        pauseBtn.setY(Gdx.graphics.getHeight() / 100 - 1.1f);
        pauseBtn.draw(batch);
    }

    private void drawGameOverScreen(SpriteBatch batch){
        drawAlways();
        drawEnemies();

        if (model.getCurrentThrowable().getSprite().getClass() == Sprite.class){
            Sprite tmp = (Sprite) model.getCurrentThrowable().getSprite();

            batch.draw(tmp.getTexture(), model.getCurrentThrowable().getX(), model.getCurrentThrowable().getY());
        }

        //Game Over specifics
        Sprite gameOver = new Sprite(new Texture("gameover.png"));
        gameOver.setSize(400 / 50, 237f / 50);
        gameOver.setX(Gdx.graphics.getWidth() / 200 - 4);
        gameOver.setY(Gdx.graphics.getWidth() / 200 + 4f);
        gameOver.draw(batch);

        restartBtn.setX((Gdx.graphics.getWidth() / 200) + 0.1f + (restartBtn.getWidth() / 2));
        restartBtn.setY(Gdx.graphics.getHeight() / 200 - 3.0f);
        restartBtn.draw(batch);

        backBtn.setX((Gdx.graphics.getWidth() / 200) - 0.1f - backBtn.getWidth() - (backBtn.getWidth() / 2));
        backBtn.setY(Gdx.graphics.getHeight() / 200 - 3.0f);
        backBtn.draw(batch);
    }

    private void drawStartScreen(SpriteBatch batch){
        drawAlways();

        Sprite startBanner = new Sprite(new Texture("startBanner.png"));
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

        settingsBtn.setX(Gdx.graphics.getWidth() / 100 - 0.1f - settingsBtn.getWidth());
        settingsBtn.setY(Gdx.graphics.getHeight() / 100 - 0.1f - settingsBtn.getHeight());
        settingsBtn.draw(batch);

        if (model.isShowSettings()){
            Sprite settingsView = new Sprite(new Texture("settings.png"));
            settingsView.setSize(350 / 50, 500 / 50);
            settingsView.setX(Gdx.graphics.getWidth() / 200 - settingsView.getWidth() / 2);
            settingsView.setY(Gdx.graphics.getWidth() / 200);
            settingsView.draw(batch);

            if (model.isMusicOn()){
                muteBtn.setX(Gdx.graphics.getWidth() / 200 - muteBtn.getWidth() / 2);
                muteBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f);
                muteBtn.draw(batch);
            } else{
                unmuteBtn.setX(Gdx.graphics.getWidth() / 200 - unmuteBtn.getWidth() / 2);
                unmuteBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f);
                unmuteBtn.draw(batch);
            }
        }
    }

    private void drawPausedGame(SpriteBatch batch){
        drawAlways();
        drawEnemies();

        if (model.getCurrentThrowable().getSprite().getClass() == Sprite.class){
            Sprite tmp = (Sprite) model.getCurrentThrowable().getSprite();

            batch.draw(tmp.getTexture(), model.getCurrentThrowable().getX(), model.getCurrentThrowable().getY());
        }

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

    private void initFonts(){
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ChunkfiveEx.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;

        //score
        fontScore = fontGenerator.generateFont(parameter);
        fontScore.getData().setScale(0.03f);
        fontScore.setUseIntegerPositions(false);
        fontScore.setColor(Color.WHITE);

        //chicken wings
        parameter.size = 60;
        fontWings = fontGenerator.generateFont(parameter);
        fontWings.getData().setScale(0.015f);
        fontWings.setUseIntegerPositions(false);
        fontWings.setColor(Color.WHITE);

        //Chicken leg icon
        chickenLeg = new Texture("chickenleg-skevsomfan.png");
    }


    //----------------------

    //Touchy
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector3 coords=new Vector3(x,y,0);
        Vector3 coords2= camera.unproject(coords);


        if (model.getState() == GameModel.State.RUNNING){
            this.pauseBtn.clicked(coords2.x,coords2.y);
        }

        if (model.getState() == GameModel.State.PAUSED){
            this.startBtn.clicked(coords2.x,coords2.y);
            this.restartBtn.clicked(coords2.x, coords2.y);
            this.backBtn.clicked(coords2.x, coords2.y);
        }

        if (model.getState() == GameModel.State.STOPPED){
            this.startBtn.clicked(coords2.x,coords2.y);
            this.storeBtn.clicked(coords2.x,coords2.y);
            this.highscoreBtn.clicked(coords2.x, coords2.y);
            this.settingsBtn.clicked(coords2.x, coords2.y);
            if (model.isShowSettings()){
                if (!model.isMusicOn()){
                    this.unmuteBtn.clicked(coords2.x, coords2.y);
                } else{
                    this.muteBtn.clicked(coords2.x, coords2.y);
                }
            }
        }

        if (model.getState() == GameModel.State.GAMEOVER){
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
        if (model.getState() == GameModel.State.RUNNING && model.getCurrentThrowable() != null) {
            if ((x >= model.getCurrentThrowable().getX() || x <= model.getCurrentThrowable().getX() + 100) && (y >= model.getCurrentThrowable().getY() || y <= model.getCurrentThrowable().getY() + 100) && -deltaY > 0) {
                float throwX = deltaX;
                float throwY = -deltaY;
                float totalLength = (float) (Math.sqrt(Math.pow(throwX, 2) + Math.pow(throwY, 2)));
                if (throwY > 0) {
                    model.getCurrentThrowable().throwToPoint(throwX/totalLength, throwY/totalLength);
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

    //Buttons

    private void initButtons(){
        startBtn = new com.chicken.invasion.Helpers.GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                startGame();
                return null;
            }
        }, new Texture("play220x220.png"));
        startBtn.setSize(220 / 100, 220 / 100);

        backBtn = new com.chicken.invasion.Helpers.GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                stopGame();
                return null;
            }
        }, new Texture("back220x220.png"));
        backBtn.setSize(220 / 100, 220 / 100);

        restartBtn = new com.chicken.invasion.Helpers.GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                restartGame();
                return null;
            }
        }, new Texture("restart220x220.png"));
        restartBtn.setSize(220 / 100, 220 / 100);

        highscoreBtn = new com.chicken.invasion.Helpers.GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                createIntent("HighScore");
                return null;
            }
        }, new Texture("highscore200x200.png"));
        highscoreBtn.setSize(200 / 100, 200 / 100);

        storeBtn = new com.chicken.invasion.Helpers.GameButton(new Callable<Void>() {
            public Void call() throws Exception {
                createIntent("Store");
                return null;
            }
        }, new Texture("store200x200.png"));
        storeBtn.setSize(200 / 100, 200 / 100);

        pauseBtn = new com.chicken.invasion.Helpers.GameButton((new Callable<Void>() {
            public Void call() throws Exception {
                pauseGame();
                return null;
            }
        }), new Texture("pause200x200.png"));
        pauseBtn.setSize(200 / 200, 200 / 200);

        muteBtn = new com.chicken.invasion.Helpers.GameButton((new Callable<Void>() {
            public Void call() throws Exception {
                mute();
                return null;
            }
        }), new Texture("mute220x220.png"));
        muteBtn.setSize(220 / 100, 220 / 100);

        unmuteBtn = new com.chicken.invasion.Helpers.GameButton((new Callable<Void>() {
            public Void call() throws Exception {
                mute();
                return null;
            }
        }), new Texture("unmute220x220.png"));
        unmuteBtn.setSize(220 / 100, 220 / 100);

        settingsBtn = new com.chicken.invasion.Helpers.GameButton((new Callable<Void>() {
            public Void call() throws Exception {
                showSettingsView();
                return null;
            }
        }), new Texture("cogwheel.png"));
        settingsBtn.setSize(1, 1);
    }

    public void startGame(){
        if (model.getState() == GameModel.State.PAUSED || model.getState() == GameModel.State.STOPPED || model.getState() == GameModel.State.GAMEOVER){
            if (model.getState() == GameModel.State.GAMEOVER || model.getState() == GameModel.State.STOPPED){
                model.startNewGame();
            }

            model.startGame();
        }
    }

    public void stopGame(){
        model.stopGame();
    }

    public void restartGame(){
        model.restartGame();
        startGame();
    }

    public void pauseGame(){
        model.pauseGame();
    }

    public void mute(){
        model.toggleMusic();
    }

    public void showSettingsView(){
        model.toggleSettingsView();
    }

    //Callbacks
    private GameCallback gameCallback;

    public void setMyGameCallback(GameCallback callback) {
        gameCallback = callback;
    }

    public GameCallback getGameCallback() {
        return gameCallback;
    }

    private HighscoreCallback highscoreCallback;

    public void setMyHighScoreCallback(HighscoreCallback callback) { this.highscoreCallback = callback; }

    public void createIntent(String className) {
        if (gameCallback != null) {

            // initiate which ever callback method you need.
            if (className.equals("Store")) {
                gameCallback.onStartActivityStore();
            } else if (className.equals("HighScore")) {
                gameCallback.onStartActivityHighScore();
            } else if (className.equals("InputName")) {
                gameCallback.onStartActivityInputName(model.getPlayer().getScore());
            }
        }
    }
}
