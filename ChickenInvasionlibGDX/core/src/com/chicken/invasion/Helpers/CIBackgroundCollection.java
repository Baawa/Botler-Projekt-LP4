package com.chicken.invasion.Helpers;

import com.chicken.invasion.Store.StoreCollection;
import com.chicken.invasion.Store.StoreItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albin on 2016-08-13.
 */
public class CIBackgroundCollection implements StoreCollection {
    private ArrayList<CIBackground> backgrounds = new ArrayList<CIBackground>();

    public CIBackgroundCollection(){
        CIBackground desert = new CIBackground("desertbackground500x900.png", "Desert", 150);
        desert.setPurchased(true);
        backgrounds.add(desert);

        CIBackground farm = new CIBackground("farmvillebackground.png", "Old Donald's Farm", 500);
        backgrounds.add(farm);
    }

    @Override
    public ArrayList<CIBackground> getStoreItems() {
        return backgrounds;
    }
}
