package com.chicken.invasion.controller.collections;

import com.chicken.invasion.model.Background;
import java.util.ArrayList;

/**
 * Created by Albin on 2016-08-13.
 */
public class BackgroundCollection {
    private ArrayList<Background> backgrounds = new ArrayList<Background>();

    public BackgroundCollection(){
        Background desert = new Background("desertbackground500x900.png", "Desert", 150);
        desert.setPurchased(true);
        backgrounds.add(desert);

        Background farm = new Background("farmvillebackground.png", "Old Donald's Farm", 500);
        backgrounds.add(farm);
    }

    public ArrayList<Background> getBackgrounds() {
        return backgrounds;
    }
}
