package app.android.chickeninvasion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.android.chickeninvasion.controller.OpenGLActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new OpenGLActivity();
    }
}
