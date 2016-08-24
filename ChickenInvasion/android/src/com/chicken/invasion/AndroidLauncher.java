package com.chicken.invasion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.chicken.invasion.activities.HighScore;
import com.chicken.invasion.activities.InputName;
import com.chicken.invasion.activities.Store;
import com.chicken.invasion.activities.WeaponStore;
import com.chicken.invasion.callback.GameCallback;
import com.chicken.invasion.controller.collections.BackgroundCollection;
import com.chicken.invasion.model.Background;
import com.chicken.invasion.model.Score;
import com.chicken.invasion.model.Weapon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AndroidLauncher extends AndroidApplication implements GameCallback {

	static SharedPreferences pref;
	SharedPreferences.Editor edit;
	int totScore;
	private Intent intent;

    private static GameController controller;
	private static List<Score> topList;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);*/
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		pref = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		edit = pref.edit();
		intent = new Intent(this, WeaponStore.class);

		//GET SAVED SCORES
		Gson gson = new Gson();
		String json = pref.getString("MyObject", "");
		Type type = new TypeToken<List<Score>>(){}.getType();
		topList= gson.fromJson(json, type);
		//--END GET SAVED SCORES


		controller = new GameController();
        Store.setController(controller);
		InputName.setController(controller);
		controller.setMyGameCallback(this);


        HighScore highScore = new HighScore();
        controller.setHighscoreCallback(highScore);
		initialize(controller, config);
	}

	public static List<Score> getPrevHighScore(){
		return topList;
	}


    //CALLBACKS -------------------

	@Override
	public void onStartActivityHighScore() {
		intent.setClass(this, HighScore.class);
		intent.putExtra("USED_BACKGROUND", AndroidLauncher.getInom().getImageURL());
		startActivity(intent);
	}

    @Override
    public void onStartActivityInputName(int points) {
        intent.setClass(this, InputName.class);
        intent.putExtra("score", points);
        startActivity(intent);
    }

	public static Background getInom(){
		return new BackgroundCollection().getBackgrounds().get(pref.getInt("BACKGROUND_EQUIPPED", 0));
	}

    @Override
	public void saveScore(int score) {
		if (pref.getInt("TOTAL_SCORE",0) == 0){
			totScore = score;
			edit.putInt("TOTAL_SCORE",score);
			edit.commit();
		}
		else {
			totScore = pref.getInt("TOTAL_SCORE",0) + score;
			edit.putInt("TOTAL_SCORE",totScore);
			edit.commit();
		}
	}

	@Override
	public int getChickenLegs() {
		return pref.getInt("TOTAL_SCORE", 0);
	}

	@Override
	public Weapon getTO() {
		return controller.getWeapons().get(pref.getInt("THROWABLE_EQUIPPED", 0));
	}

	@Override
	public Background getEquippedBackground() {
		return new BackgroundCollection().getBackgrounds().get(pref.getInt("BACKGROUND_EQUIPPED", 0));
	}

	@Override
	public void onStartActivityStore() {
		intent.setClass(this, WeaponStore.class);
		intent.putExtra("USED_BACKGROUND", AndroidLauncher.getInom().getImageURL());
		intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	@Override
	public void getTOUpgrade(){
		ArrayList<Weapon> tempList = controller.getWeapons();
		for (Weapon e : tempList){
			String tempDamage = pref.getString(e.getName() + "_DAMAGE","");
			String tempSpeed = pref.getString(e.getName() + "_SPEED","");
			if(!tempDamage.equals("") && !tempSpeed.equals("")) {
				e.setDamage(Integer.parseInt(tempDamage));
				e.setSpeed(Integer.parseInt(tempSpeed));
			}
		}
	}
}
