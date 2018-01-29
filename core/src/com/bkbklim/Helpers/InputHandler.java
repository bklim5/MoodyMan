package com.bkbklim.Helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.bkbklim.GameObjects.Man;
import com.bkbklim.Logic.GameWorld;
import com.bkbklim.UserInterface.SimpleButton;

import static com.bkbklim.Helpers.Constants.GAME_HEIGHT;

/**
 * Created by boonkhailim on 14/9/17.
 */

public class InputHandler implements InputProcessor {
    private Man man;
    private GameWorld myWorld;

    private SimpleButton playButton, mainMenuButon, retryButton;

    private float scaleFactorX;
    private float scaleFactorY;

    public InputHandler(GameWorld myWorld, float scaleFactorX, float scaleFactorY) {
        this.myWorld = myWorld;
        man = myWorld.getMan();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        playButton = new SimpleButton(myWorld.getMidPointX() - (AssetLoader.playButtonUp.getRegionWidth() / 2),
                GAME_HEIGHT / 2 + 50, 29, 16, AssetLoader.playButtonUp, AssetLoader.playButtonDown);
        retryButton = new SimpleButton(myWorld.getMidPointX() + myWorld.getMidPointX() / 2- (AssetLoader.retryButtonUp.getRegionWidth() / 2),
                GAME_HEIGHT / 2 + 30, 31, 16, AssetLoader.retryButtonUp, AssetLoader.retryButtonDown);
        mainMenuButon = new SimpleButton(myWorld.getMidPointX() / 2 - (AssetLoader.mainMenuButtonUp.getRegionWidth() / 2),
                GAME_HEIGHT / 2 + 30, 43, 16, AssetLoader.mainMenuButtonUp, AssetLoader.mainMenuButtonDown);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
        } else if (myWorld.isReady() && !myWorld.getMan().isReadyFloatingUp()) {
            myWorld.start();
        } else if (myWorld.isRunning()) {
            man.onClick();
        }

        if (myWorld.isGameOver() || myWorld.isHighScore()) {
            retryButton.isTouchDown(screenX, screenY);
            mainMenuButon.isTouchDown(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            if (playButton.isTouchUp(screenX, screenY)) {
                myWorld.ready();
                return true;
            }
        } else if (myWorld.isHighScore() || myWorld.isGameOver()) {
            if (mainMenuButon.isTouchUp(screenX, screenY)) {
                myWorld.menu();
                return true;

            } else if (retryButton.isTouchUp(screenX, screenY)) {
                myWorld.ready();
                return true;

            }
        }
        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        // Can now use Space Bar to play the game
        if (keycode == Input.Keys.SPACE) {

            if (myWorld.isMenu()) {
                myWorld.ready();
            } else if (myWorld.isReady()) {
                myWorld.start();
            }

            man.onClick();

            if (myWorld.isGameOver() || myWorld.isHighScore()) {
                myWorld.restart();
            }

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public SimpleButton getPlayButton() {
        return playButton;
    }

    public SimpleButton getMainMenuButon() {
        return mainMenuButon;
    }

    public SimpleButton getRetryButton() {
        return retryButton;
    }
}
