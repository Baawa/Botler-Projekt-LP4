package com.chicken.invasion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_view);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        prefs = this.getSharedPreferences("myList", Context.MODE_PRIVATE);
        edit = prefs.edit();

        mWeapon = new Weapon();
        toList = mWeapon.getVapen();
        availability = getSavedAvailability(mWeapon.getAvailability());



        viewPager.setClipToPadding(false);
        viewPager.setPadding(300, 0, 300, 0);
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
