package com.chicken.invasion;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Created by pedramshirmohammad on 16-05-24.
 */
public class BackgroundStore extends Activity{

    private RelativeLayout storeLayout;
    private ImageButton upgradeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_view);
        storeLayout = (RelativeLayout)findViewById(R.id.storeLayout);
        upgradeBtn = (ImageButton)findViewById(R.id.upgradeBtn);
        storeLayout.removeView(upgradeBtn);

    }
}
