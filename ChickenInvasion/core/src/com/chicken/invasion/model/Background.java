package com.chicken.invasion.model;

/**
 * Created by Albin on 2016-08-13.
 */
public class Background implements IStoreItem {
    private int price;
    private String name;
    private boolean purchased = false;
    private String imageURL;

    public Background(String imageURL, String name, int price){
        this.imageURL = imageURL;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean isPurchased() {
        return purchased;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public String getImageURL(){
        return this.imageURL;
    }
}
