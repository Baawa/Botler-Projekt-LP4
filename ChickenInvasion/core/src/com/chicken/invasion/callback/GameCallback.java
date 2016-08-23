package com.chicken.invasion.callback;

import com.chicken.invasion.model.Background;
import com.chicken.invasion.model.Weapon;

/**
 * Created by Albin on 2016-08-14.
 */
public interface GameCallback {
    void onStartActivityStore();

    void onStartActivityHighScore();

    void onStartActivityInputName(int points);

    void saveScore(int score);

    int getChickenLegs();

    Weapon getTO();

    Background getEquippedBackground();

    void getTOUpgrade();
}
