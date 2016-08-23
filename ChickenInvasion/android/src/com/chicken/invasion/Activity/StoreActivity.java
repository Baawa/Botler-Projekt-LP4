package com.chicken.invasion.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.chicken.invasion.R;

/**
 * Created by pedramshirmohammad on 16-08-14.
 */
public class StoreActivity extends Activity {

    int totalScore;
    Intent intent;
    SharedPreferences prefs;
    TextView txtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        txtScore = (TextView)findViewById(R.id.txt_store_money);

        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        intent = getIntent();
        totalScore = prefs.getInt("TOTAL_SCORE", 0);

        txtScore.setText(Integer.toString(totalScore));
    }
}
