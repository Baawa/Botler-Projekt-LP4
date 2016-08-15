package com.chicken.invasion;

import android.app.Application;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by pedramshirmohammad on 16-05-25.
 */
public class AbstractStore extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initSingletons();
    }

    protected void initSingletons(){
        //Initialize the instance
        //MySingleton.initInstance();
    }

    public void customAppMethod(View view, Context ctx){
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.view_pager);
        GameViewController controller = new GameViewController();
        StoreCardAdapter ilo = new StoreCardAdapter(ctx,controller);
        viewPager.setAdapter(ilo);
    }
}
