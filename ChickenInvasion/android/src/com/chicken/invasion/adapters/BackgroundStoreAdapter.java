package com.chicken.invasion.adapters;

import android.content.Context;
import android.content.res.Resources;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chicken.invasion.GameController;
import com.chicken.invasion.R;
import com.chicken.invasion.model.Background;

import java.util.List;

/**
 * Created by pedramshirmohammad on 16-05-23.
 */
public class BackgroundStoreAdapter extends PagerAdapter {

    private Context ctx;
    private List<Background> mBackgrounds;
    private GameController controller;

    public BackgroundStoreAdapter(Context ctx, GameController controller){
        this.ctx = ctx;
        this.controller = controller;
        mBackgrounds = controller.getBackgrounds();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        Background currBackground = mBackgrounds.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // card_view.xml LAYOUT
        View item_view = layoutInflater.inflate(R.layout.buy_background_view, container, false);


        //IMAGE
        ImageView BackgroundImg = (ImageView) item_view.findViewById(R.id.Background_Img);
        Resources res = ctx.getResources();
        String url = currBackground.getImageURL().substring(0,currBackground.getImageURL().length()-4);
        int resID = res.getIdentifier( url, "drawable", ctx.getPackageName());
        BackgroundImg.setImageDrawable(res.getDrawable(resID));

        TextView name = (TextView) item_view.findViewById(R.id.background_name);
        name.setText(currBackground.getName());
        TextView price = (TextView) item_view.findViewById(R.id.background_price);
        if (currBackground.isPurchased()){
            price.setText((currBackground.getPrice() / 3) + "");
        }else {
            price.setText(currBackground.getPrice() + "");
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
        return mBackgrounds.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
