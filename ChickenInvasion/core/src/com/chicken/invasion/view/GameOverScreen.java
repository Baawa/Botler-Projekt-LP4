package com.chicken.invasion.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chicken.invasion.GameController;
import com.chicken.invasion.controller.EnemyController;

import java.util.ListIterator;

/**
 * Created by Kristoffer on 2016-08-20.
 */

public class GameOverScreen extends BackgroundScreen implements IScreen {

    public GameOverScreen(SpriteBatch batch, GameController controller) {
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
            batch.draw(eCon.getCurrentFrame(),eCon.getEnemyX(),eCon.getEnemyY(),eCon.getEnemyWidth(),eCon.getEnemyHeight());
        }

        //draw weapon
        if (controller.getCurrentWeapon().getSprite() != null)
            controller.getCurrentWeapon().getSprite().draw(batch);

        controller.getBannerAt(3).draw(batch); // draw Game Over Banner

        controller.getButtonAt(2).getSprite().draw(batch); // draw restartBtn

        controller.getButtonAt(1).getSprite().draw(batch); // draw backBtn
    }

    @Override
    public void dispose(){

    }
}
