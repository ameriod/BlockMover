package com.nordeck.blockmover.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.nordeck.blockmover.Ludum32Game;
import com.nordeck.blockmover.state.MoveTrack;

/**
 * Created by parker on 5/9/15.
 */
public class Box extends BaseMapObject {
    public static final String TAG = "Box";

    private Texture boxTexture;
    private Texture boxTextureDark;
    private Texture currentTexture;
    private boolean isOnEndPoint;

    private float oldX, oldY;

    private static final int SPEED = Ludum32Game.CELL_SIZE / 4;

    private int deltaPosition;

    private MoveState currentMoveState;

    private Array<String> moveTrack;
    // keep track of the position of the box move with the player moves
    private int playerMovePosition;

    public Box(float x, float y, Texture boxTexture, Texture boxTextureDark) {
        super(x, y, true);
        this.currentTexture = boxTexture;
        this.boxTexture = boxTexture;
        this.boxTextureDark = boxTextureDark;
        this.moveTrack = new Array<String>();
    }

    @Override
    public void actOnPlayer(Player player, Rectangle intersection) {
        // no impl
    }

    @Override
    public void actOnMapObject(BaseMapObject interactingMapObject, Rectangle intersection) {
        // no impl
    }

    @Override
    public void update(float delta) {
        // The box can move so keep track of the positions
        oldX = getX();
        oldY = getY();
        if (currentMoveState == MoveState.UP) {
            setPosition(getX(), getY() + SPEED);
            incrementDeltaPosition();
        } else if (currentMoveState == MoveState.DOWN) {
            setPosition(getX(), getY() - SPEED);
            incrementDeltaPosition();
        } else if (currentMoveState == MoveState.RIGHT) {
            setPosition(getX() + SPEED, getY());
            incrementDeltaPosition();
        } else if (currentMoveState == MoveState.LEFT) {
            setPosition(getX() - SPEED, getY());
            incrementDeltaPosition();
        } else {
            resetBoxMove();
        }
        if (deltaPosition >= Ludum32Game.CELL_SIZE) {
            moveTrack.add(MoveTrack.getBoxMove(currentMoveState, playerMovePosition));
            resetBoxMove();
        }
    }

    private void incrementDeltaPosition() {
        deltaPosition = deltaPosition + SPEED;
    }

    private void resetBoxMove() {
        deltaPosition = 0;
        currentMoveState = MoveState.IDLE;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float runTime) {
        spriteBatch.draw(currentTexture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void initAssets() {
        // no impl
    }

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public void moveBox(Player player) {
        // the player has not completed the move so, but since the list size is zero indexed we do not need to add 1
        // to get the right player move track position
        playerMovePosition = player.getMoveTrack().size;
        if (player.isMovingUp()) {
            currentMoveState = MoveState.UP;
        } else if (player.isMovingDown()) {
            currentMoveState = MoveState.DOWN;
        } else if (player.isMovingRight()) {
            currentMoveState = MoveState.RIGHT;
        } else if (player.isMovingLeft()) {
            currentMoveState = MoveState.LEFT;
        }
    }

    public void intersectWall() {
        setPosition(getOldX(), getOldY());
        resetBoxMove();
    }

    public void intersectBox() {
        setPosition(getOldX(), getOldY());
    }

    public boolean isMoving() {
        return currentMoveState != MoveState.IDLE;
    }

    public Array<String> getMoveTrack() {
        return moveTrack;
    }

    public void setIsOnEndPoint(boolean on) {
        isOnEndPoint = on;
        if (on) {
            currentTexture = boxTextureDark;
        } else {
            currentTexture = boxTexture;
        }
    }

    public boolean isOnEndPoint() {
        return isOnEndPoint;
    }

    public void restoreState(Array<String> moveTrack) {
        this.moveTrack = moveTrack;
        // need to convert the box move track to a regular one then input it
        MoveTrack.restoreMoveTrack(MoveTrack.convertBoxMoveTrack(moveTrack), this);
    }
}
