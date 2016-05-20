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

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		pref = this.getSharedPreferences("TOTAL_SCORE", Context.MODE_PRIVATE);
		edit = pref.edit();


		ChickenInvasion chickenInvasion = new ChickenInvasion();
		chickenInvasion.setMyGameCallback(this);

        HighScore highScore = new HighScore();
        chickenInvasion.setMyScoreCallback(highScore);
		//Intent intent = new Intent(getApplicationContext(),HighScore.class);m
		//startActivity(intent);
		initialize(chickenInvasion, config);
	}

	@Override
	public void onStartActivityHighScore() {
		Intent intent = new Intent(this, HighScore.class);
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
		intent.putExtra("PLAYER_SCORE",totScore);
		startActivity(intent);
	}

}
