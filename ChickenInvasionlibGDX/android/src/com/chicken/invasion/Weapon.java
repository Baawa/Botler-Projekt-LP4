package com.chicken.invasion;

/**
 * Created by pedramshirmohammad on 16-04-26.
 */
public abstract class Weapon {

    private int streangth;
    private int speed;
    private int img;

    public Weapon(int streangth,int speed,int img){
        this.streangth = streangth;
        this.speed = speed;
        this.img = img;
    }

    public int getStreangth() {
        return streangth;
    }

    public int getSpeed() {
        return speed;
    }

    public int getImg() {
        return img;
    }
}
