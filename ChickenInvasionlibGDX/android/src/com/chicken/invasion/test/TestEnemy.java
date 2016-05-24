package com.chicken.invasion.test;

import android.test.InstrumentationTestCase;

import com.chicken.invasion.Enemy;

public class TestEnemy extends InstrumentationTestCase {
    Enemy e;
    public void test() throws Exception {
        e = new Enemy();
        assertEquals(true, e.getPush());
        assertEquals(2.0f,e.getHeight());
        assertEquals(2.0f,e.getWidth());
        assertEquals(1.0,e.getHealth());
        e.decHealth(1);
        assertEquals(0.0,e.getHealth());
        assertEquals(1.0,e.getTotalHealth());
        float y = e.getY();
        e.incY();
        assertEquals(y+1,e.getY());
        e.setPush(false);
        assertEquals(false,e.getPush());
    }

}
