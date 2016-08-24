package com.chicken.invasion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chicken.invasion.R;
import com.chicken.invasion.adapters.WeaponStoreAdapter;

/**
 * Created by pedramshirmohammad on 16-05-26.
 */
public class WeaponStore extends Store implements IStore {
    private WeaponStoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new WeaponStoreAdapter(this,controller);
        intent.setClass(this, BackgroundStore.class);
        itemList = controller.getWeapons();
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
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }


    @Override
    public void initUpgradeButton() {
        upgrade.setOnClickListener(this);
    }
}
