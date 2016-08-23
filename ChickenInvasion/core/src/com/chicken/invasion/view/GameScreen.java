package com.chicken.invasion.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.chicken.invasion.GameController;
import com.chicken.invasion.controller.EnemyController;

import java.util.ListIterator;

/**
 * Created by Kristoffer on 2016-08-20.
 */

public class GameScreen extends BackgroundScreen implements IScreen {

    public GameScreen(SpriteBatch batch, GameController controller) {
        super(batch, controller);
    }

    @Override
    public void render(float dt){
        super.render(dt);
        // Generate an iterator. Start just after the last element.
        ListIterator<EnemyController> li = controller.getActiveEnemies().listIterator(controller.getActiveEnemies().size());

        // Iterate in reverse to draw them correct on the screen.
        while(li.hasPrevious()) {
            EnemyController eCon = li.previous();
            //draw
            eCon.update();
            batch.draw(eCon.getCurrentFrame(),eCon.getEnemyX(),eCon.getEnemyY(),eCon.getEnemyWidth(),eCon.getEnemyHeight());
        }

        //draw weapon
        if (controller.getCurrentWeapon().getSprite() != null)
            controller.getCurrentWeapon().getSprite().draw(batch);

        //draw score font
        controller.getFontScore().draw(batch,
                String.valueOf(controller.getPlayerScore()),
                (float) Gdx.graphics.getWidth() / 200 - 0.5f,
                (float) Gdx.graphics.getHeight() / 100 - 2,
                0.1f,
                Align.center,
                false);

        //Draw pauseBtn
        controller.getButtonAt(5).getSprite().draw(batch); // draw pauseBtn
    }

    @Override
    public void dispose(){

    }
}
