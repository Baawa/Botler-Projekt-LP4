package com.chicken.invasion;

import java.util.Comparator;

/**
 * Created by Kristoffer on 2016-05-20.
 */
public class ScoreComparator implements Comparator<Score> {
    @Override
    public int compare(Score s1, Score s2) {
        if (s1.getPoints()>s2.getPoints()) return -1;

        if (s1.getPoints()==s2.getPoints()) return 0;

        return 1;
    }
}
