package com.chicken.invasion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.v4.view.ViewPager;
import android.widget.ImageButton;


import android.view.View;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.chicken.invasion.Helpers.CIBackground;
import com.chicken.invasion.Weapons.CIWeapon;
import com.chicken.invasion.CIStore.Storeitem;


import java.util.List;

/**
 * Created by pedramshirmohammad on 16-04-26.
 */
public abstract class Store extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    protected ViewPager viewPager;
    private StoreCardAdapter cardAdapter;
    protected List<? extends Storeitem> itemList;
    protected static GameViewController controller;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;
    private ImageButton buyAndEquip;
    protected ImageButton goToBackground;
    BuyBackgroundAdapter backgroundAdapter;
    protected RelativeLayout storeLayout;
    protected ImageButton upgrade;
    private int totalScore;
    private TextView scoreView;
    protected Intent intent;
    private boolean first = true;
    private String backgroundSource;



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

        intent = getIntent();

        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        edit = prefs.edit();
        totalScore = prefs.getInt("TOTAL_SCORE", 0);
        scoreView.setText(Integer.toString(totalScore));


        viewPager.setClipToPadding(false);
        viewPager.setPadding(0, 0, 0, 0);

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        intent = getIntent();
        backgroundSource = AndroidLauncher.getInom().getImageURL();
        //backgroundSource = intent.getStringExtra("USED_BACKGROUND");
        //int id = getResources().getIdentifier(backgroundSource, "drawable", getPackageName());
        //storeLayout.setBackgroundResource(id);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Storeitem to = itemList.get(position);
        if (to.isPurchased()){
            buyAndEquip.setImageDrawable(getResources().getDrawable(R.drawable.equipicon200x200,null));
        } else {
            buyAndEquip.setImageDrawable(getResources().getDrawable(R.drawable.buyicon200x200,null));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public <T extends Storeitem> void getSavedAvailability(List<T> itemList){
        for (int i=0;i<itemList.size();i++){
            boolean temp = prefs.getBoolean(itemList.get(i).getName(),false);
            itemList.get(i).setPurchased(temp);
        }
        itemList.get(0).setPurchased(true);
    }

    protected <T extends Storeitem> void initBuyAndEquip(List<T> itemList){
        buyAndEquip = (ImageButton) findViewById(R.id.buyAndEquipBtn);
        Storeitem to = itemList.get(viewPager.getCurrentItem());
        if (to.isPurchased()){
            buyAndEquip.setImageDrawable(getResources().getDrawable(R.drawable.equipicon200x200,null));
        }
        buyAndEquip.setOnClickListener(this);
    }

    public void saveEquippedTO(int index,String itemType){
        edit.putInt(itemType + "_EQUIPPED", index);
        edit.commit();
    }

    public void saveUpdate(CIWeapon weapon){
        edit.putString(weapon.getName() + "_DAMAGE", String.valueOf(weapon.getDamage()));
        edit.putString(weapon.getName() + "_SPEED", String.valueOf(weapon.getSpeed()));
        edit.commit();
    }

    public void getUpdate(List<CIWeapon> toList){
        for(CIWeapon e : toList){
            String tempDamage = prefs.getString(e.getName() + "_DAMAGE","");
            String tempSpeed = prefs.getString(e.getName() + "_SPEED","");
            if(!tempDamage.equals("") && !tempSpeed.equals("")) {
                e.setDamage(Float.parseFloat(tempDamage));
                e.setSpeed(Float.parseFloat(tempSpeed));
            }
        }
    }

    public <T extends Storeitem> void saveTO(List<T> list){
        for (Storeitem e: list){
            edit.putBoolean(e.getName(), e.isPurchased());
            edit.commit();
        }
    }


    public static void setController(GameViewController c){
        controller = c;
    }

    public void setScore(int price){
        totalScore = totalScore + price;
        scoreView.setText(Integer.toString(totalScore));
    }


    @Override
    public void onClick(View v) {
        Storeitem to = itemList.get(viewPager.getCurrentItem());
        switch (v.getId()) {
            case R.id.buyAndEquipBtn:

                //Check if player owns item
                if (to.isPurchased()){
                    // EQUIP
                    if(to.getClass() == CIWeapon.class){
                        GameModel.getInstance().getPlayer().setEquippedThrowable((CIWeapon) to);

                        saveEquippedTO(viewPager.getCurrentItem(), "THROWABLE");
                    }
                    else {
                        GameModel.getInstance().setCurrentBackground((CIBackground) to);

                        saveEquippedTO(viewPager.getCurrentItem(), "BACKGROUND");
                        onStart();
                    }

                    Toast.makeText(this, to.getName() + " is now equipped!",
                            Toast.LENGTH_LONG).show();
                }


                //PlayerObject wants to buy item
                else{
                    if (GameModel.getInstance().getPlayer().getChickenLegs()<to.getPrice()){
                        // PLAYER HAS NO MONEY
                        Toast.makeText(this, "Not enough chicken legs. Play some more!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        //BUY
                        //Withdraw the price
                        GameModel.getInstance().getPlayer().setChickenLegs(GameModel.getInstance().getPlayer().getChickenLegs() - to.getPrice());
                        to.setPurchased(true);
                        setScore(-to.getPrice());

                        buyAndEquip.setImageDrawable(getResources().getDrawable(R.drawable.equipicon200x200));
                        Toast.makeText(this, "Purchased " + to.getName() + ". Equip to try it out!",
                                Toast.LENGTH_LONG).show();
                        cardAdapter = new StoreCardAdapter(this, controller);
                        viewPager.setAdapter(cardAdapter);
                        saveTO(itemList);
                    }
                }
                break;

            case R.id.upgradeBtn:
                if (to.isPurchased()){
                    if (GameModel.getInstance().getPlayer().getChickenLegs()<to.getPrice()/3){
                        // NO MONEY
                        Toast.makeText(this, "Not enough chicken legs. Play some more!",
                                Toast.LENGTH_LONG).show();
                    }else {
                        //UPGRADE
                        CIWeapon toObject = (CIWeapon) to;
                        setScore(to.getPrice() / -3);
                        controller.getGameCallback().saveScore(-(to.getPrice() / 3));
                        GameModel.getInstance().getPlayer().setChickenLegs(GameModel.getInstance().getPlayer().getChickenLegs() - to.getPrice() / -3);
                        toObject.setDamage(toObject.getDamage() + 0.5f);
                        toObject.setSpeed(toObject.getSpeed() + 0.2f);
                        saveUpdate(toObject);
                        cardAdapter = new StoreCardAdapter(this, controller);
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
