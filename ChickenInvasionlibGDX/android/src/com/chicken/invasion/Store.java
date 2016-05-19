package com.chicken.invasion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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



        //2 TEST

        /*ThrowableObject shtekpanna = new ThrowableObject(100,"Pan",new Texture("pan300x300.png"),null,null);

        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(shtekpanna);
        prefsEditor.putString("MyObject1", json);
        prefsEditor.commit();

        aas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gsosn = new Gson();
                String json = prefs.getString("MyObject1", "");
                ThrowableObject obj = gsosn.fromJson(json, ThrowableObject.class);
                Log.e("FUNKAR",obj.getName());
            }
        });*/

        //END 2 TEST
    }

    public HashMap<String,Boolean> getSavedAvailability(HashMap<String,Boolean> map){
        for (Map.Entry<String,Boolean> e: map.entrySet()){
            boolean savedResult = prefs.getBoolean(e.getKey(),e.getValue());
            map.put(e.getKey(),savedResult);
        }
        return map;
    }
}
