package com.nordeck.blockmover.parser;

import com.badlogic.gdx.utils.Array;
import com.nordeck.blockmover.AssetLoader;
import com.nordeck.blockmover.Ludum32Game;
import com.nordeck.blockmover.object.*;

/**
 * Created by parker on 5/9/15.
 */
public class SokobanBoardParser {
    public static final String TAG = "SokobanParser";

    protected String boardStr;
    protected String[] boardSegments;

    public static final String WALL = "#";
    public static final String PLAYER = "@";
    public static final String PLAYER_ON_GOAL = "+";
    public static final String BOX = "$";
    public static final String BOX_ON_GOAL = "*";
    public static final String GOAL = ".";
    public static final String FLOOR = " ";

    private Array<BaseMapObject> baseLayer;
    private Array<EndPoint> goalLayer;
    private Array<Box> boxLayer;

    private int playerStartX, playerStartY;
    int maxCellWidth = 0, maxCellHeight = 0;

    public SokobanBoardParser(String boardStr) {
        this.boardStr = boardStr;
        this.boardSegments = getBoardSegments(boardStr);
        buildBoard(boardSegments);
    }

    public String getBoardStr() {
        return boardStr;
    }

    public String[] getBoardSegments() {
        return boardSegments;
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

    public int getPlayerStartX() {
        return playerStartX;
    }

    public int getPlayerStartY() {
        return playerStartY;
    }

    public void buildBoard(String[] boardSegments) {
        baseLayer = new Array<BaseMapObject>();
        goalLayer = new Array<EndPoint>();
        boxLayer = new Array<Box>();
        maxCellHeight = boardSegments.length;
        for (int y = 0, sizeY = boardSegments.length; y < sizeY; y++) {
            char[] rowChars = boardSegments[y].toCharArray();
            if (maxCellWidth < rowChars.length) {
                maxCellWidth = rowChars.length;
            }
            for (int x = 0, sizeX = rowChars.length; x < sizeX; x++) {
                String cell = String.valueOf(rowChars[x]);
                int xPos = getX(x);
                int yPos = getY(y);
                if (cell.equals(WALL)) {
                    addWall(xPos, yPos);
                } else if (cell.equals(FLOOR)) {
                    addFloor(xPos, yPos);
                } else if (cell.equals(BOX)) {
                    addBox(xPos, yPos, true);
                } else if (cell.equals(GOAL)) {
                    addGoal(xPos, yPos);
                } else if (cell.equals(PLAYER)) {
                    setPlayer(xPos, yPos);
                    addFloor(xPos, yPos);
                } else if (cell.equals(PLAYER_ON_GOAL)) {
                    setPlayer(xPos, yPos);
                    addGoal(xPos, yPos);
                } else if (cell.equals(BOX_ON_GOAL)) {
                    addBox(xPos, yPos, false);
                    addGoal(xPos, yPos);
                }
            }
        }
    }

    private void setPlayer(int x, int y) {
        playerStartX = x;
        playerStartY = y;
    }

    private void addWall(int x, int y) {
        Wall wall = new Wall(x, y, AssetLoader.wallBeige);
        baseLayer.add(wall);
    }

    private void addBox(int x, int y, boolean addFloor) {
        Box box = new Box(x, y, AssetLoader.crateBrown, AssetLoader.crateDarkBrown);
        boxLayer.add(box);
        if (addFloor) {
            Ground ground = new Ground(x, y, AssetLoader.groundGrass);
            baseLayer.add(ground);
        }
    }

    private void addFloor(int x, int y) {
        Ground ground = new Ground(x, y, AssetLoader.groundGrass);
        baseLayer.add(ground);
    }

    private void addGoal(int x, int y) {
        EndPoint endPoint = new EndPoint(x, y, AssetLoader.endPointBeige);
        goalLayer.add(endPoint);
        Ground ground = new Ground(x, y, AssetLoader.groundGrass);
        baseLayer.add(ground);
    }


    private int getX(int x) {
        return x * Ludum32Game.CELL_SIZE;
    }

    private int getY(int y) {
        return y * Ludum32Game.CELL_SIZE;
    }

    public int getMaxCellWidth() {
        return maxCellWidth;
    }

    public int getMaxCellHeight() {
        return maxCellHeight;
    }

    private String[] getBoardSegments(String boardStr) {
        return boardStr.split("\\r?\\n");
    }
}
