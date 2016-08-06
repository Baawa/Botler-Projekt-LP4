package com.chicken.invasion.Store;

/**
 * Created by Albin on 2016-08-06.
 */
public interface StoreItem {
    boolean isPurchased();

    String getName();

    void setPurchased(boolean purchased);

    int getPrice();
}
