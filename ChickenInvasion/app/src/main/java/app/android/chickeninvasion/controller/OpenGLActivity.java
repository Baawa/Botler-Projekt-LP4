package app.android.chickeninvasion.controller;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import app.android.chickeninvasion.ThrowableObject;
import app.android.chickeninvasion.view.MainView;

/**
 * Created by Kristoffer on 2016-03-23.
 */
public class OpenGLActivity extends Activity {
    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MainView(this);
        setContentView(mGLView);


        ThrowableObject TO = new ThrowableObject(0,0,300,400);
        TO.throwToPoint(100,300);
    }

}
