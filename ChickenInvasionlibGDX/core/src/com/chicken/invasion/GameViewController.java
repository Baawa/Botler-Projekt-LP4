package com.chicken.invasion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;
import com.chicken.invasion.CallBack.GameCallback;
import com.chicken.invasion.CallBack.HighscoreCallback;
import com.chicken.invasion.Helpers.CIBackground;
import com.chicken.invasion.Helpers.CIBackgroundCollection;
import com.chicken.invasion.Helpers.CICollisionRect;
import com.chicken.invasion.Helpers.CIMusicPlayer;
import com.chicken.invasion.Helpers.CIPlayer;
import com.chicken.invasion.Weapons.CIWeapon;
import com.chicken.invasion.oldstuff.*;
import com.chicken.invasion.oldstuff.Background;
import com.chicken.invasion.oldstuff.BackgroundHolder;
import com.chicken.invasion.oldstuff.ThrowableHolder;
import com.chicken.invasion.oldstuff.ThrowableObject;
import com.chicken.invasion.oldstuff.Wave;

/**
 * Created by Albin on 2016-08-13.
 */
public class GameViewController extends ApplicationAdapter implements GestureDetector.GestureListener {
    private GameModel model;

    //Drawing
    private SpriteBatch batch; //used for drawing
    private CIBackground bGround;
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
        setBackground(gameCallback.getEquippedBackground());
        drawBackground();

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
        setBackground(gameCallback.getEquippedBackground());
        drawBackground();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        switch (model.getState()) {
            case RUNNING:
                batch.begin();

                //highScoreInput.draw(batch,255);
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

    //----------------------Drawing
    private void drawFonts() {
        //draw score
        fontScore.draw(batch,
                String.valueOf(playerObject.getScore()),
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
        if (bGround.getSprite().getClass() == Sprite.class){
            Sprite tmp = (Sprite)bGround.getSprite();

            batch.draw(tmp.getTexture(), 0,0, Gdx.graphics.getWidth() / this.scale, Gdx.graphics.getHeight() / this.scale);
        }

        batch.draw(chickenLeg, 0.1f, Gdx.graphics.getHeight() / 100 - 1f, 1, 1);

        //Draw chicken wings
        fontWings.draw(batch, String.valueOf(model.getPlayer().getChickenLegs()), 1.5f, Gdx.graphics.getHeight()/100 - 0.2f);
    }


    //----------------------

    //Touchy
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
        return false;
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

    //Callbacks
    private GameCallback gameCallback;

    public void setMyGameCallback(GameCallback callback) {
        gameCallback = callback;
    }

    private HighscoreCallback highscoreCallback;

    public void setMyHighScoreCallback(HighscoreCallback callback) { this.highscoreCallback = callback; }
}
