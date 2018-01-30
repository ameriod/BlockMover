package com.nordeck.blockmover;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by parker on 5/8/15.
 */
public class GameRenderer {
    public static final String TAG = "GameRenderer";

    private GameWorld gameWorld;
    private Camera camera;
    private boolean isPlayerSet;

    public GameRenderer(GameWorld gameWorld, Camera camera) {
        this.gameWorld = gameWorld;
        this.camera = camera;
        this.isPlayerSet = false;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.isPlayerSet = false;
    }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, float runTime) {
        // update the game world
        gameWorld.update(runTime);

        // make the camera follow the player
        Vector3 cameraPosition = camera.position;
        if (isPlayerSet) {
            float lerp = 0.1f;
            // slow the camera update and smooth it so it lags a little
            cameraPosition.x += (gameWorld.getPlayer().getX() - cameraPosition.x) * lerp;
            cameraPosition.y += (gameWorld.getPlayer().getY() - cameraPosition.y) * lerp;
        } else {
            // set the initial camera to center on the player, do this before rendering the player / board to prevent
            // a visual jump.
            cameraPosition.x = gameWorld.getPlayer().getX();
            cameraPosition.y = gameWorld.getPlayer().getY();
            isPlayerSet = true;
        }
        camera.update();

        // update the camera so it fits inside the VeiwPort / stage
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        // the runTime is needed for animations
        gameWorld.getBoard().render(spriteBatch, runTime);
        gameWorld.getPlayer().draw(spriteBatch, runTime);
        spriteBatch.end();
        // debug drawing
//        if (Ludum32Game.IS_DEBUG && shapeRenderer != null) {
//            shapeRenderer.setProjectionMatrix(camera.combined);
//            gameWorld.getBoard().drawDebug(shapeRenderer);
//            gameWorld.getPlayer().drawDebug(shapeRenderer);
//        }
    }
}
