package app.android.chickeninvasion.view;

import android.opengl.*;
import android.content.*;


/**
 * Created by Albin on 2016-03-23.
 */
public class MainView extends GLSurfaceView{
    private final MainViewRenderer mRenderer;

    public MainView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MainViewRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
