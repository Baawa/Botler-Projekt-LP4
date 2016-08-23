package com.chicken.invasion.test;

import android.test.InstrumentationTestCase;

import com.chicken.invasion.oldstuff.Model;

public class TestModel extends InstrumentationTestCase{

    private Model m;

    public void test() throws Exception {
        m = Model.getInstance();
        testDiff(2, 4);
        m.nextWave();
        testDiff(4, 6);
        m.restartWaves();
        testDiff(2, 4);

        //States
        assertEquals(Model.State.STOPPED, m.getState());
        m.startGame();
        assertEquals(Model.State.RUNNING, m.getState());
        m.pauseGame();
        assertEquals(Model.State.PAUSED, m.getState());
        m.gameOver();
        assertEquals(Model.State.GAMEOVER,m.getState());

        //Spawn
        assertEquals(false,m.shouldSpawnObject());
        m.setSpawnObject(true);
        assertEquals(true,m.shouldSpawnObject());

    }

    private void testDiff(int d, int t){
        assertEquals(d,m.getDifficulty());
        assertEquals(t,m.getNumberOfThrowables());
    }

}
