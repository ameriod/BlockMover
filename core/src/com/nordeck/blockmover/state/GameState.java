package com.nordeck.blockmover.state;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.nordeck.blockmover.Utils;

/**
 * Created by parker on 5/17/15.
 */
public class GameState {

    public static GameState newInstance(String saveJson) {
        if (Utils.isTextEmpty(saveJson)) {
            return new GameState();
        }
        Json json = new Json();
        return json.fromJson(GameState.class, saveJson);
    }

    public GameState() {
    }

    private Array<PackState> packStates;

    public Array<PackState> getPackStates() {
        return packStates;
    }

    public PackState updatePackState(PackState updatedPackState) {
        if (packStates == null) {
            // completely new game
            packStates = new Array<PackState>();
        }
        for (int i = 0, size = packStates.size; i < size; i++) {
            PackState packState = packStates.get(i);
            if (Utils.equals(updatedPackState.getFileName(), packState.getFileName())) {
                packStates.removeIndex(i);
                break;
            }
        }
        // Remove then re-add
        packStates.add(updatedPackState);
        return updatedPackState;
    }

    public PackState getPackState(String fileName) {
        if (packStates != null) {
            for (int i = 0, size = packStates.size; i < size; i++) {
                PackState packState = packStates.get(i);
                if (Utils.equals(fileName, packState.getFileName())) {
                    return packState;
                }
            }
        }
        // always create a new pack state file
        return new PackState(fileName);
    }

    public String toJson() {
        Json json = new Json();
        return json.toJson(this);
    }
}
