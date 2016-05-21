package com.chicken.invasion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pedramshirmohammad on 16-05-16.
 */
public class HighScore extends Activity implements ChickenInvasion.isHighScoreCallback{

    private ListView highscoreList;
    private HighScoreAdapter scoreAdapter;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

    private static List<Score> topList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.highscore_view);
        highscoreList = (ListView)findViewById(R.id.highscoreList);


        //SHARE ON FACEBOOK
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();

        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);
        //END SHARE ON FACEBOOK

        if (topList == null){
            topList = new ArrayList<>();
        }

        scoreAdapter = new HighScoreAdapter(this,R.layout.highscore_content,topList);
        highscoreList.setAdapter(scoreAdapter);

        /*SAVE HIGHSCORE


        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        edit = prefs.edit();

        edit.putInt("PEDRAM", 2333);
        edit.commit();
        Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_LONG).show();

        int sssoo = prefs.getInt("PEDRAM",0);
        Log.e("sssi", Integer.toString(sssoo));

        END SAVE HIGHSCORE*/

    }

    public static boolean isNewHighscore(int points) {
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

    public static void addNewHighScore(String name, int points){
        topList.add(new Score(name, points));

        Collections.sort(topList, new ScoreComparator());

        if (topList.size()>10){
            topList.remove(topList.size() - 1);
        }
    }

    @Override
    public boolean isHighscore(int points) {
        return isNewHighscore(points);
    }
}
