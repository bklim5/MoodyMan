package com.bkbklim.TweenAccessor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by boonkhailim on 14/9/17.
 */

public class SpriteAccessor implements TweenAccessor<Sprite> {
    public static final int ALPHA = 1;

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;

            default:
                return 0;

        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case (ALPHA):
                target.setAlpha(newValues[0]);
                Color c = target.getColor();
                c.a = newValues[0];
                target.setColor(c);

                break;

        }
    }
}
