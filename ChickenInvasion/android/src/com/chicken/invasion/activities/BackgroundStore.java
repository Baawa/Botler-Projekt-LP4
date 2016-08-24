package com.chicken.invasion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chicken.invasion.adapters.BackgroundStoreAdapter;
import com.chicken.invasion.R;

/**
 * Created by pedramshirmohammad on 16-05-25.
 */
public class BackgroundStore extends Store implements IStore {


    private BackgroundStoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent.setClass(this, WeaponStore.class);
        itemList = controller.getBackgrounds();
        adapter = new BackgroundStoreAdapter(this,controller);
        getSavedAvailability(itemList);
        viewPager.setAdapter(adapter);
        initBuyAndEquip(itemList);
        initSwitchBackground();
        initUpgradeButton();
    }

    @Override
    public void initSwitchBackground() {
        goToBackground.setImageDrawable(getResources().getDrawable(R.drawable.switchweapons));
        goToBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initUpgradeButton() {
        storeLayout.removeView(upgrade);
    }

}
