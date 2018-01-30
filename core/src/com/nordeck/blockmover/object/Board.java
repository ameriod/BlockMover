package com.nordeck.blockmover.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.nordeck.blockmover.Ludum32Game;
import com.nordeck.blockmover.Utils;
import com.nordeck.blockmover.object.ui.Level;
import com.nordeck.blockmover.parser.SokobanBoardParser;

/**
 * Created by parker on 5/9/15.
 */
public class Board {
    public static final String TAG = "Board";

    private SokobanBoardParser sokobanBoardParser;
    private Array<BaseMapObject> baseLayer;
    private Array<EndPoint> goalLayer;
    private Array<Box> boxLayer;
    int goalCount;
    private int boardWidth, boardHeight;
    private int playerStartY, playerStartX;

    public Board(Level level) {
        this.sokobanBoardParser = new SokobanBoardParser(level.getLevelMap());
        this.baseLayer = sokobanBoardParser.getBaseLayer();
        this.goalLayer = sokobanBoardParser.getGoalLayer();
        this.boxLayer = sokobanBoardParser.getBoxLayer();
        this.goalCount = sokobanBoardParser.getGoalLayer().size;
        this.boardHeight = sokobanBoardParser.getMaxCellHeight() * Ludum32Game.CELL_SIZE;
        this.boardWidth = sokobanBoardParser.getMaxCellWidth() * Ludum32Game.CELL_SIZE;
        this.playerStartY = sokobanBoardParser.getPlayerStartY();
        this.playerStartX = sokobanBoardParser.getPlayerStartX();

        Utils.log(TAG, "boardWidth: " + boardWidth + " boardHeight: " + boardHeight);
    }

    public void centerBoard(int boardWidth, int boardHeight) {
        int offSetWidth = (Ludum32Game.GAME_WIDTH - boardWidth) / 2;
        int offSetHeight = (Ludum32Game.GAME_HEIGHT - boardHeight) / 2;
        Utils.log(TAG, "offSetWidth: " + offSetWidth + " offSetHeight: " + offSetHeight);

        for (int i = 0, size = baseLayer.size; i < size; i++) {
            BaseGameObject object = baseLayer.get(i);
            object.setPosition(object.getX() + offSetWidth, object.getY() + offSetHeight);
        }

        for (int i = 0, size = goalLayer.size; i < size; i++) {
            BaseGameObject object = goalLayer.get(i);
            object.setPosition(object.getX() + offSetWidth, object.getY() + offSetHeight);
        }

        for (int i = 0, size = boxLayer.size; i < size; i++) {
            BaseGameObject object = boxLayer.get(i);
            object.setPosition(object.getX() + offSetWidth, object.getY() + offSetHeight);
        }

        playerStartY = playerStartY + offSetHeight;
        playerStartX = playerStartX + offSetWidth;
    }


    public void update(float delta) {
        for (int i = 0, size = baseLayer.size; i < size; i++) {
            baseLayer.get(i).update(delta);
        }
        for (int i = 0, size = goalLayer.size; i < size; i++) {
            goalLayer.get(i).update(delta);
        }
        for (int i = 0, size = boxLayer.size; i < size; i++) {
            boxLayer.get(i).update(delta);
        }
    }

    public void render(SpriteBatch spriteBatch, float runTime) {
        for (int i = 0, size = baseLayer.size; i < size; i++) {
            baseLayer.get(i).draw(spriteBatch, runTime);
        }
        for (int i = 0, size = goalLayer.size; i < size; i++) {
            goalLayer.get(i).draw(spriteBatch, runTime);
        }
        for (int i = 0, size = boxLayer.size; i < size; i++) {
            boxLayer.get(i).draw(spriteBatch, runTime);
        }
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        for (int i = 0, size = baseLayer.size; i < size; i++) {
            baseLayer.get(i).drawDebug(shapeRenderer);
        }
        for (int i = 0, size = goalLayer.size; i < size; i++) {
            goalLayer.get(i).drawDebug(shapeRenderer);
        }
        for (int i = 0, size = boxLayer.size; i < size; i++) {
            boxLayer.get(i).drawDebug(shapeRenderer);
        }
    }

    public int getPlayerStartX() {
        return playerStartX;
    }

    public int getPlayerStartY() {
        return playerStartY;
    }

    public Array<BaseMapObject> getBaseLayer() {
        return baseLayer;
    }

    public Array<EndPoint> getGoalLayer() {
        return goalLayer;
    }

    public Array<Box> getBoxLayer() {
        return boxLayer;
    }

    public int getGoalCount() {
        return goalCount;
    }

    public void restoreBoxStates(Array<Array<String>> boxStates) {
        for (int i = 0, size = boxStates.size; i < size; i++) {
            boxLayer.get(i).restoreState(boxStates.get(i));
        }
    }

    public Array<Array<String>> getBoxMoveTracks() {
        Array<Array<String>> boxStates = new Array<Array<String>>();
        for (int i = 0, size = boxLayer.size; i < size; i++) {
            boxStates.add(boxLayer.get(i).getMoveTrack());
        }
        return boxStates;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
}
