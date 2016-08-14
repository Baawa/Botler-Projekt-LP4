package com.chicken.invasion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.chicken.invasion.CallBack.GameCallback;
import com.chicken.invasion.Enemy_Throwable.Throwable_Object;
import com.chicken.invasion.GameViewController;
import com.chicken.invasion.Helpers.CIBackground;
import com.chicken.invasion.Helpers.CIBackgroundCollection;
import com.chicken.invasion.Weapons.CIWeapon;
import com.chicken.invasion.Weapons.WeaponCollection;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
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

    private static GameViewController controller;
	private static List<Score> topList;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
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

		
		controller = new GameViewController();
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



	@Override
	public CIBackground getEquippedBackground(){
		return new CIBackgroundCollection().getStoreItems().get(pref.getInt("BACKGROUND_EQUIPPED", 0));
	}

    //CALLBACKS -------------------

	@Override
	public void onStartActivityHighScore() {
		intent.setClass(this, HighScore.class);
		intent.putExtra("USED_BACKGROUND", getEquippedBackground().getImageURL());
		startActivity(intent);
	}

    @Override
    public void onStartActivityInputName(int points) {
        intent.setClass(this, InputName.class);
        intent.putExtra("score", points);
        startActivity(intent);
    }

	public static CIBackground getInom(){
		return new CIBackgroundCollection().getStoreItems().get(pref.getInt("BACKGROUND_EQUIPPED", 0));
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
	public CIWeapon getTO() {
		return (CIWeapon)GameModel.getInstance().getThrowableCollection().getThrowables().get(pref.getInt("THROWABLE_EQUIPPED", 0));
	}

	@Override
	public void onStartActivityStore() {
		intent.setClass(this, WeaponStore.class);
		intent.putExtra("USED_BACKGROUND", getEquippedBackground().getImageURL());
		intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

	@Override
	public void getTOUpgrade(){
		ArrayList<CIWeapon> tempList = GameModel.getInstance().getThrowableCollection().getThrowables();
		for (CIWeapon e : tempList){
			String tempDamage = pref.getString(e.getName() + "_DAMAGE","");
			String tempSpeed = pref.getString(e.getName() + "_SPEED","");
			if(!tempDamage.equals("") && !tempSpeed.equals("")) {
				e.setDamage(Float.parseFloat(tempDamage));
				e.setSpeed(Float.parseFloat(tempSpeed));
			}
		}
	}
}
