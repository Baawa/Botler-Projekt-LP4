package com.chicken.invasion;

import android.app.Activity;
import android.app.Application;
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
public abstract class Store extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    protected ViewPager viewPager;
    private StoreCardAdapter cardAdapter;
    protected List<? extends iItem> itemList;
    protected static ChickenInvasion controller;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;
    private ImageButton buyAndEquip;
    protected ImageButton goToBackground;
    BuyBackgroundAdapter backgroundAdapter;
    protected RelativeLayout storeLayout;
    protected ImageButton upgrade;
    private int totalScore;
    private TextView scoreView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_view);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        scoreView = (TextView)findViewById(R.id.score_view);
        goToBackground = (ImageButton)findViewById(R.id.goto_btn);
        upgrade = (ImageButton) findViewById(R.id.upgradeBtn);
        storeLayout = (RelativeLayout)findViewById(R.id.storeLayout);
        scoreView = (TextView)findViewById(R.id.score_view);



        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        edit = prefs.edit();
        totalScore = prefs.getInt("TOTAL_SCORE", 0);
        scoreView.setText(Integer.toString(totalScore));




        viewPager.setClipToPadding(false);

        viewPager.setPadding(0, 0, 0, 0);


        //cardAdapter = new StoreCardAdapter(this,controller);
        //viewPager.setAdapter(cardAdapter);




        //initUpgradeBtn();
        //-------------


        viewPager.addOnPageChangeListener(this);
    }

    public <T extends iItem> void getSavedAvailability(List<T> itemList){
        for (int i=0;i<itemList.size();i++){
            boolean temp = prefs.getBoolean(itemList.get(i).getName(),false);
            itemList.get(i).setPurchased(temp);
        }
        itemList.get(0).setPurchased(true);
    }

    protected <T extends iItem> void initBuyAndEquip(List<T> itemList){
        buyAndEquip = (ImageButton) findViewById(R.id.buyAndEquipBtn);
        iItem to = itemList.get(viewPager.getCurrentItem());
        if (to.isPurchased()){
            buyAndEquip.setImageDrawable(getResources().getDrawable(R.drawable.equipicon200x200));
        }
        buyAndEquip.setOnClickListener(this);
    }




    public void saveEquippedTO(int index,String name){
        edit.putInt(name +"_EQUIPPED",index);
        edit.commit();
    }

    public <T extends iItem> void saveTO(List<T> list){
        for (iItem e: list){
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

        iItem to = itemList.get(position);
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
        iItem to = itemList.get(viewPager.getCurrentItem());
        switch (v.getId()) {
            case R.id.buyAndEquipBtn:

                //Check if player owns item
                if (to.isPurchased()){
                    // EQUIP
                    if(to.getClass() == ThrowableObject.class){
                        controller.getPlayer().removeTO();
                        controller.getPlayer().setEquippedTO((ThrowableObject) to);
                    }
                    else {
                        controller.setBackground((Background) to);
                    }

                    Toast.makeText(this, to.getName() + " is now equipped!",
                            Toast.LENGTH_LONG).show();
                    saveEquippedTO(viewPager.getCurrentItem(),to.getName());
                }


                //Player wants to buy item
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
                        saveTO(itemList);
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
                        ThrowableObject toObject = (ThrowableObject) to;
                        toObject.setDamage(toObject.getDamage() + 0.5);
                        toObject.setSpeed(toObject.getSpeed() + 0.2);
                        cardAdapter = new StoreCardAdapter(this,controller);
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
