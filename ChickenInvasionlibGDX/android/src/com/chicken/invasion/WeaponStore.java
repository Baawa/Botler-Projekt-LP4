package com.chicken.invasion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedramshirmohammad on 16-05-26.
 */
public class WeaponStore extends Store implements iStore {

    private Intent intent;

    private StoreCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StoreCardAdapter(this,controller);
        intent = new Intent(this,BackgroundStore.class);
        itemList = controller.getThrowableHolder().getThrowables();
        getSavedAvailability(itemList);
        viewPager.setAdapter(adapter);
        initBuyAndEquip(itemList);
        initSwitchBackground();
        initUpgradeButton();
    }

    @Override
    public void initSwitchBackground() {
        goToBackground.setImageDrawable(getResources().getDrawable(R.drawable.switchbackground));
        goToBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }


    @Override
    public void initUpgradeButton() {
        upgrade.setOnClickListener(this);
    }

}
