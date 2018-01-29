package com.bkbklim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.bkbklim.Helpers.InputHandler;
import com.bkbklim.Helpers.ThirdPartyController;
import com.bkbklim.Logic.GameRenderer;
import com.bkbklim.Logic.GameWorld;

import static com.bkbklim.Helpers.Constants.GAME_HEIGHT;

/**
 * Created by boonkhailim on 14/9/17.
 */

public class GameScreen implements Screen {

    private ThirdPartyController thirdPartyController;
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime = 0;

    public GameScreen(ThirdPartyController thirdPartyController) {
        this.thirdPartyController = thirdPartyController;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameHeight = GAME_HEIGHT;
        float scaleFactorY = (screenHeight / gameHeight);  // we will fix the height
        float gameWidth = (screenWidth / scaleFactorY);
        int midPointX = (int) (gameWidth / 2);

        world = new GameWorld(midPointX);
        Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight));
        renderer = new GameRenderer(world, (int) gameWidth, midPointX);
        world.setRenderer(renderer);
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);
    }


    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
