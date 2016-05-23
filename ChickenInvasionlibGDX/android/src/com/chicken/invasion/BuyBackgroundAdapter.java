package com.chicken.invasion;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pedramshirmohammad on 16-05-23.
 */
public class BuyBackgroundAdapter extends PagerAdapter {

    private Context ctx;


    public BuyBackgroundAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // card_view.xml LAYOUT
        View item_view = layoutInflater.inflate(R.layout.buy_background_view, container, false);
        TextView ted = (TextView)item_view.findViewById(R.id.textViewss);
        ted.setText("YELLOW");


        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
