package com.nordeck.blockmover;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.nordeck.blockmover.object.*;
import com.nordeck.blockmover.object.ui.Level;
import com.nordeck.blockmover.state.LevelState;
import com.nordeck.blockmover.state.MoveTrack;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashSet;

/**
 * Created by parker on 5/8/15.
 */
public class GameWorld {
    public static final String TAG = "GameWorld";

    private Player player;
    private Board board;
    private boolean isBoardOver;
    private LevelState levelState;
    private Level level;

    private float startZoomState;

    public GameWorld(Level level, LevelState levelState) {
        this.level = level;
        this.levelState = levelState;
        this.board = level.getBoard();
        board.centerBoard(board.getBoardWidth(), getBoard().getBoardHeight());
        // center the player inside the 64x64 tile
        this.player = new Player(board.getPlayerStartX() + (Ludum32Game.CELL_SIZE - Player.WIDTH) / 2,
                board.getPlayerStartY() + (Ludum32Game.CELL_SIZE - Player.HEIGHT) / 2);

        if (levelState != null && levelState.isInProgress()) {
            board.restoreBoxStates(levelState.getBoxTracks());
            player.restoreState(levelState.getPlayerTrack());
        }
        // find the best zoom state
        double zoomState = (double) board.getBoardHeight() / (double) Ludum32Game.GAME_HEIGHT;
        if (zoomState < GameInputProcessor.ZOOM_DEFAULT) {
            zoomState = GameInputProcessor.ZOOM_DEFAULT;
        } else {
            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);
            zoomState = Double.parseDouble(df.format(zoomState));
        }
        startZoomState = (float) zoomState;
        Utils.log(TAG, "zoomState: " + zoomState + " startZoomState: " + startZoomState);
    }

    public float getStartZoomState() {
        return startZoomState;
    }

    public void update(float delta) {
        int goalCount = 0;
        player.update(delta);
        board.update(delta);

        // Intersections
        // wall on player / box
        for (int i = 0, size = board.getBaseLayer().size; i < size; i++) {
            BaseMapObject mapObject = board.getBaseLayer().get(i);
            if (mapObject.isCollidable() && mapObject instanceof Wall) {
                if (player.hasIntersection(mapObject) != null) {
                    player.intersectWall();
                }
                boxWallCollision((Wall) mapObject);
            }
        }

        // Prevent box overlap
        for (int i = 0, size = board.getBoxLayer().size; i < size; i++) {
            Box box = board.getBoxLayer().get(i);
            boxBoxCollision(box);
        }

        // player on box (do last to prevent the jittery play box reaction)
        for (int i = 0, size = board.getBoxLayer().size; i < size; i++) {
            Box box = board.getBoxLayer().get(i);
            // make the player move the box, the player slows down
            if (box.isCollidable() && player.hasIntersection(box) != null) {
                box.moveBox(player);
                player.intersectBox();
                break;
            }
        }

        HashSet<Box> completedBoxes = new HashSet<Box>();
        for (int i = 0, size = board.getGoalLayer().size; i < size; i++) {
            EndPoint endPoint = board.getGoalLayer().get(i);
            Box box = boxEndPointCollision(endPoint);
            if (box != null) {
                goalCount++;
                completedBoxes.add(box);
            }
        }

        // Toggle the box states
        for (int i = 0, size = board.getBoxLayer().size; i < size; i++) {
            Box boardBox = board.getBoxLayer().get(i);
            boardBox.setIsOnEndPoint(completedBoxes.contains(boardBox));
        }

        isBoardOver = goalCount == board.getGoalCount();
    }

    private Box boxEndPointCollision(EndPoint endPoint) {
        for (int i = 0, size = board.getBoxLayer().size; i < size; i++) {
            Box box = board.getBoxLayer().get(i);
            Rectangle intersection = box.hasIntersection(endPoint);
            if (intersection != null && intersection.getHeight() == Ludum32Game.CELL_SIZE
                    && intersection.getWidth() == Ludum32Game.CELL_SIZE) {
                return box;
            }
        }
        return null;
    }

    private void boxBoxCollision(Box currentBox) {
        for (int i = 0, size = board.getBoxLayer().size; i < size; i++) {
            Box box = board.getBoxLayer().get(i);
            if (box != currentBox) {
                if (box.hasIntersection(currentBox) != null) {
                    box.intersectBox();
                    currentBox.intersectBox();
                }
            }
        }
    }

    private void boxWallCollision(Wall wall) {
        for (int i = 0, size = board.getBoxLayer().size; i < size; i++) {
            Box box = board.getBoxLayer().get(i);
            if (box.hasIntersection(wall) != null) {
                box.intersectWall();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isBoardOver() {
        return isBoardOver;
    }

    public void undoMove() {
        Array<String> moveTrack = player.getMoveTrack();
        if (!player.isIdle()) {
            // Only undo if the player is idle
            return;
        }
        if (moveTrack.size == 0) {
            // well if you have not moved then you can not undo...
            return;
        }
        int lastPlayerPosition = moveTrack.size - 1;
        String lastPlayerMove = moveTrack.get(lastPlayerPosition);
        // go through each box to checkmark if they moved with the player
        for (int i = 0, size = board.getBoxLayer().size; i < size; i++) {
            Box box = board.getBoxLayer().get(i);
            Array<String> boxTrack = box.getMoveTrack();
            if (boxTrack.size > 0) {
                int lastBoxMovePos = boxTrack.size - 1;
                String[] lastMoveArray = MoveTrack.parseBoxMove(boxTrack.get(lastBoxMovePos));
                int playerPos = Integer.parseInt(lastMoveArray[MoveTrack.BOX_TRACK_PLAYER_POS]);
                if (playerPos == lastPlayerPosition) {
                    // The player can only move one box at a time
                    String lastBoxMove = lastMoveArray[MoveTrack.BOX_TRACK_MOVE];
                    // reverse the movement
                    int reversalBox = MoveTrack.getReverseMove(lastBoxMove);
                    if (MoveTrack.isXMovement(lastBoxMove)) {
                        box.setX(box.getX() + reversalBox);
                    } else {
                        box.setY(box.getY() + reversalBox);
                    }
                    // remove the move track
                    box.getMoveTrack().removeIndex(lastBoxMovePos);
                    break;
                }
            }
        }
        // reverse
        int reversalPlayer = MoveTrack.getReverseMove(lastPlayerMove);
        if (MoveTrack.isXMovement(lastPlayerMove)) {
            player.setX(player.getX() + reversalPlayer);
        } else {
            player.setY(player.getY() + reversalPlayer);
        }
        // move the move track
        player.getMoveTrack().removeIndex(lastPlayerPosition);
    }

    public LevelState getLevelState() {
        if (levelState == null) {
            levelState = new LevelState();
            levelState.setName(level.getName());
        }
        levelState.setPlayerTrack(getPlayer().getMoveTrack());
        levelState.setBoxTracks(board.getBoxMoveTracks());
        return levelState;
    }

}
