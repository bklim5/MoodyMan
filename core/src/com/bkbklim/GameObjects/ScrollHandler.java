package com.bkbklim.GameObjects;

import com.bkbklim.Helpers.AssetLoader;
import com.bkbklim.Logic.GameWorld;

import static com.bkbklim.Helpers.Constants.GAP_BETWEEN_DIFF_OBSTACLES;
import static com.bkbklim.Helpers.Constants.SCROLL_SPEED;
import static com.bkbklim.Helpers.Constants.firstObstacleY;

/**
 * Created by boonkhailim on 16/9/17.
 */

public class ScrollHandler {
    private ObstacleScrollable obstacle1, obstacle2, obstacle3;
    private GameWorld gameWorld;
    private int gap = GAP_BETWEEN_DIFF_OBSTACLES;

    public ScrollHandler(GameWorld gameWorld, float wallRightX) {
        this.gameWorld = gameWorld;
        obstacle1 = new ObstacleScrollable(0, firstObstacleY, 50, 25, SCROLL_SPEED, wallRightX);
        obstacle2 = new ObstacleScrollable(0, obstacle1.getTailY() - GAP_BETWEEN_DIFF_OBSTACLES, 50, 25, SCROLL_SPEED, wallRightX);
        obstacle3 = new ObstacleScrollable(0, obstacle2.getTailY() - GAP_BETWEEN_DIFF_OBSTACLES, 50, 25, SCROLL_SPEED, wallRightX);

    }

    public void updateReady(float delta) {

    }

    public void update(float delta) {
        obstacle1.update(delta);
        obstacle2.update(delta);
        obstacle3.update(delta);

        if (obstacle1.isScrolledBottom()) {
            obstacle1.reset(obstacle3.getTailY() - gap);
        } else if (obstacle2.isScrolledBottom()) {
            obstacle2.reset(obstacle1.getTailY() - gap);
        } else if (obstacle3.isScrolledBottom()) {
            obstacle3.reset(obstacle2.getTailY() - gap);
        }
    }

    public void stop() {
        obstacle1.stop();
        obstacle2.stop();
        obstacle3.stop();
    }

    public boolean collides(Man man) {
        if (!obstacle1.isScored() && (obstacle1.getY() + obstacle1.getHeight() / 2 > man.getY() + man.getHeight())) {
            addScore(1);
            obstacle1.setScored(true);
            AssetLoader.coin.play();
        } else if (!obstacle2.isScored() && (obstacle2.getY() + obstacle2.getHeight() / 2 > man.getY() + man.getHeight())) {
            addScore(1);
            obstacle2.setScored(true);
            AssetLoader.coin.play();
        } else if (!obstacle3.isScored() && (obstacle3.getY() + obstacle3.getHeight() / 2 > man.getY() + man.getHeight())) {
            addScore(1);
            obstacle3.setScored(true);
            AssetLoader.coin.play();
        }
        return (obstacle1.collides(man) || obstacle2.collides(man) || obstacle3.collides(man));
    }

    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }

    public void onRestart() {
        obstacle1.onRestart(firstObstacleY, SCROLL_SPEED);
        obstacle2.onRestart(obstacle1.getTailY() - GAP_BETWEEN_DIFF_OBSTACLES, SCROLL_SPEED);
        obstacle3.onRestart(obstacle2.getTailY() - GAP_BETWEEN_DIFF_OBSTACLES, SCROLL_SPEED);
    }

    public void decreaseGap() {
        gap -= 10;
        gap = (gap < 90)?90:gap;
    }

    public void accelerate() {
        obstacle1.accelerate();
        obstacle2.accelerate();
        obstacle3.accelerate();
    }



    public ObstacleScrollable getObstacle1() {
        return obstacle1;
    }

    public ObstacleScrollable getObstacle2() {
        return obstacle2;
    }

    public ObstacleScrollable getObstacle3() {
        return obstacle3;
    }

}
