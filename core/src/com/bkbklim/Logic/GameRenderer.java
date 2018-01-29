package com.bkbklim.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bkbklim.GameObjects.Man;
import com.bkbklim.GameObjects.ObstacleScrollable;
import com.bkbklim.GameObjects.ScrollHandler;
import com.bkbklim.Helpers.AssetLoader;
import com.bkbklim.Helpers.InputHandler;
import com.bkbklim.TweenAccessor.BitmapFontCacheAccessor;
import com.bkbklim.TweenAccessor.Value;
import com.bkbklim.TweenAccessor.ValueAccessor;
import com.bkbklim.UserInterface.SimpleButton;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import static com.bkbklim.Helpers.Constants.GAME_HEIGHT;
import static com.bkbklim.Helpers.Constants.GAME_TITLE;
import static com.bkbklim.Helpers.Constants.GAP_BETWEEN_ONE_SET_OF_OBSTACLES;

/**
 * Created by boonkhailim on 14/9/17.
 */

public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private int gameWidth;
    private int midPointX;

    // game objects
    private Man man;
    private ScrollHandler scroller;
    private ObstacleScrollable obstacle1, obstacle2, obstacle3;

    // game assets
    private TextureRegion bgTR, bgNightTR, grass, manLeftTR, manTR, manRightTR;
    private TextureRegion wifeTR;
    private TextureRegion scoreBoard, star, noStar;
    private Animation<TextureRegion> manAnimation;
    private BitmapFontCache whiteFontCache, shadowFontCache, titleWhiteFontCache,
            titleShadowFontCache, smallWhiteFontCache, smallShadowFontCache;
    private GlyphLayout gameTitleLayout, gameOverLayout, highScoreLayout, readyLayout;

    // tween stuff
    private TweenManager manager, gameTitleManager;
    private Value alpha;
    private Color transitionColor;

    // Buttons
    private SimpleButton playButton, mainMenuButton, retryButton;



    public GameRenderer(GameWorld world, int gameWidth, int midPointX) {
        this.myWorld = world;

        this.gameWidth = gameWidth;
        this.midPointX = midPointX;

        // project 3D into a 2D plane
        cam = new OrthographicCamera();
        cam.setToOrtho(true, gameWidth, GAME_HEIGHT);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();

        alpha = new Value();
        transitionColor = new Color();
        prepareTransition(0, 0, 0, 1f);
        prepareMenuTween();
    }


    public void initGameObjects() {
        man = myWorld.getMan();
        scroller = myWorld.getScroller();
        obstacle1 = scroller.getObstacle1();
        obstacle2 = scroller.getObstacle2();
        obstacle3 = scroller.getObstacle3();

    }

    public void initAssets() {
        bgTR = AssetLoader.bgTR;
        bgNightTR = AssetLoader.bgNightTR;
        grass = AssetLoader.grassTR;
        manLeftTR = AssetLoader.manLeftTR;
        manTR = AssetLoader.manTR;
        manRightTR = AssetLoader.manRightTR;
        manAnimation = AssetLoader.manAnimation;
        scoreBoard = AssetLoader.scoreBoard;
        star = AssetLoader.star;
        noStar = AssetLoader.noStar;
        wifeTR = AssetLoader.wifeTR;

        whiteFontCache = new BitmapFontCache(AssetLoader.whiteFont, true);
        shadowFontCache = new BitmapFontCache(AssetLoader.shadow, true);
        smallWhiteFontCache = new BitmapFontCache(AssetLoader.smallWhiteFont, true);
        smallShadowFontCache = new BitmapFontCache(AssetLoader.smallShadowFont, true);
        titleShadowFontCache = new BitmapFontCache(AssetLoader.shadow, true);
        titleWhiteFontCache = new BitmapFontCache(AssetLoader.whiteFont, true);

        playButton = ((InputHandler) Gdx.input.getInputProcessor()).getPlayButton();
        retryButton = ((InputHandler) Gdx.input.getInputProcessor()).getRetryButton();
        mainMenuButton = ((InputHandler) Gdx.input.getInputProcessor()).getMainMenuButon();

        gameTitleLayout = new GlyphLayout();
        highScoreLayout = new GlyphLayout();
        gameOverLayout = new GlyphLayout();
        readyLayout = new GlyphLayout();

    }

    public void render(float delta, float runTime) {
        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, gameWidth, 204);


        // End ShapeRenderer
        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();

        if (myWorld.isNight()) {
            batcher.draw(bgNightTR, 0, 0, gameWidth, 204);
        } else {
            batcher.draw(bgTR, 0, 0, gameWidth, 204);
        }

        batcher.enableBlending();
        if (myWorld.isRunning()) {
            drawObstacles();
            drawScore();
            drawMan();
        } else if (myWorld.isMenu()) {
            drawGameTitle(delta);
            drawMan();
            drawMenuUI();
        } else if (myWorld.isReady()) {
            drawMan();
            drawReady();
            drawScore();
        } else if (myWorld.isGameOver()) {
            drawGameOver();
            drawScoreboard();
            drawRetry();
        } else if (myWorld.isHighScore()) {
            drawHighScore();
            drawScoreboard();
            drawRetry();
        }


        // End SpriteBatch
        batcher.disableBlending();
        batcher.end();

        drawTransition(delta);
    }

    private void drawObstacles() {
        obstacle1.draw(batcher);
        obstacle2.draw(batcher);
        obstacle3.draw(batcher);
    }

    private void drawMan() {
        // Draw man at its coordinates. Retrieve the Animation object from
        // AssetLoader
        // Pass in the runTime variable to get the current frame.
        if (man.isAlive() && myWorld.isRunning()) {
            if (man.isFacingRight()) {
                batcher.draw(manRightTR, man.getX(), man.getY(), man.getWidth() / 2.0f,
                        man.getHeight() / 2.0f, man.getWidth(), man.getHeight(),
                        1, 1, man.getRotation());
            } else {
                batcher.draw(manLeftTR, man.getX(), man.getY(), man.getWidth() / 2.0f,
                        man.getHeight() / 2.0f, man.getWidth(), man.getHeight(),
                        1, 1, man.getRotation());
            }
        } else {
            batcher.draw(manTR, man.getX(), man.getY(), man.getWidth() / 2.0f,
                    man.getHeight() / 2.0f, man.getWidth(), man.getHeight(), 1, 1, 0);
        }
    }

    private void drawScore() {
        // Convert integer into String
        String score = Integer.toString(myWorld.getScore());

        // Draw shadow first
        AssetLoader.shadow.draw(batcher, score, (gameWidth / 2) - (3 * score.length()), GAME_HEIGHT / 4);
        // Draw text
        AssetLoader.whiteFont.draw(batcher, score, (gameWidth / 2) - (3 * score.length() - 1), GAME_HEIGHT / 4);
    }

    private void drawGameTitle(float delta) {
        gameTitleLayout.setText(titleShadowFontCache.getFont(), GAME_TITLE);
        titleShadowFontCache.draw(batcher);
        titleWhiteFontCache.draw(batcher);
        gameTitleManager.update(delta);
    }

    private void drawMenuUI() {
        playButton.drawButton(batcher);
    }

    private void drawReady() {
        readyLayout.setText(whiteFontCache.getFont(), "TAP TO MOVE");
        whiteFontCache.setText(readyLayout, midPointX - readyLayout.width / 2, GAME_HEIGHT / 2);
        shadowFontCache.setText(readyLayout, midPointX - readyLayout.width / 2, GAME_HEIGHT / 2 - 1);
        shadowFontCache.draw(batcher);
        whiteFontCache.draw(batcher);
    }

    private void drawGameOver() {
        gameOverLayout.setText(whiteFontCache.getFont(), "GAME OVER");
        whiteFontCache.setText(gameOverLayout, midPointX - gameOverLayout.width / 2, GAME_HEIGHT / 2 - 56);
        shadowFontCache.setText(gameOverLayout, midPointX - gameOverLayout.width / 2, GAME_HEIGHT / 2 - 55);
        shadowFontCache.draw(batcher);
        whiteFontCache.draw(batcher);
    }

    private void drawHighScore() {
        highScoreLayout.setText(whiteFontCache.getFont(), "HIGH SCORE!");
        whiteFontCache.setText(highScoreLayout, midPointX - highScoreLayout.width / 2, GAME_HEIGHT / 2 - 56);
        shadowFontCache.setText(highScoreLayout, midPointX - highScoreLayout.width / 2, GAME_HEIGHT / 2 - 55);
        shadowFontCache.draw(batcher);
        whiteFontCache.draw(batcher);
    }

    private void drawScoreboard() {
        batcher.draw(scoreBoard, midPointX - scoreBoard.getRegionWidth() / 2, GAME_HEIGHT / 2 - 20, scoreBoard.getRegionWidth(), scoreBoard.getRegionHeight());

        float startStarX = midPointX - scoreBoard.getRegionWidth() / 2 + 3f;
        batcher.draw(noStar, startStarX, GAME_HEIGHT / 2  - 5, noStar.getRegionWidth(), noStar.getRegionHeight());
        batcher.draw(noStar, startStarX + noStar.getRegionWidth() + 2f, GAME_HEIGHT / 2  - 5, noStar.getRegionWidth(), noStar.getRegionHeight());
        batcher.draw(noStar, startStarX + (noStar.getRegionWidth() + 2f) * 2, GAME_HEIGHT / 2  - 5, noStar.getRegionWidth(), noStar.getRegionHeight());
        batcher.draw(noStar, startStarX + (noStar.getRegionWidth() + 2f) * 3, GAME_HEIGHT / 2  - 5, noStar.getRegionWidth(), noStar.getRegionHeight());
        batcher.draw(noStar, startStarX + (noStar.getRegionWidth() + 2f) * 4, GAME_HEIGHT / 2  - 5, noStar.getRegionWidth(), noStar.getRegionHeight());

        if (myWorld.getScore() > 5) {
            batcher.draw(star, startStarX + (star.getRegionWidth() + 2f) * 4, GAME_HEIGHT / 2  - 5, star.getRegionWidth(), star.getRegionHeight());
        }

        if (myWorld.getScore() > 15) {
            batcher.draw(star, startStarX + (star.getRegionWidth() + 2f) * 3, GAME_HEIGHT / 2  - 5, star.getRegionWidth(), star.getRegionHeight());
        }

        if (myWorld.getScore() > 30) {
            batcher.draw(star, startStarX + (star.getRegionWidth() + 2f) * 2, GAME_HEIGHT / 2  - 5, star.getRegionWidth(), star.getRegionHeight());
        }

        if (myWorld.getScore() > 60) {
            batcher.draw(star, startStarX + star.getRegionWidth() + 2f, GAME_HEIGHT / 2  - 5, star.getRegionWidth(), star.getRegionHeight());
        }

        if (myWorld.getScore() > 100) {
            batcher.draw(star, startStarX, GAME_HEIGHT / 2  - 5, star.getRegionWidth(), star.getRegionHeight());
        }

        int length = ("" + myWorld.getScore()).length();

        AssetLoader.smallWhiteFont.draw(batcher, "" + myWorld.getScore(),
                100 - (2 * length), GAME_HEIGHT / 2  - 10);

        int length2 = ("" + AssetLoader.getHighScore()).length();
        AssetLoader.smallWhiteFont.draw(batcher, "" + AssetLoader.getHighScore(),
                100 - (2f * length2), GAME_HEIGHT / 2  + 7);
    }

    private void drawRetry() {
        retryButton.drawButton(batcher);
        mainMenuButton.drawButton(batcher);
    }


    public void prepareTransition(int r, int g, int b, float duration) {
        alpha.setValue(1);
        transitionColor.set(r / 255f, g / 255f, b / 255f, 1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();

        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    public void prepareMenuTween() {
        Tween.registerAccessor(BitmapFontCache.class, new BitmapFontCacheAccessor());
        gameTitleManager = new TweenManager();

        gameTitleLayout.setText(titleShadowFontCache.getFont(), GAME_TITLE);

        titleShadowFontCache.setText(gameTitleLayout, gameWidth / 2 - gameTitleLayout.width / 2, GAME_HEIGHT / 4);
        titleWhiteFontCache.setText(gameTitleLayout, gameWidth / 2 - gameTitleLayout.width / 2, GAME_HEIGHT / 4 - 1);

        Tween.from(titleShadowFontCache, BitmapFontCacheAccessor.POSITION_Y, 1.0f)
                .target(GAME_HEIGHT / 2)
                .ease(TweenEquations.easeOutQuad)
                .start(gameTitleManager);

        Tween.from(titleWhiteFontCache, BitmapFontCacheAccessor.POSITION_Y, 1.0f)
                .target(GAME_HEIGHT / 2)
                .ease(TweenEquations.easeOutQuad)
                .start(gameTitleManager);

    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
}
