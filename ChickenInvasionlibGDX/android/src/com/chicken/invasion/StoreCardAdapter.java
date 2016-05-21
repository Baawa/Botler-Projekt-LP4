package com.chicken.invasion;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pedramshirmohammad on 16-04-26.
 */
public class StoreCardAdapter extends PagerAdapter {


    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<ThrowableObject> mWeapons;
    private ImageView weaponImg;
    private HashMap<String,Boolean> availability;
    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    public StoreCardAdapter(Context ctx, List<ThrowableObject> mWeapons,HashMap<String,Boolean> availability){
        this.ctx = ctx;
        this.mWeapons = mWeapons;
        this.availability = availability;
        pref = ctx.getSharedPreferences("myList", Context.MODE_PRIVATE);
        edit = pref.edit();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.card_layout,container,false);
        CardView card = (CardView)item_view.findViewById(R.id.weapon_view);
        ProgressBar strength = (ProgressBar) item_view.findViewById(R.id.streangthIndicator);
        weaponImg = (ImageView)item_view.findViewById(R.id.weapon_img);


        Drawable mDrawable = item_view.getResources().getDrawable(R.drawable.pan100x100);

        /*if (availability.get(mWeapons.get(position).getName())){
            buyBtn.setText("EQUIP");
        }


        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                availability.put(mWeapons.get(position).getName(),true);
                for (Map.Entry<String,Boolean> e: availability.entrySet()){
                    edit.putBoolean(e.getKey(),e.getValue());
                    edit.commit();
                }
            }
        });*/


        strength.setProgress((int)mWeapons.get(position).getDamage()*10);
        weaponImg.setImageDrawable(mDrawable);
        container.addView(item_view);


        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }

    @Override
    public int getCount() {
        return mWeapons.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
