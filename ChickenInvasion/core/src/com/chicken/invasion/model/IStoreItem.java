package com.chicken.invasion.model;

/**
 * Created by Albin on 2016-08-06.
 */
public interface IStoreItem {
    boolean isPurchased();

    String getImageURL();

    String getName();

    void setPurchased(boolean purchased);

    int getPrice();


}
