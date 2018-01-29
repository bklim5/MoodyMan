package com.bkbklim.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.bkbklim.Helpers.AssetLoader;

import static com.bkbklim.Helpers.Constants.GAME_HEIGHT;

/**
 * Created by boonkhailim on 14/9/17.
 */

public class Man {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private Circle boundingCircle;

    private float rotation;
    private int height;
    private int width;
    private float originalY, originalX, readyStartY;
    private boolean facingRight;
    private boolean isAlive;
    private boolean isReadyFloatingUp;
    private static final int initialAcceleration = 200;
    private static final int accelerateSpeed = 20;

    public Man(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        originalY = y;
        originalX = x;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(initialAcceleration, 0);
        facingRight = true;
        rotation = 0;
        boundingCircle = new Circle();
        isAlive = true;
        isReadyFloatingUp = true;
        readyStartY = 210;
    }

    public void updateMenu(float runTime) {
        position.y = 2 * (float) Math.sin(5 * runTime) + GAME_HEIGHT / 2;
        position.x = 3 * (float) Math.sin(10 * runTime) + originalX;
    }

    public void updateReady(float runTime) {
        if (isReadyFloatingUp) {
            position.y = readyStartY;
            readyStartY -= 3;
            if (position.y <= originalY) {
                isReadyFloatingUp = false;
            }
        } else {
            position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
        }

        position.x = 2 * (float) Math.sin(3 * runTime) + originalX;

    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        if (velocity.x > 200) {
            velocity.x = 200;
        }

        if (velocity.x < -200) {
            velocity.x = -200;
        }

        boundingCircle.set(position.x + width / 2, position.y + height / 2, width / 2 - 5);

        position.add(velocity.cpy().scl(delta));
    }

    public boolean hitWall(float rightWallX) {
        return (position.x + 10 <= 0 || position.x + width - 10 >= rightWallX);
    }

    public void onClick() {
        if (isAlive) {
            AssetLoader.swoosh.play();
            facingRight = !facingRight;
            rotation = (facingRight)?10:-10;
            acceleration.set(acceleration.x * -1, 0);
            velocity.set(velocity.x * -1 / 10, 0);
        }
    }

    public void die() {
        isAlive = false;
        velocity.x = 0;
        velocity.y = 30;
        decelerate();
    }

    public boolean isFullyDead() {
        return position.y >= GAME_HEIGHT;
    }

    public void accelerate() {
        acceleration.x += facingRight?accelerateSpeed:accelerateSpeed*-1;
    }


    public void decelerate() {
        acceleration.x = 0;
    }

    public void onRestart(int x, int y) {
        rotation = 0;
        position.x = x - width / 2;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = initialAcceleration;
        acceleration.y = 0;
        isAlive = true;
        facingRight = true;
        isReadyFloatingUp = true;
        readyStartY = 210;
    }



    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public boolean isReadyFloatingUp() {
        return isReadyFloatingUp;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
