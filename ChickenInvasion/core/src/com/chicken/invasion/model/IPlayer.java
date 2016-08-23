package com.chicken.invasion.model;

/**
 * Created by Albin on 2016-08-06.
 */
public interface IPlayer {
    void setScore(int score);
    int getScore();

    void setChickenLegs(int chickenLegs);
    int getChickenLegs();

    void setEquippedWeapon(Weapon weapon);
    Weapon getEquippedWeapon(); // Remember to return a copied version of the Weapon!
}