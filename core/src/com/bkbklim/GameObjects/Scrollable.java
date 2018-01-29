package com.bkbklim.GameObjects;

import com.badlogic.gdx.math.Vector2;

import static com.bkbklim.Helpers.Constants.GAME_HEIGHT;

/**
 * Created by boonkhailim on 15/9/17.
 */

public class Scrollable {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int height;
    protected int width;
    protected boolean isScrolledBottom;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, scrollSpeed);
        this.width = width;
        this.height = height;
        isScrolledBottom = false;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        // if the scrollable object is no longer visible
        if (position.y + height > GAME_HEIGHT) {
            isScrolledBottom = true;
        }
    }

    public void reset(float newY) {
        position.y = newY;
        isScrolledBottom = false;
    }

    public void stop() {
        velocity.y = 0;
    }

    public boolean isScrolledBottom() {
        return isScrolledBottom;
    }

    public float getTailY() {
        return position.y;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}
