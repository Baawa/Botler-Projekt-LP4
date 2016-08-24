package com.chicken.invasion.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chicken.invasion.AndroidLauncher;
import com.chicken.invasion.adapters.HighScoreAdapter;
import com.chicken.invasion.R;
import com.chicken.invasion.model.Score;
import com.chicken.invasion.model.ScoreComparator;
import com.chicken.invasion.callback.HighscoreCallback;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pedramshirmohammad on 16-05-16.
 */
public class HighScore extends Activity implements HighscoreCallback {

    private static List<Score> topList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_view);
        ListView highscoreList = (ListView)findViewById(R.id.highscoreList);
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        RelativeLayout highScoreLayout = (RelativeLayout)findViewById(R.id.highscore_layout);

        if(topList == null){
            if(AndroidLauncher.getPrevHighScore() != null ){
                topList = AndroidLauncher.getPrevHighScore();
            }
        }
        if(topList != null){
            HighScoreAdapter scoreAdapter = new HighScoreAdapter(this,R.layout.highscore_content,topList);
            highscoreList.setAdapter(scoreAdapter);
        }


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
