package com.nordeck.blockmover.object.ui;

import com.nordeck.blockmover.Utils;
import com.nordeck.blockmover.object.Board;

/**
 * Created by parker on 5/16/15.
 */
public class Level {
    private String name;
    private String levelMap;

    public Level(String name, String levelMap) {
        this.name = name;
        this.levelMap = levelMap;
    }

    public Level() {
    }

    public String getName() {
        return name;
    }

    public String getLevelMap() {
        return levelMap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevelMap(String levelMap) {
        this.levelMap = levelMap;
    }

    public void addLineToLevel(String line) {
        if (Utils.isTextEmpty(levelMap)) {
            levelMap = line;
        }
        levelMap += "\n" + line;
    }

    public Board getBoard() {
        return new Board(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level level = (Level) o;

        if (name != null ? !name.equals(level.name) : level.name != null) return false;
        return !(levelMap != null ? !levelMap.equals(level.levelMap) : level.levelMap != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (levelMap != null ? levelMap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
