package com.chicken.invasion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pedramshirmohammad on 16-04-26.
 */
public class Store extends Activity {

    ViewPager viewPager;
    StoreCardAdapter CardAdapter;
    Weapon mWeapon;
    ArrayList<ThrowableObject> toList;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;
    HashMap<String,Boolean> availability;
    Intent intent;
    int playerSore;
    TextView scoreView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_view);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        scoreView = (TextView)findViewById(R.id.score_view);
        prefs = this.getSharedPreferences("myList", Context.MODE_PRIVATE);
        edit = prefs.edit();

        intent = getIntent();
        playerSore = intent.getIntExtra("PLAYER_SCORE", 0)*10;

        scoreView.setText(Integer.toString(playerSore));

        mWeapon = new Weapon();
        toList = mWeapon.getVapen();
        availability = getSavedAvailability(mWeapon.getAvailability());



        viewPager.setClipToPadding(false);
<<<<<<< HEAD
<<<<<<< HEAD
        viewPager.setPadding(200, 0, 300, 0);
=======
        viewPager.setPadding(0, 0, 0, 0);
>>>>>>> origin/master
=======
        viewPager.setPadding(0, 0, 0, 0);
>>>>>>> origin/master
        CardAdapter = new StoreCardAdapter(this,toList,availability);
        viewPager.setAdapter(CardAdapter);
    }

    public HashMap<String,Boolean> getSavedAvailability(HashMap<String,Boolean> map){
        for (Map.Entry<String,Boolean> e: map.entrySet()){
            boolean savedResult = prefs.getBoolean(e.getKey(),e.getValue());
            map.put(e.getKey(),savedResult);
        }
        return map;
    }
}
