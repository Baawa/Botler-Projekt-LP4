package com.chicken.invasion.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chicken.invasion.GameController;
import com.chicken.invasion.R;
import com.chicken.invasion.model.GameModel;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;


/**
 * Created by Kristoffer on 2016-05-21.
 */
public class InputName extends Activity {

    private int playerScore;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private TextView scoreLabel;
    private static GameController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Intent intent = getIntent();
        playerScore = intent.getIntExtra("score", 0);


        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        editor = prefs.edit();

        setContentView(R.layout.inputname);

        scoreLabel = (TextView)findViewById(R.id.scoreLabel);
        scoreLabel.setText(Integer.toString(GameModel.getInstance().getPlayer().getScore()));
        //SHARE ON FACEBOOK
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentDescription("Look at my Chicken Invasion score " + Integer.toString(GameModel.getInstance().getPlayer().getScore()))
                .build();

        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);
        //END SHARE ON FACEBOOK

        final EditText inputName = (EditText)findViewById(R.id.nameInput);

        inputName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName.setText("");
            }
        });
        inputName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        ImageButton save = (ImageButton)findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighScore.addNewHighScore(inputName.getText().toString(), playerScore,editor);
                InputName.this.finish();
            }
        });
    }

    public static void setController(GameController c){
        controller = c;
    }
}
