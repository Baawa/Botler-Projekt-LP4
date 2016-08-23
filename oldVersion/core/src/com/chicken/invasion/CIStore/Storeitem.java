package com.chicken.invasion.CIStore;

/**
 * Created by Albin on 2016-08-06.
 */
public interface Storeitem {
    boolean isPurchased();

    String getName();

    void setPurchased(boolean purchased);

    int getPrice();
}
