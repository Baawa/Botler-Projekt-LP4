package com.chicken.invasion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.chicken.invasion.ChickenInvasion;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class AndroidLauncher extends AndroidApplication implements ChickenInvasion.GameCallback {

	SharedPreferences pref;
	SharedPreferences.Editor edit;
	int totScore;

    private ChickenInvasion controller;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		pref = this.getSharedPreferences("TOTAL_SCORE", Context.MODE_PRIVATE);
		edit = pref.edit();


		controller = new ChickenInvasion();
        Store.setController(controller);

		controller.setMyGameCallback(this);

        HighScore highScore = new HighScore();
        controller.setMyIsHighScoreCallback(highScore);
		//Intent intent = new Intent(getApplicationContext(),HighScore.class);
		//startActivity(intent);
		initialize(controller, config);
	}

    //CALLBACKS -------------------

	@Override
	public void onStartActivityHighScore() {
		Intent intent = new Intent(this, HighScore.class);
		startActivity(intent);
	}

    @Override
    public void onStartActivityInputName(int points) {
        Intent intent = new Intent(this, InputName.class);
        intent.putExtra("score", points);
        startActivity(intent);
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
	public void onStartActivityStore() {
		Intent intent = new Intent(this, Store.class);
		startActivity(intent);
	}

}
