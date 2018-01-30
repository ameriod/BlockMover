package com.nordeck.blockmover;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nordeck.blockmover.object.Player;

/**
 * Created by parker on 5/15/15.
 */
public class GameInputProcessor implements InputProcessor, GestureDetector.GestureListener {
    public static final String TAG = "GameInputProcessor";

    private static final int MAX_FLING = 300;

    public static final float ZOOM_IN_MAX = 1.0f;
    public static final float ZOOM_OUT_MAX = 3.0f;
    public static final float ZOOM_DEFAULT = 1.0f;
    public static final float ZOOM_INCREMENT = 0.1f;

    private GameWorld gameWorld;
    private OrthographicCamera gameCamera;
    private Player player;
    private GestureDetector gestureDetector;

    public GameInputProcessor(GameWorld gameWorld, OrthographicCamera gamCamera, boolean useGestures) {
        setGameWorld(gameWorld);
        this.gameCamera = gamCamera;
        if (useGestures) {
            this.gestureDetector = new GestureDetector(this);
        }
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.player = gameWorld.getPlayer();
    }

    public void zoomOut() {
        gameCamera.zoom = gameCamera.zoom + ZOOM_INCREMENT;
        if (gameCamera.zoom > ZOOM_OUT_MAX) {
            gameCamera.zoom = ZOOM_OUT_MAX;
        }
        gameCamera.update();
    }

    public void zoomIn() {
        gameCamera.zoom = gameCamera.zoom - ZOOM_INCREMENT;
        if (gameCamera.zoom < ZOOM_IN_MAX) {
            gameCamera.zoom = ZOOM_DEFAULT;
        }
        gameCamera.update();
    }

    // Input Processor

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT && player.isIdle()) {
            player.moveLeft();
            return true;
        } else if (keycode == Input.Keys.RIGHT && player.isIdle()) {
            player.moveRight();
            return true;
        } else if (keycode == Input.Keys.UP && player.isIdle()) {
            player.moveUp();
            return true;
        } else if (keycode == Input.Keys.DOWN && player.isIdle()) {
            player.moveDown();
            return true;
        } else if (keycode == Input.Keys.U) {
            gameWorld.undoMove();
            return true;
        } else if (keycode == Input.Keys.PLUS || keycode == Input.Keys.EQUALS) {
            zoomIn();
            return true;
        } else if (keycode == Input.Keys.MINUS) {
            zoomOut();
            return true;
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (gestureDetector != null) {
            return gestureDetector.touchDown(screenX, screenY, pointer, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (gestureDetector != null) {
            return gestureDetector.touchUp(screenX, screenY, pointer, pointer);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (gestureDetector != null) {
            return gestureDetector.touchDragged(screenX, screenY, pointer);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (gestureDetector != null) {
            return gestureDetector.mouseMoved(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (gestureDetector != null) {
            return gestureDetector.scrolled(amount);
        }
        return false;
    }

    // Gesture Listener / Detector

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    // only let a 15 degree arc around up, left, right, down
    private static final float FLING_OFFSET = 15f;
    // For some reason up can be neg or positive
    private static final float FLING_UP_MAX = 180f + FLING_OFFSET;
    private static final float FLING_UP_MIN = 180f - FLING_OFFSET;
    private static final float FLING_UP_MAX_NEG = -180f + FLING_OFFSET;
    private static final float FLING_UP_MIN_NEG = -180f - FLING_OFFSET;
    private static final float FLING_LEFT_MAX = -90f + FLING_OFFSET;
    private static final float FLING_LEFT_MIN = -90f - FLING_OFFSET;
    private static final float FLING_RIGHT_MAX = 90f + FLING_OFFSET;
    private static final float FLING_RIGHT_MIN = 90f - FLING_OFFSET;
    private static final float FLING_DOWN_MAX = 0 + FLING_OFFSET;
    private static final float FLING_DOWN_MIN = 0 - FLING_OFFSET;

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (!player.isIdle()) {
            // Do not move unless the player is idle
            return false;
        }
        float velocityXAbs = Math.abs(velocityX);
        float velocityYAbs = Math.abs(velocityY);
        // make sure the fling is valid
        if (velocityXAbs >= MAX_FLING || velocityYAbs >= MAX_FLING) {
            // need to convert the radians to degrees
            float angle = MathUtils.atan2(velocityX, velocityY) * MathUtils.radiansToDegrees;
            // Check the directions
            if (velocityXAbs > velocityYAbs) {
                if (velocityX > 0 && Utils.isBetweenOrEquals(FLING_RIGHT_MAX, FLING_RIGHT_MIN, angle)) {
                    // Right
                    player.moveRight();
                    return true;
                } else if (Utils.isBetweenOrEquals(FLING_LEFT_MAX, FLING_LEFT_MIN, angle)) {
                    // Left
                    player.moveLeft();
                    return true;
                }
            } else {
                if (velocityY > 0 && Utils.isBetweenOrEquals(FLING_DOWN_MAX, FLING_DOWN_MIN, angle)) {
                    // Down
                    player.moveDown();
                    return true;
                } else if (Utils.isBetweenOrEquals(FLING_UP_MAX, FLING_UP_MIN, angle) ||
                        Utils.isBetweenOrEquals(FLING_UP_MAX_NEG, FLING_UP_MIN_NEG, angle)) {
                    // Up (can be 180 or -180 wtf)
                    player.moveUp();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

}
