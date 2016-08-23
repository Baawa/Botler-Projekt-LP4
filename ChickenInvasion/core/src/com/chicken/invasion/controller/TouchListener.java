package com.chicken.invasion.controller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.chicken.invasion.model.Button;
import com.chicken.invasion.model.GameModel;
import com.chicken.invasion.model.GameState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristoffer on 2016-08-17.
 */

public class TouchListener implements GestureDetector.GestureListener {

    private Camera camera;

    /*
    buttons index:
    #0 - start
    #1 - back
    #2 - restart
    #3 - highscore
    #4 - store
    #5 - pause
    #6 - mute
    #7 - unmute
    #8 - settings
     */
    private List<ButtonController> buttons;

    public TouchListener(Camera camera, List<ButtonController> buttons){
        this.camera = camera;
        this.buttons = buttons;
    }
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector3 coords=new Vector3(x,y,0);
        Vector3 coords2= camera.unproject(coords);


        //Check which button was pressed
        if (GameModel.getInstance().getState() == GameState.RUNNING){
            buttons.get(5).getButton().clicked(coords2.x,coords2.y); //pause
        }

        if (GameModel.getInstance().getState() == GameState.PAUSED){
            buttons.get(0).getButton().clicked(coords2.x,coords2.y);//start
            buttons.get(1).getButton().clicked(coords2.x, coords2.y);// back
            buttons.get(2).getButton().clicked(coords2.x, coords2.y);// restart
        }

        if (GameModel.getInstance().getState() == GameState.STOPPED){
            buttons.get(0).getButton().clicked(coords2.x,coords2.y); // start
            buttons.get(4).getButton().clicked(coords2.x,coords2.y); // store
            buttons.get(3).getButton().clicked(coords2.x, coords2.y);// highscore
            buttons.get(8).getButton().clicked(coords2.x, coords2.y);// settings
            if (GameModel.getInstance().isShowSettings()){
                if (!GameModel.getInstance().isMusicOn()){
                    buttons.get(7).getButton().clicked(coords2.x, coords2.y); //unmute
                } else{
                    buttons.get(6).getButton().clicked(coords2.x, coords2.y); // mute
                }
            }
        }

        if (GameModel.getInstance().getState() == GameState.GAMEOVER){
            buttons.get(2).getButton().clicked(coords2.x, coords2.y);//restart
            buttons.get(1).getButton().clicked(coords2.x, coords2.y); // back
        }

        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (GameModel.getInstance().getState() == GameState.RUNNING && GameModel.getInstance().getCurrentWeapon() != null) {
            if ((x >= GameModel.getInstance().getCurrentWeapon().getX()
                    || x <= GameModel.getInstance().getCurrentWeapon().getX() + 100) && (y >= GameModel.getInstance().getCurrentWeapon().getY()
                    || y <= GameModel.getInstance().getCurrentWeapon().getY() + 100) && -deltaY > 0) {
                float throwX = deltaX;
                float throwY = -deltaY;
                float totalLength = (float) (Math.sqrt(Math.pow(throwX, 2) + Math.pow(throwY, 2)));
                if (throwY > 0) {
                    GameModel.getInstance().getCurrentWeapon().throwToPoint(throwX/totalLength, throwY/totalLength);
                }

            }
        }
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
