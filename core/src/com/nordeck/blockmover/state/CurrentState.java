package com.nordeck.blockmover.state;

import com.badlogic.gdx.utils.Json;
import com.nordeck.blockmover.Utils;

/**
 * Created by parker on 5/17/15.
 */
public class CurrentState {
    private String packFileName;
    private int position;

    public int getPosition() {
        return position;
    }

    public String getPackFileName() {
        return packFileName;
    }

    public static CurrentState newInstance(String saveJson) {
        if (Utils.isTextEmpty(saveJson)) {
            return null;
        }
        Json json = new Json();
        return json.fromJson(CurrentState.class, saveJson);
    }

    public CurrentState(String packFileName, int position) {
        this.packFileName = packFileName;
        this.position = position;
    }

    public CurrentState() {
    }

    public String toJson() {
        Json json = new Json();
        return json.toJson(this);
    }
}
