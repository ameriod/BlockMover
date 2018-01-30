package com.nordeck.blockmover.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.nordeck.blockmover.AssetLoader;
import com.nordeck.blockmover.Ludum32Game;
import com.nordeck.blockmover.state.MoveTrack;

/**
 * TODO clean up the texture to texture region shit
 * <p/>
 * Created by parker on 2/21/15.
 */
public class Player extends BaseGameObject {
    public static final String TAG = "Player";

    public static final int HEIGHT = 60;
    public static final int WIDTH = 42;

    public TextureRegion currentTexture;
    public Texture character1, character2, character3, character4, character5, character6, character7,
            character8, character9, character10;

    private Animation leftAnimation, rightAnimation, upAnimation, downAnimation, idleAnimation;

    private MoveState currentMoveState;

    private static final int SPEED = Ludum32Game.CELL_SIZE / 8;

    private int deltaPosition;

    private float oldX, oldY;

    private static final float MOVE_ANIMATION_DURATION = 0.3f;

    private int boxCollisionCount;

    private Array<String> moveTrack;

    public Player(float x, float y) {
        super(x, y);
        // set the initial size on the idle state
        currentMoveState = MoveState.IDLE;
        currentTexture = new TextureRegion(character4);
        setSize(WIDTH, HEIGHT);
        initBoundingBox();
        boxCollisionCount = 0;
        moveTrack = new Array<String>();
    }

    @Override
    public void update(float delta) {
        oldX = getX();
        oldY = getY();

        if (currentMoveState == MoveState.LEFT) {
            currentTexture = leftAnimation.getKeyFrame(delta, true);
            setX(getX() - SPEED);
            incrementDeltaPosition();
        } else if (currentMoveState == MoveState.RIGHT) {
            currentTexture = rightAnimation.getKeyFrame(delta, true);
            setX(getX() + SPEED);
            incrementDeltaPosition();
        } else if (currentMoveState == MoveState.DOWN) {
            setY(getY() - SPEED);
            currentTexture = downAnimation.getKeyFrame(delta, true);
            incrementDeltaPosition();
        } else if (currentMoveState == MoveState.UP) {
            setY(getY() + SPEED);
            currentTexture = upAnimation.getKeyFrame(delta, true);
            incrementDeltaPosition();
        } else {
            currentTexture = idleAnimation.getKeyFrame(delta);
            resetDeltaMove();
        }
        if (boxCollisionCount > 1) {
            // the box cannot move since the player bumped it move than once (the box moves faster than the player)
            resetMove();
            boxCollisionCount = 0;
        }
        if (deltaPosition >= Ludum32Game.CELL_SIZE) {
            // track the players movements
            moveTrack.add(MoveTrack.getMove(currentMoveState, boxCollisionCount > 0));
            resetDeltaMove();
            boxCollisionCount = 0;
        }
    }

    private void resetDeltaMove() {
        deltaPosition = 0;
        moveIdle();
    }

    private void incrementDeltaPosition() {
        deltaPosition = deltaPosition + SPEED;
    }

    public void moveLeft() {
        currentMoveState = MoveState.LEFT;
    }

    public void moveRight() {
        currentMoveState = MoveState.RIGHT;
    }

    public void moveUp() {
        currentMoveState = MoveState.UP;
    }

    public void moveDown() {
        currentMoveState = MoveState.DOWN;
    }

    public void moveIdle() {
        currentMoveState = MoveState.IDLE;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float runTime) {
        // center the textures inside the static collision box
        spriteBatch.draw(currentTexture,
                getX() + ((getWidth() - currentTexture.getRegionWidth()) / 2f),
                getY() + ((getHeight() - currentTexture.getRegionHeight()) / 2f),
                currentTexture.getRegionWidth(),
                currentTexture.getRegionHeight());
    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
        drawDebugShape(shapeRenderer, Color.GREEN);
    }

    @Override
    public void initAssets() {
        character1 = AssetLoader.character1;
        character2 = AssetLoader.character2;
        character3 = AssetLoader.character3;
        character4 = AssetLoader.character4;
        character5 = AssetLoader.character5;
        character6 = AssetLoader.character6;
        character7 = AssetLoader.character7;
        character8 = AssetLoader.character8;
        character9 = AssetLoader.character9;
        character10 = AssetLoader.character10;

        Array<TextureRegion> idleArray = new Array<TextureRegion>();
        idleArray.addAll(new TextureRegion(character4));
        idleAnimation = new Animation(MOVE_ANIMATION_DURATION, idleArray);

        Array<TextureRegion> leftArray = new Array<TextureRegion>();
        leftArray.addAll(new TextureRegion(character1), new TextureRegion(character10));
        leftAnimation = new Animation(MOVE_ANIMATION_DURATION, leftArray);

        Array<TextureRegion> rightArray = new Array<TextureRegion>();
        rightArray.addAll(new TextureRegion(character2), new TextureRegion(character3));
        rightAnimation = new Animation(MOVE_ANIMATION_DURATION, rightArray);

        Array<TextureRegion> downArray = new Array<TextureRegion>();
        downArray.addAll(new TextureRegion(character4), new TextureRegion(character5), new TextureRegion(character6));
        downAnimation = new Animation(MOVE_ANIMATION_DURATION, downArray);

        Array<TextureRegion> upArray = new Array<TextureRegion>();
        upArray.addAll(new TextureRegion(character7), new TextureRegion(character8), new TextureRegion(character9));
        upAnimation = new Animation(MOVE_ANIMATION_DURATION, upArray);
    }

    public void intersectBox() {
        // increment to keep tack of the box movements (move than one reset the player since to box cannot move)
        boxCollisionCount++;
        MoveState savedState = currentMoveState;
        // bump from the box to let it move
        resetMove();
        // reset the move state
        currentMoveState = savedState;
    }

    public void intersectWall() {
        resetMove();
    }

    private void resetMove() {
        if (currentMoveState == MoveState.LEFT) {
            setPosition(getX() + deltaPosition, getY());
        } else if (currentMoveState == MoveState.RIGHT) {
            setPosition(getX() - deltaPosition, getY());
        } else if (currentMoveState == MoveState.DOWN) {
            setPosition(getX(), getY() + deltaPosition);
        } else if (currentMoveState == MoveState.UP) {
            setPosition(getX(), getY() - deltaPosition);
        }
        resetDeltaMove();
    }

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public boolean isMovingRight() {
        return currentMoveState.equals(MoveState.RIGHT);
    }

    public boolean isMovingLeft() {
        return currentMoveState.equals(MoveState.LEFT);
    }

    public boolean isMovingUp() {
        return currentMoveState.equals(MoveState.UP);
    }

    public boolean isMovingDown() {
        return currentMoveState.equals(MoveState.DOWN);
    }

    public boolean isIdle() {
        return currentMoveState.equals(MoveState.IDLE);
    }

    public Array<String> getMoveTrack() {
        return moveTrack;
    }

    public void restoreState(Array<String> moveTrack) {
        this.moveTrack = moveTrack;
        MoveTrack.restoreMoveTrack(moveTrack, this);
    }
}