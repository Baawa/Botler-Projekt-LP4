package com.chicken.invasion;

/**
 * Created by pedramshirmohammad on 16-05-25.
 */
public class Background implements iItem{

    private String name;
    private String imageURL;
    private boolean purchased;
    private int price;

    public Background(String name,String imageURL,int price){
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        purchased = false;
    }

    @Override
    public boolean isPurchased() {
        return purchased;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }
}
