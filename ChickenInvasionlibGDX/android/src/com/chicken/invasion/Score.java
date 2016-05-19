package com.chicken.invasion;

/**
 * Created by pedramshirmohammad on 16-05-16.
 */
public class Score {

    private int points;
    private String name;

    public Score(String name,int points){
        this.points = points;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}
