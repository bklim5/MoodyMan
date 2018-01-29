package com.bkbklim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bkbklim.Helpers.AssetLoader;
import com.bkbklim.Helpers.ThirdPartyController;
import com.bkbklim.MoodyMan;
import com.bkbklim.TweenAccessor.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;


/**
 * Created by boonkhailim on 14/9/17.
 */

public class SplashScreen implements Screen {

    private MoodyMan moodyMan;
    private ThirdPartyController thirdPartyController;
    private TweenManager tweenManager;
    private Sprite sprite, manSprite;
    private TextureRegion manTR;
    private SpriteBatch batcher;
    private float manTRy;
    private float width, height;
    private float manScale;

    public SplashScreen(MoodyMan moodyMan, ThirdPartyController thirdPartyController) {
        this.moodyMan = moodyMan;
        this.thirdPartyController = thirdPartyController;
    }

    @Override
    public void show() {
        sprite = new Sprite(AssetLoader.logoTR);
        sprite.setColor(1, 1, 1, 0);

        manSprite = new Sprite(AssetLoader.manTR);
        manSprite.setColor(1, 1, 1, 0);

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        float desiredWidth = width * 0.8f;
        float scale = desiredWidth / sprite.getWidth();

        //scale and center sprite
        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        sprite.setPosition(width / 2 - sprite.getWidth() / 2, height / 2 + sprite.getHeight() / 2);


        // roll manTR under logo!
        manTR = AssetLoader.manTR;
        manScale = manTR.getRegionWidth() / (width * 0.25f);

        manSprite.setSize(manTR.getRegionWidth() / manScale, manTR.getRegionHeight() / manScale);
        manSprite.setPosition(width / 2 - (manTR.getRegionWidth() / manScale) / 2, -1 * height / 5);
        manSprite.flip(false, true);

        setupTween();

        manTRy = -1 * height / 5;

        batcher = new SpriteBatch();

    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        tweenManager = new TweenManager();
        TweenCallback tweenCallback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                moodyMan.setScreen(new GameScreen(thirdPartyController));
            }
        };

        Tween.to(sprite, SpriteAccessor.ALPHA, 1.5f)
                .target(1)
                .ease(TweenEquations.easeInOutQuad)
                .repeatYoyo(1, 1.5f)
                .setCallback(tweenCallback)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .start(tweenManager);

        Tween.to(manSprite, SpriteAccessor.ALPHA, 1.5f)
                .target(1)
                .ease(TweenEquations.easeInOutQuad)
                .repeatYoyo(1, 1.5f)
                .start(tweenManager);

    }

    @Override
    public void render(float delta) {
        tweenManager.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();

        sprite.draw(batcher);
        manSprite.draw(batcher);

        manTRy += height / 100;
        if (manTRy > height / 2 - height / 30) {
            manTRy = height / 2 - height / 30;
        }
        manSprite.setY(manTRy);
        batcher.end();
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
