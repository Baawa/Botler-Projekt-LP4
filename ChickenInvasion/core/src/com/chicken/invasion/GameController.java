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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.chicken.invasion.callback.GameCallback;
import com.chicken.invasion.callback.HighscoreCallback;
import com.chicken.invasion.controller.ButtonController;
import com.chicken.invasion.controller.EnemyController;
import com.chicken.invasion.controller.WeaponController;
import com.chicken.invasion.controller.TouchListener;
import com.chicken.invasion.model.Background;
import com.chicken.invasion.model.Enemy;
import com.chicken.invasion.controller.collections.BackgroundCollection;
import com.chicken.invasion.model.GameState;
import com.chicken.invasion.model.Weapon;
import com.chicken.invasion.physics.Body;
import com.chicken.invasion.physics.CollisionRect;
import com.chicken.invasion.controller.collections.EnemyCollection;
import com.chicken.invasion.model.Player;
import com.chicken.invasion.model.Button;
import com.chicken.invasion.controller.collections.WeaponCollection;
import com.chicken.invasion.model.GameModel;
import com.chicken.invasion.view.GameOverScreen;
import com.chicken.invasion.view.GameScreen;
import com.chicken.invasion.view.IScreen;
import com.chicken.invasion.view.PausedScreen;
import com.chicken.invasion.view.StartScreen;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Albin on 2016-08-13.
 */
public class GameController extends ApplicationAdapter{
    private final static int SCALE = 100;
    private static float SCREEN_WIDTH;
    
    private GameModel model;

    //Drawing
    private SpriteBatch batch; //used for drawing
    private IScreen currentScreen;

    private BackgroundCollection bgCollection;
    private WeaponCollection weaponCollection;
    private EnemyCollection enemyCollection;
    private WeaponController currentWeapon;
    private List<EnemyController> activeEnemies;
    private List<ButtonController> buttons;
    private List<Sprite> banners;
    private BitmapFont fontScore, fontWings;
    private Texture background;

    World world;

    @Override
    public void create() {
        world = new World(new Vector2(0, 0), true);

        SCREEN_WIDTH = Gdx.graphics.getWidth();

        bgCollection = new BackgroundCollection();
        weaponCollection = new WeaponCollection(world);
        enemyCollection = new EnemyCollection();

        model = GameModel.getInstance();
        model.setScreenSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        model.setTopRect(new CollisionRect(new Rectangle(0, Gdx.graphics.getHeight()/SCALE, 3*SCREEN_WIDTH/SCALE, 0.1f)));
        model.setBottomRect(new CollisionRect(new Rectangle(0f, 0f, 25f, 0.1f)));
        model.setAllEnemies(enemyCollection.getEnemies());
        model.setPlayer(new Player(weaponCollection.getWeapons().get(0)));

        batch = new SpriteBatch();

        //Camera
        Camera camera = new OrthographicCamera(SCREEN_WIDTH / SCALE, Gdx.graphics.getHeight() / SCALE);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //IBackground
        model.setCurrentBackground(gameCallback.getEquippedBackground());
        background = new Texture(model.getCurrentBackground().getImageURL());

        initFonts();
        initButtons();
        initBanners();

        activeEnemies = new ArrayList<EnemyController>();

        //Activate touch
        Gdx.input.setInputProcessor(new GestureDetector(new TouchListener(camera,buttons)));

        //Update player
        model.getPlayer().setChickenLegs(model.getPlayer().getChickenLegs() + gameCallback.getChickenLegs());

        gameCallback.getTOUpgrade();
        if (model.getPlayer().getEquippedWeapon() == null){
            if (gameCallback.getTO() != null){
                model.getPlayer().setEquippedWeapon(gameCallback.getTO());
            } else{
                model.getPlayer().setEquippedWeapon(weaponCollection.getWeapons().get(0));
            }
        }
        currentScreen = new StartScreen(batch,this);
        //model.nextWave();
        Gdx.gl.glClearColor(1, 1, 1, 1);
    }

    @Override
    public void resume() {
        super.resume();
        model.setCurrentBackground(gameCallback.getEquippedBackground());
        background = new Texture(model.getCurrentBackground().getImageURL());
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if (model.getState() == GameState.GAMEOVER && currentScreen.getClass() != GameOverScreen.class) {
            currentScreen = new GameOverScreen(batch, this);
            gameCallback.saveScore(getPlayersWings());
            if (getHighscoreCallback().isHighscore(getPlayerScore())){
                getGameCallback().onStartActivityInputName(getPlayerScore());
            }
        }
        if (currentWeapon == null && model.getCurrentWeapon() != null) {
            currentWeapon = new WeaponController(model.getCurrentWeapon());
        }
        float dt = Gdx.graphics.getDeltaTime();
        model.updateGameObjects(dt);
        // Draw screen
        batch.begin();
        currentScreen.render(dt);
        batch.end();

        if (model.getState()==GameState.RUNNING){world.step(1 / 60f, 6, 2);}
    }

    private void initFonts(){
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ChunkfiveEx.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = SCALE;

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
    }

    //Buttons

    private void initButtons(){
        Button startBtn = new Button(new Callable<Void>() {
            public Void call() throws Exception {
                startGame();
                return null;
            }
        }, 220 / SCALE, 220 / SCALE, "play220x220.png");
        startBtn.setX(SCREEN_WIDTH / 200 - startBtn.getWidth() / 2);
        startBtn.setY(startBtn.getHeight() / 2 + 0.1f);

        Button backBtn = new Button(new Callable<Void>() {
            public Void call() throws Exception {
                stopGame();
                return null;
            }
        }, 220 / SCALE, 220 / SCALE, "back220x220.png");
        backBtn.setX((SCREEN_WIDTH / 200) - 0.1f - backBtn.getWidth() - (backBtn.getWidth() / 2));
        backBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f - 1.0f - startBtn.getHeight());

        Button restartBtn = new Button(new Callable<Void>() {
            public Void call() throws Exception {
                restartGame();
                return null;
            }
        }, 220 / SCALE, 220 / SCALE, "restart220x220.png");
        restartBtn.setX((SCREEN_WIDTH / 200) + 0.1f + (restartBtn.getWidth() / 2));
        restartBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f - 1.0f - startBtn.getHeight());

        Button highscoreBtn = new Button(new Callable<Void>() {
            public Void call() throws Exception {
                createIntent("HighScore");
                return null;
            }
        }, 200 / SCALE, 200 / SCALE, "highscore200x200.png");
        highscoreBtn.setX(SCREEN_WIDTH / 200 + startBtn.getWidth() / 2 + 0.5f);
        highscoreBtn.setY(startBtn.getY() - 0.1f);


        Button storeBtn = new Button(new Callable<Void>() {
            public Void call() throws Exception {
                createIntent("Store");
                return null;
            }
        }, 200 / SCALE, 200 / SCALE, "store200x200.png");
        storeBtn.setX(SCREEN_WIDTH / 200 - startBtn.getWidth() / 2 - 0.5f - storeBtn.getWidth());
        storeBtn.setY(startBtn.getY() - 0.1f);

        Button pauseBtn = new Button(new Callable<Void>() {
            public Void call() throws Exception {
                pauseGame();
                return null;
            }
        }, 1, 1, "pause200x200.png");
        pauseBtn.setX(SCREEN_WIDTH / SCALE - 2);
        pauseBtn.setY(Gdx.graphics.getHeight() / SCALE - 1.1f);

        Button muteBtn = new Button(new Callable<Void>() {
            public Void call() throws Exception {
                mute();
                return null;
            }
        }, 220 / SCALE, 220 / SCALE, "mute220x220.png");
        muteBtn.setX(SCREEN_WIDTH / 200 - muteBtn.getWidth() / 2);
        muteBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f);


        Button unmuteBtn = new Button(new Callable<Void>() {
            public Void call() throws Exception {
                mute();
                return null;
            }
        }, 220 / SCALE, 220 / SCALE, "unmute220x220.png");
        unmuteBtn.setX(SCREEN_WIDTH / 200 - unmuteBtn.getWidth() / 2);
        unmuteBtn.setY(Gdx.graphics.getHeight() / 200 - 2.0f);

        Button settingsBtn = new Button(new Callable<Void>() {
            public Void call() throws Exception {
                showSettingsView();
                return null;
            }
        }, 1, 1, "cogwheel.png");
        settingsBtn.setX(SCREEN_WIDTH / SCALE - 0.1f - settingsBtn.getWidth());
        settingsBtn.setY(Gdx.graphics.getHeight() / SCALE - 0.1f - settingsBtn.getHeight());

        buttons = new ArrayList<ButtonController>();
        buttons.add(new ButtonController(startBtn));
        buttons.add(new ButtonController(backBtn));
        buttons.add(new ButtonController(restartBtn));
        buttons.add(new ButtonController(highscoreBtn));
        buttons.add(new ButtonController(storeBtn));
        buttons.add(new ButtonController(pauseBtn));
        buttons.add(new ButtonController(muteBtn));
        buttons.add(new ButtonController(unmuteBtn));
        buttons.add(new ButtonController(settingsBtn));
    }

    private void initBanners(){
        //Settings Banner
        Sprite settingsBanner = new Sprite(new Texture("settings.png"));
        settingsBanner.setSize(350 / 50, 500 / 50);
        settingsBanner.setX(SCREEN_WIDTH / 200 - settingsBanner.getWidth() / 2);
        settingsBanner.setY(SCREEN_WIDTH / 200);

        //Start Banner
        Sprite startBanner = new Sprite(new Texture("startBanner.png"));
        startBanner.setSize(350 / 50, 250f / 50);
        startBanner.setX(SCREEN_WIDTH / 200 - startBanner.getWidth() / 2);
        startBanner.setY(SCREEN_WIDTH / 200 + 4f);

        //Paused Banner
        Sprite pauseBanner = new Sprite(new Texture("pausedGame.png"));
        pauseBanner.setSize(400 / 50, 240f / 50);
        pauseBanner.setX(SCREEN_WIDTH / 200 - pauseBanner.getWidth() / 2);
        pauseBanner.setY(SCREEN_WIDTH / 200 + 4f);

        //Game Over Banner
        Sprite gameOverBanner = new Sprite(new Texture("gameover.png"));
        gameOverBanner.setSize(400 / 50, 237f / 50);
        gameOverBanner.setX(SCREEN_WIDTH / 200 - 4);
        gameOverBanner.setY(SCREEN_WIDTH / 200 + 4f);

        banners = new ArrayList<Sprite>();
        banners.add(settingsBanner);
        banners.add(startBanner);
        banners.add(pauseBanner);
        banners.add(gameOverBanner);
    }

    public Sprite getBannerAt(int index){
        return banners.get(index);
    }

    public void startGame(){
        if (model.getState() != GameState.RUNNING){
            if (model.getState() == GameState.GAMEOVER || model.getState() == GameState.STOPPED){
                model.startNewGame();
                currentWeapon = new WeaponController(model.getCurrentWeapon());
            }
            model.startGame();
            currentScreen = new GameScreen(batch, this);
        }
    }

    public void restartGame(){
        model.startNewGame();
        currentWeapon = new WeaponController(model.getCurrentWeapon());
        startGame();
    }

    public void stopGame(){
        model.stopGame();
        currentScreen = new StartScreen(batch,this);
    }

    public void pauseGame(){
        model.pauseGame();
        currentScreen = new PausedScreen(batch,this);
    }

    public void mute(){
        model.toggleMusic();
    }

    public void showSettingsView(){
        model.toggleSettingsView();
    }

    public ButtonController getButtonAt(int index){ return buttons.get(index); }
    public Texture getBackground(){ return background;}
    public BitmapFont getFontWings(){ return fontWings;}
    public BitmapFont getFontScore(){ return fontScore;}
    public int getPlayersWings(){ return model.getPlayer().getChickenLegs();}
    public boolean isShowSettings(){ return model.isShowSettings();}
    public boolean isMusicOn(){ return model.isMusicOn();}
    public int getPlayerScore(){ return model.getPlayer().getScore();}
    public WeaponController getCurrentWeapon(){ return currentWeapon; }
    public ArrayList<Background> getBackgrounds(){ return bgCollection.getBackgrounds(); }
    public ArrayList<Weapon> getWeapons(){ return weaponCollection.getWeapons(); }
    public ArrayList<Enemy> getEnemies(){ return enemyCollection.getEnemies(); }

    public List<EnemyController> getActiveEnemies(){
        if (activeEnemies.size() != model.getActiveEnemies().size()){
            updateEnemyList();
        }else {
            for (int i = 0; i < model.getActiveEnemies().size(); i++){
                if (!activeEnemies.get(i).getEnemy().equals(model.getActiveEnemies().get(i))){
                    updateEnemyList();
                    break;
                }
            }
        }
        return activeEnemies;
    }

    private void updateEnemyList(){
        activeEnemies.clear();
        try {
            for (Enemy e : model.getActiveEnemies()){
                activeEnemies.add(new EnemyController(e));
            }
        }catch (ConcurrentModificationException e){
            e.printStackTrace();
        }
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

    public void setHighscoreCallback(HighscoreCallback hsCallback){ highscoreCallback = hsCallback;}

    public HighscoreCallback getHighscoreCallback(){ return highscoreCallback;}

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
