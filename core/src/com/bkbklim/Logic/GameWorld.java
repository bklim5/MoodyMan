package com.bkbklim.Logic;

import com.bkbklim.GameObjects.Man;
import com.bkbklim.GameObjects.ScrollHandler;
import com.bkbklim.Helpers.AssetLoader;

import java.util.Random;

/**
 * Created by boonkhailim on 14/9/17.
 */

public class GameWorld {

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    public enum BgState {
        DAY, NIGHT
    }

    private Man man;
    private ScrollHandler scroller;
    private GameRenderer renderer;
    private int midPointX;
    private int score;
    private GameState currentState;
    private BgState currentBgState;
    private Random r;
    private float runTime = 0;
    private static final int manPosY = 155;


    public GameWorld(int midPointX) {
        man = new Man(midPointX - 15, manPosY, 30, 25);
        scroller = new ScrollHandler(this, midPointX * 2);
        this.midPointX = midPointX;
        currentState = GameState.MENU;
        currentBgState = BgState.DAY;
        r = new Random();
    }

    public void update(float delta) {
        runTime += delta;
        switch (currentState) {
            case MENU:
                man.updateMenu(runTime);
                break;
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }

    }

    private void updateReady(float delta) {
        man.updateReady(runTime);
        scroller.updateReady(delta);

    }

    private void updateRunning(float delta) {
        man.update(delta);
        scroller.update(delta);

        if (man.isAlive() && (scroller.collides(man) || man.hitWall(midPointX * 2))) {
            scroller.stop();
            man.die();
            AssetLoader.dead.play();
        }

        if (!man.isAlive() && man.isFullyDead()) {
            currentState = GameState.GAMEOVER;
            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }
    }

    public void menu() {
        currentState = GameState.MENU;
        renderer.prepareTransition(0, 0, 0, 1f);
//        if (!controller.isAdShown()) {
//            controller.showBannerAd();
//        }

        renderer.prepareMenuTween();


    }

    public void ready() {
        restart();
    }

    public void start() {
        AssetLoader.swoosh.play();
        currentState = GameState.RUNNING;
    }

    public void restart() {
        currentState = GameState.READY;
        currentBgState = (r.nextInt(2) == 0)?BgState.DAY:BgState.NIGHT;
        score = 0;
        man.onRestart(midPointX - 5, manPosY);
        scroller.onRestart();
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() { return currentState == GameState.MENU; }

    public boolean isRunning() { return currentState == GameState.RUNNING; }

    public boolean isDay() { return currentBgState == BgState.DAY; }

    public boolean isNight() { return currentBgState == BgState.NIGHT; }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
        if (getScore() % 5 == 0) {
            scroller.decreaseGap();
        }

        if (getScore() % 10 == 0) {
            man.accelerate();
        }

        if (getScore() % 20 == 0) {
            scroller.accelerate();
        }
    }

    public Man getMan() {
        return man;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getMidPointX() {
        return midPointX;
    }

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }
}
