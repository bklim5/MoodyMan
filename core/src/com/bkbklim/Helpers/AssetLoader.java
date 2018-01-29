package com.bkbklim.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boonkhailim on 14/9/17.
 */

public class AssetLoader {

    private static Preferences prefs;

    private static Texture texture, logoTexture, manTexture, manRightTexture, manLeftTexture;
    public static TextureRegion logoTR, manTR, manRightTR, manLeftTR, bgTR, bgNightTR, grassTR,
            scoreBoard, star, noStar;
    public static TextureRegion wifeTR, workTR, moneyTR;
    public static TextureRegion playButtonUp, playButtonDown, retryButtonUp, retryButtonDown,
            mainMenuButtonUp, mainMenuButtonDown;
    public static BitmapFont font, shadow, whiteFont, smallWhiteFont, smallShadowFont,
            mediumGreenFont, mediumShadowFont, smallGreenFont;
    public static Animation<TextureRegion> manAnimation;
    public static Sound dead, swoosh, coin;

    private static List<Texture> textureToDispose = new ArrayList<>();
    private static List<Sound> soundToDispose = new ArrayList<>();
    private static List<BitmapFont> fontToDispose = new ArrayList<>();

    public static void load() {

        texture = new Texture(Gdx.files.internal("img/texture.png"));

        logoTexture = new Texture("img/smileProduction.png");
        logoTR = new TextureRegion(logoTexture, 0, 0, 760, 300);

        manTexture = new Texture("img/moodyManNeutral.png");
        manRightTexture = new Texture("img/moodyManRight.png");
        manLeftTexture = new Texture("img/moodyManLeft.png");
        manTR = new TextureRegion(manTexture);
        manRightTR = new TextureRegion(manRightTexture);
        manLeftTR = new TextureRegion(manLeftTexture);
        manTR.flip(false, true);
        manRightTR.flip(false, true);
        manLeftTR.flip(false, true);

        TextureRegion[] men = {manLeftTR, manTR, manRightTR};
        manAnimation = new Animation<>(0.1f, men);
        manAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


        bgTR = new TextureRegion(new Texture("bg/bg.png"));
        bgTR.flip(false, true);

        bgNightTR = new TextureRegion(new Texture("bg/bgdark.png"));
        bgNightTR.flip(false, true);

        grassTR = new TextureRegion(texture, 0, 43, 143, 11);
        grassTR.flip(false, true);

        Texture wifeTexture = new Texture("img/wife.png");
        wifeTR = new TextureRegion(wifeTexture);
        wifeTR.flip(false, true);

        Texture workTexture = new Texture("img/work.png");
        workTR = new TextureRegion(workTexture);
        workTR.flip(false, true);

        Texture moneyTexture = new Texture("img/money.png");
        moneyTR = new TextureRegion(moneyTexture);
        moneyTR.flip(false, true);


        scoreBoard = new TextureRegion(texture, 152, 57, 97, 37);
        scoreBoard.flip(false, true);

        star = new TextureRegion(texture, 226, 96, 10, 10);
        star.flip(false, true);

        noStar = new TextureRegion(texture, 239, 96, 10, 10);
        noStar.flip(false, true);

        //menu play buttons
        playButtonUp = new TextureRegion(texture, 0, 57, 29, 16);
        playButtonDown = new TextureRegion(texture, 29, 57, 29, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        //retry button
        retryButtonUp = new TextureRegion(texture, 0, 73, 31, 16);
        retryButtonDown = new TextureRegion(texture, 31, 73, 31, 16);
        retryButtonUp.flip(false, true);
        retryButtonDown.flip(false, true);

        //back to main menu button
        mainMenuButtonUp = new TextureRegion(texture, 62, 73, 43, 16);
        mainMenuButtonDown = new TextureRegion(texture, 105, 73, 43, 16);
        mainMenuButtonUp.flip(false, true);
        mainMenuButtonDown.flip(false, true);

        textureToDispose.add(texture);
        textureToDispose.add(logoTexture);
        textureToDispose.add(manTexture);
        textureToDispose.add(manRightTexture);
        textureToDispose.add(manLeftTexture);
        textureToDispose.add(wifeTexture);
        textureToDispose.add(workTexture);
        textureToDispose.add(moneyTexture);

        // font
        font = new BitmapFont(Gdx.files.internal("text/text.fnt"));
        shadow = new BitmapFont(Gdx.files.internal("text/shadow.fnt"));
        whiteFont = new BitmapFont(Gdx.files.internal("text/whitetext.fnt"));
        smallWhiteFont = new BitmapFont(Gdx.files.internal("text/whitetext.fnt"));
        smallShadowFont = new BitmapFont(Gdx.files.internal("text/shadow.fnt"));
        mediumShadowFont = new BitmapFont(Gdx.files.internal("text/shadow.fnt"));
        mediumGreenFont = new BitmapFont(Gdx.files.internal("text/text.fnt"));
        smallGreenFont = new BitmapFont(Gdx.files.internal("text/text.fnt"));
        font.getData().setScale(.25f, -.25f);
        shadow.getData().setScale(.25f, -.25f);
        whiteFont.getData().setScale(.25f, -.25f);
        smallWhiteFont.getData().setScale(.1f, -.1f);
        smallShadowFont.getData().setScale(.1f, -.1f);
        mediumGreenFont.getData().setScale(.2f, -.2f);
        mediumShadowFont.getData().setScale(.2f, -.2f);
        smallGreenFont.getData().setScale(.15f, -.15f);

        fontToDispose.add(font);
        fontToDispose.add(shadow);
        fontToDispose.add(whiteFont);
        fontToDispose.add(smallWhiteFont);
        fontToDispose.add(smallShadowFont);
        fontToDispose.add(mediumShadowFont);
        fontToDispose.add(mediumGreenFont);
        fontToDispose.add(smallGreenFont);

        // sound
        dead = Gdx.audio.newSound(Gdx.files.internal("music/die.wav"));
        swoosh = Gdx.audio.newSound(Gdx.files.internal("music/swoosh.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("music/score.wav"));

        soundToDispose.add(dead);
        soundToDispose.add(swoosh);
        soundToDispose.add(coin);

        prefs = Gdx.app.getPreferences("com.bkbklim.MoodyMan");

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

    }

    public static void dispose() {
        for (Texture toDispose: textureToDispose) {
            toDispose.dispose();
        }

        for (Sound toDispose: soundToDispose) {
            toDispose.dispose();
        }

        for (BitmapFont toDispose: fontToDispose) {
            toDispose.dispose();
        }

    }

    public static void setHighScore(int score) {
        prefs.putInteger("highScore", score);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore", 0);
    }
}
