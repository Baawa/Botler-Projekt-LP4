package com.chicken.invasion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by pedramshirmohammad on 16-05-16.
 */
public class HighScore extends Activity implements ChickenInvasion.isHighScoreCallback{

    private ListView highscoreList;
    private HighScoreAdapter scoreAdapter;
    private SharedPreferences prefs;
    private  SharedPreferences.Editor edit;

    private static List<Score> topList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_view);
        highscoreList = (ListView)findViewById(R.id.highscoreList);
        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        edit = prefs.edit();

        if(topList== null){
            topList = AndroidLauncher.getPrevHighScore();
        }


        scoreAdapter = new HighScoreAdapter(this,R.layout.highscore_content,topList);
        highscoreList.setAdapter(scoreAdapter);

    }

    public static boolean isNewHighscore(int points) {

        topList = AndroidLauncher.getPrevHighScore();

        if (topList == null) {
            topList = new ArrayList<>();
            return true;
        }

        for (Score score : topList){
            if (points > score.getPoints()){
                return true;
            }
        }
        return false;
    }

    public static void addNewHighScore(String name, int points,SharedPreferences.Editor editor){
        topList.add(new Score(name, points));

        Gson gson = new Gson();

        Collections.sort(topList, new ScoreComparator());

        if (topList.size()>10){
            topList.remove(topList.size() - 1);
        }

        String json = gson.toJson(topList);
        editor.putString("MyObject", json);
        editor.commit();
    }

    @Override
    public boolean isHighscore(int points) {
        return isNewHighscore(points);
    }
}
