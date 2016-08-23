package com.chicken.invasion.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chicken.invasion.GameController;

/**
 * Created by Kristoffer on 2016-08-19.
 */

public class StartScreen extends BackgroundScreen implements IScreen{



    public StartScreen(SpriteBatch batch, GameController controller) {
        super(batch, controller);
    }

    @Override
    public void render(float dt){
        super.render(dt);

        controller.getBannerAt(1).draw(batch);
        controller.getButtonAt(0).getSprite().draw(batch); // draw startBtn

        controller.getButtonAt(4).getSprite().draw(batch); // draw storeBtn
        controller.getButtonAt(3).getSprite().draw(batch); // draw highscoreBtn
        controller.getButtonAt(8).getSprite().draw(batch); // draw settingsBtn

        if (controller.isShowSettings()){
            controller.getBannerAt(0).draw(batch);// draw settings banner

            if (controller.isMusicOn()){
                controller.getButtonAt(6).getSprite().draw(batch); // draw muteBtn
            } else{
                controller.getButtonAt(7).getSprite().draw(batch); // draw unmuteBtn
            }
        }
    }

    @Override
    public void dispose(){
        super.dispose();

    }
}
