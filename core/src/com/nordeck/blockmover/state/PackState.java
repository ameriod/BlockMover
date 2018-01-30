package com.nordeck.blockmover.state;

import com.badlogic.gdx.utils.Array;
import com.nordeck.blockmover.Utils;

/**
 * Created by parker on 5/17/15.
 */
public class PackState {
    private String fileName;
    private Array<LevelState> levelStates;

    public PackState() {
    }

    public PackState(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public Array<LevelState> getLevelStates() {
        return levelStates;
    }

    /**
     * If there is no level state then this is null
     * @param levelName
     * @return
     */
    public LevelState getLevelState(String levelName) {
        if (levelStates == null) {
            levelStates = new Array<LevelState>();
        }
        for (int i = 0, size = levelStates.size; i < size; i++) {
            LevelState state = levelStates.get(i);
            if (state.getName().equals(levelName)) {
                return state;
            }
        }
        return null;
    }

    public void updateLevelState(LevelState updateState) {
        if (levelStates == null) {
            // completely new game
            levelStates = new Array<LevelState>();
        }
        for (int i = 0, size = levelStates.size; i < size; i++) {
            LevelState state = levelStates.get(i);
            if (Utils.equals(state.getName(), updateState.getName())) {
                levelStates.removeIndex(i);
                break;
            }
        }
        // Remove then re-add
        levelStates.add(updateState);
    }
}
