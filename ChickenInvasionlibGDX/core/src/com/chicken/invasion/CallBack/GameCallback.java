package com.chicken.invasion.CallBack;

import com.chicken.invasion.Helpers.CIBackground;
import com.chicken.invasion.Weapons.CIWeapon;

/**
 * Created by Albin on 2016-08-14.
 */
public interface GameCallback {
    void onStartActivityStore();

    void onStartActivityHighScore();

    void onStartActivityInputName(int points);

    void saveScore(int score);

    int getChickenLegs();

    CIWeapon getTO();

    CIBackground getEquippedBackground();

    void getTOUpgrade();
}
