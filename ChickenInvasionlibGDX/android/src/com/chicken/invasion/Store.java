package com.chicken.invasion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;


import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pedramshirmohammad on 16-04-26.
 */
public class Store extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    private ViewPager viewPager;
    private StoreCardAdapter cardAdapter;
    private static ChickenInvasion controller;
    private List<ThrowableObject> toList;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;
    private ImageButton buyAndEquip;
    private Button goToBackground;
    BuyBackgroundAdapter backgroundAdapter;
    private RelativeLayout storeLayout;
    ImageButton upgrade;
    private int totalScore;
    private TextView scoreView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_view);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        scoreView = (TextView)findViewById(R.id.score_view);
        goToBackground = (Button)findViewById(R.id.goto_background);
        upgrade = (ImageButton) findViewById(R.id.upgradeBtn);
        storeLayout = (RelativeLayout)findViewById(R.id.storeLayout);
        scoreView = (TextView)findViewById(R.id.score_view);
        intent = new Intent(this,BackgroundStore.class);

        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        edit = prefs.edit();
        totalScore = prefs.getInt("TOTAL_SCORE", 0);
        scoreView.setText(Integer.toString(totalScore));

        backgroundAdapter = new BuyBackgroundAdapter(this);


        toList = controller.getThrowableHolder().getThrowables();
        getSavedAvailability();

        viewPager.setClipToPadding(false);

        viewPager.setPadding(0, 0, 0, 0);

        cardAdapter = new StoreCardAdapter(this,controller);
        viewPager.setAdapter(cardAdapter);


        goToBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


        //BUTTONS
        buyAndEquip = (ImageButton) findViewById(R.id.buyAndEquipBtn);
        ThrowableObject to = toList.get(viewPager.getCurrentItem());
        if (to.isPurchased()){
            buyAndEquip.setImageDrawable(getResources().getDrawable(R.drawable.equipicon200x200));
        }
        buyAndEquip.setOnClickListener(this);
        upgrade.setOnClickListener(this);
        //-------------



        //BUTTONS
        buyAndEquip = (ImageButton) findViewById(R.id.buyAndEquipBtn);
        to = toList.get(viewPager.getCurrentItem());
        if (to.isPurchased()){
            buyAndEquip.setImageDrawable(getResources().getDrawable(R.drawable.equipicon200x200));
        }
        buyAndEquip.setOnClickListener(this);
        upgrade = (ImageButton) findViewById(R.id.upgradeBtn);
        upgrade.setOnClickListener(this);
        //-------------

        viewPager.addOnPageChangeListener(this);
    }

    public void getSavedAvailability(){
        for (int i=0;i<toList.size();i++){
            boolean temp = prefs.getBoolean(toList.get(i).getName(),false);
            toList.get(i).setPurchased(temp);
        }
        toList.get(0).setPurchased(true);
    }

    public void saveEquippedTO(int index){
        edit.putInt("EQUIPPED",index);
        edit.commit();
    }

    public void saveTO(List<ThrowableObject> list){
        for (ThrowableObject e: list){
            edit.putBoolean(e.getName(), e.isPurchased());
            edit.commit();
        }
    }

    // SWIPE LISTENER
    public static void setController(ChickenInvasion c){
        controller = c;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ThrowableObject to = toList.get(position);
        if (to.isPurchased()){
            buyAndEquip.setImageDrawable(getResources().getDrawable(R.drawable.equipicon200x200));
        } else {
            buyAndEquip.setImageDrawable(getResources().getDrawable(R.drawable.buyicon200x200));
        }

    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // END SWIPE LISTENER

    // BUTTON CLICK LISTENER
    @Override
    public void onClick(View v) {
        ThrowableObject to = toList.get(viewPager.getCurrentItem());
        switch (v.getId()) {
            case R.id.buyAndEquipBtn:

                //Check if player owns weapon
                if (to.isPurchased()){
                    // EQUIP
                    controller.getPlayer().removeTO();
                    controller.getPlayer().setEquippedTO(to);
                    Toast.makeText(this, to.getName() + " is now equipped!",
                            Toast.LENGTH_LONG).show();
                    saveEquippedTO(viewPager.getCurrentItem());
                }
                //Player wants to buy weapon
                else{
                    if (controller.getPlayer().getChickenWings()<to.getPrice()){
                        // PLAYER HAS NO MONEY
                        Toast.makeText(this, "Not enough chicken legs. Play some more!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        //BUY
                        //Withdraw the price
                        controller.getPlayer().addChickenWings(to.getPrice() / -1);
                        to.setPurchased(true);
                        buyAndEquip.setImageDrawable(getResources().getDrawable(R.drawable.equipicon200x200));
                        Toast.makeText(this, "Purchased " + to.getName() + ". Equip to try it out!",
                                Toast.LENGTH_LONG).show();
                        viewPager.setAdapter(cardAdapter);
                        saveTO(toList);
                    }
                }
                break;

            case R.id.upgradeBtn:
                if (to.isPurchased()){
                    if (controller.getPlayer().getChickenWings()<to.getPrice()/3){
                        // NO MONEY
                        Toast.makeText(this, "Not enough chicken legs. Play some more!",
                                Toast.LENGTH_LONG).show();
                    }else {
                        //UPGRADE
                        to.setDamage(to.getDamage() + 0.5);
                        to.setSpeed(to.getSpeed() + 0.2);
                        viewPager.setAdapter(cardAdapter);
                    }
                } else {
                    //PLAYER DOESN'T OWN WEAPON
                    Toast.makeText(this, "You do not own " + to.getName(),
                            Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }
}
