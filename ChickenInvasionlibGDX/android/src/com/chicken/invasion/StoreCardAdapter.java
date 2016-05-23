package com.chicken.invasion;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pedramshirmohammad on 16-04-26.
 */
public class StoreCardAdapter extends PagerAdapter {


    private Context ctx;
    public List<ThrowableObject> mWeapons;
    private SharedPreferences pref;
    private SharedPreferences.Editor edit;
    private ChickenInvasion controller;

    public StoreCardAdapter(Context ctx, ChickenInvasion controller){
        this.ctx = ctx;
        this.controller = controller;
        this.mWeapons = controller.getThrowableHolder().getThrowables();
        pref = ctx.getSharedPreferences("myList", Context.MODE_PRIVATE);
        edit = pref.edit();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        mWeapons = controller.getThrowableHolder().getThrowables();
        ThrowableObject currWeapon = mWeapons.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // card_view.xml LAYOUT
        View item_view = layoutInflater.inflate(R.layout.card_layout, container, false);

        //IMAGE
        ImageView weaponImg = (ImageView) item_view.findViewById(R.id.weaponImg);
        Resources res = ctx.getResources();
        int resID = res.getIdentifier(currWeapon.getImageURL() , "drawable", ctx.getPackageName());
        weaponImg.setImageDrawable(res.getDrawable(resID));

        //WEAPON INFO
        ProgressBar damage = (ProgressBar) item_view.findViewById(R.id.damageIndicator);
        damage.setProgress((int) currWeapon.getDamage()*10);
        ProgressBar speed = (ProgressBar) item_view.findViewById(R.id.speedIndicator);
        speed.setProgress((int) (currWeapon.getSpeed()*10));

        TextView name = (TextView) item_view.findViewById(R.id.name);
        name.setText(currWeapon.getName());
        TextView price = (TextView) item_view.findViewById(R.id.price);
        if (currWeapon.isPurchased()){
            price.setText((currWeapon.getPrice() / 3) + "");
        }else {
            price.setText(currWeapon.getPrice() + "");
        }

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
