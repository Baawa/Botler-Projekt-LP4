package com.chicken.invasion;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

/**
 * Created by pedramshirmohammad on 16-04-26.
 */
public class Store extends Activity {

    ViewPager viewPager;
    StoreCardAdapter CardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_view);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setClipToPadding(false);
        viewPager.setPadding(0, 0, 0, 0);
        CardAdapter = new StoreCardAdapter(this);
        viewPager.setAdapter(CardAdapter);
    }
}
