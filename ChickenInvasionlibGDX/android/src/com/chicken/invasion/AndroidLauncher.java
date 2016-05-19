package com.chicken.invasion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.chicken.invasion.ChickenInvasion;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class AndroidLauncher extends AndroidApplication implements ChickenInvasion.GameCallback {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        setContentView(R.layout.start_view);

		ChickenInvasion chickenInvasion = new ChickenInvasion();
		chickenInvasion.setMyGameCallback(this);
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
	public void onStartActivityStore() {
		Intent intent = new Intent(this, Store.class);
		startActivity(intent);
	}

}
