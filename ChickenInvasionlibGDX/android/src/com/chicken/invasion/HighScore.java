package com.chicken.invasion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedramshirmohammad on 16-05-16.
 */
public class HighScore extends Activity{

    private ListView highscoreList;
    private HighScoreAdapter scoreAdapter;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_view);
        highscoreList = (ListView)findViewById(R.id.highscoreList);

        //TEST
        List<Score> testList = new ArrayList<Score>();

        Score aa = new Score("aa", 22);
        Score bb = new Score("bb", 33);
        Score cc = new Score("cc", 44);
        Score dd = new Score("dd", 55);
        Score ee = new Score("ee", 66);
        Score ff = new Score("ff", 77);

        testList.add(aa);
        testList.add(bb);
        testList.add(cc);
        testList.add(dd);
        testList.add(ee);
        testList.add(ff);
        //END TEST
        scoreAdapter = new HighScoreAdapter(this,R.layout.highscore_content,testList);
        highscoreList.setAdapter(scoreAdapter);

        //SAVE HIGHSCORE


        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        edit = prefs.edit();

        edit.putInt("PEDRAM", 2333);
        edit.commit();
        Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_LONG).show();

        int sssoo = prefs.getInt("PEDRAM",0);
        Log.e("sssi", Integer.toString(sssoo));

        //END SAVE HIGHSCORE


    }

}
