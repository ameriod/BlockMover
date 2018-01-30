package com.nordeck.blockmover.state;

import com.badlogic.gdx.utils.Array;
import com.nordeck.blockmover.Ludum32Game;
import com.nordeck.blockmover.object.BaseGameObject;
import com.nordeck.blockmover.object.MoveState;

import java.util.regex.Pattern;

/**
 * Created by parker on 5/13/15.
 */
public class MoveTrack {
    public static final String UP = "u";
    public static final String DOWN = "d";
    public static final String LEFT = "l";
    public static final String RIGHT = "r";

    public static final String UP_MOVED = "U";
    public static final String DOWN_MOVED = "D";
    public static final String LEFT_MOVED = "L";
    public static final String RIGHT_MOVED = "R";

    public static final String PLAYER_POSITION_DELIMITER = "|";

    public static String getMove(MoveState moveState, boolean movedBox) {
        if (moveState == MoveState.DOWN) {
            if (movedBox) {
                return DOWN_MOVED;
            }
            return DOWN;
        } else if (moveState == MoveState.UP) {
            if (movedBox) {
                return UP_MOVED;
            }
            return UP;
        } else if (moveState == MoveState.LEFT) {
            if (movedBox) {
                return LEFT_MOVED;
            }
            return LEFT;
        } else if (moveState == MoveState.RIGHT) {
            if (movedBox) {
                return RIGHT_MOVED;
            }
            return RIGHT;
        }
        return "";
    }

    /**
     * Example 2|D
     *
     * @param moveState
     * @param playerMovePosition
     * @return
     */
    public static String getBoxMove(MoveState moveState, int playerMovePosition) {
        return playerMovePosition + PLAYER_POSITION_DELIMITER + getMove(moveState, true);
    }

    public static final int BOX_TRACK_PLAYER_POS = 0;
    public static final int BOX_TRACK_MOVE = 1;

    /**
     * @param boxMoveTrack
     * @return 0 = player position, 1 = the move
     */
    public static String[] parseBoxMove(String boxMoveTrack) {
        if (!boxMoveTrack.contains(PLAYER_POSITION_DELIMITER)) {
            throw new IllegalStateException("ERROR boxMoveTrack: " + boxMoveTrack + " need a " +
                    PLAYER_POSITION_DELIMITER);
        }
        return boxMoveTrack.split(Pattern.quote(PLAYER_POSITION_DELIMITER));
    }

    /**
     * Set the reverse of the move track in where the box / player needs to move
     *
     * @param move
     * @return
     */
    public static int getReverseMove(String move) {
        if (isUp(move)) {
            return -Ludum32Game.CELL_SIZE;
        } else if (isDown(move)) {
            return Ludum32Game.CELL_SIZE;
        } else if (isLeft(move)) {
            return Ludum32Game.CELL_SIZE;
        } else if (isRight(move)) {
            return -Ludum32Game.CELL_SIZE;
        }
        return 0;
    }

    private static boolean isUp(String move) {
        return move.equals(UP) || move.equals(UP_MOVED);
    }

    private static boolean isDown(String move) {
        return move.equals(DOWN) || move.equals(DOWN_MOVED);
    }

    private static boolean isLeft(String move) {
        return move.equals(LEFT) || move.equals(LEFT_MOVED);
    }

    private static boolean isRight(String move) {
        return move.equals(RIGHT) || move.equals(RIGHT_MOVED);
    }

    public static boolean isXMovement(String move) {
        return isLeft(move) || isRight(move);
    }

    public static boolean isYMovement(String move) {
        return !isXMovement(move);
    }

    public static Array<String> convertBoxMoveTrack(Array<String> boxMoveTrack) {
        Array<String> moveTrack = new Array<String>();
        for (int i = 0, size = boxMoveTrack.size; i < size; i++) {
            String[] boxMove = parseBoxMove(boxMoveTrack.get(i));
            moveTrack.add(boxMove[BOX_TRACK_MOVE]);
        }
        return moveTrack;
    }

    /**
     * Does not take a box move track only a players use {@link #convertBoxMoveTrack(Array)} first
     *
     * @param moveTrack
     * @param gameObject
     */
    public static void restoreMoveTrack(Array<String> moveTrack, BaseGameObject gameObject) {
        for (int i = 0, size = moveTrack.size; i < size; i++) {
            String move = moveTrack.get(i);
            // reverse the reverse move
            int forwardMove = -getReverseMove(move);
            if (isXMovement(move)) {
                gameObject.setX(gameObject.getX() + forwardMove);
            } else {
                gameObject.setY(gameObject.getY() + forwardMove);
            }
        }
    }

}
