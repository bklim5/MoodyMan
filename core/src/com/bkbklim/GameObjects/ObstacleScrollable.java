package com.bkbklim.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.bkbklim.Helpers.AssetLoader;

import java.util.Random;

import static com.bkbklim.Helpers.Constants.GAP_BETWEEN_ONE_SET_OF_OBSTACLES;

/**
 * Created by boonkhailim on 16/9/17.
 */

public class ObstacleScrollable extends Scrollable {
    private Random r;
    private Rectangle obstacleLeft, obstacleRight;
    private float wallRightX;
    private boolean isScored = false;
    private TextureRegion tr;
    private static final int trWidth = 25;
    private static final int trHeight = 25;

    public ObstacleScrollable(float x, float y, int width, int height, float scrollSpeed, float wallRightX) {
        super(x, y, width, height, scrollSpeed);
        r = new Random();
        this.width = r.nextInt(3) * trWidth + trWidth;
        obstacleLeft = new Rectangle();
        obstacleRight = new Rectangle();
        this.wallRightX = wallRightX;
        tr = getRandomTr();
    }

    @Override
    public void reset(float newY) {
        super.reset(newY);
        width = r.nextInt(3) * trWidth + trWidth;
        isScored = false;
        tr = getRandomTr();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        obstacleLeft.set(position.x, position.y, width, height);
        obstacleRight.set(position.x + width + GAP_BETWEEN_ONE_SET_OF_OBSTACLES, position.y,
                wallRightX - (position.x + width + GAP_BETWEEN_ONE_SET_OF_OBSTACLES), height);
    }

    public boolean collides(Man man) {
        return (position.y + height > man.getY()) && (Intersector.overlaps(man.getBoundingCircle(), obstacleLeft) || Intersector.overlaps(man.getBoundingCircle(), obstacleRight));
    }

    private TextureRegion getRandomTr() {
        switch (r.nextInt(3)) {
            case 0:
                return AssetLoader.wifeTR;
            case 1:
                return AssetLoader.workTR;
            case 2:
                return AssetLoader.moneyTR;
            default:
                return AssetLoader.wifeTR;
        }
    }

    public void onRestart(float y, float scrollSpeed) {
        velocity.y = scrollSpeed;
        reset(y);

    }

    public void accelerate() {
        velocity.y += 10;
    }

    public void draw(SpriteBatch batcher) {
        float startDrawX = obstacleLeft.x;

        while (startDrawX < obstacleLeft.x + obstacleLeft.getWidth()) {
            batcher.draw(tr, startDrawX, position.y, trWidth, trHeight);
            startDrawX += trWidth;
        }

        startDrawX = obstacleRight.x;
        while (startDrawX < obstacleRight.x + obstacleRight.getWidth()) {
            batcher.draw(tr, startDrawX, position.y, trWidth, trHeight);
            startDrawX += trWidth;
        }
    }

    public Rectangle getObstacleLeft() {
        return obstacleLeft;
    }

    public Rectangle getObstacleRight() {
        return obstacleRight;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean scored) {
        isScored = scored;
    }

    public TextureRegion getTr() {
        return tr;
    }


}
