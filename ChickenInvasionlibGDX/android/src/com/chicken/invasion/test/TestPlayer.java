package com.chicken.invasion.test;

import android.test.InstrumentationTestCase;

import com.chicken.invasion.Player;

public class TestPlayer extends InstrumentationTestCase{
    private Player p;

    public void test() throws Exception {
        p = new Player();
        assertEquals(0,p.getChickenWings());
        assertEquals(0,p.getScore());
        p.incScore(10);
        assertEquals(10,p.getScore());
        assertEquals(10,p.getChickenWings());
        assertEquals(null,p.getCurrentTO());
        p.resetScore();
        assertEquals(0,p.getScore());
        assertEquals(10,p.getChickenWings());
    }

}
