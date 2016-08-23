package com.chicken.invasion.model;

/**
 * Created by Albin on 2016-08-13.
 */
public class Player implements IPlayer {
    private int chickenWings;
    private int score;
    private Weapon weapon;

    public Player(Weapon weapon){
        this.weapon = weapon;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void setChickenLegs(int chickenWings) {
        this.chickenWings = chickenWings;
    }

    @Override
    public int getChickenLegs() {
        return this.chickenWings;
    }

    @Override
    public void setEquippedWeapon(Weapon weapon) {
        this.weapon = weapon.clone();
    }

    @Override
    public Weapon getEquippedWeapon() {
        return this.weapon.clone();
    }
}
