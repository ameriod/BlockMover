package com.nordeck.blockmover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.nordeck.blockmover.state.CurrentState;
import com.nordeck.blockmover.state.GameState;

/**
 * This is equivalent to shared prefs on Android. Keeps everything nice and static.
 * <p/>
 * Created by Parker on 1/26/2015.
 */
public class PrefUtils {
    public static final String TAG = "PrefUtils";

    private static PrefUtils sInstance;
    private static Preferences prefs;

    private static final String STORAGE_NAME = PrefUtils.class.getPackage() + ".game_prefs";

    private PrefUtils() {
        prefs = Gdx.app.getPreferences(STORAGE_NAME);
    }

    private static final String GAME_STATE = "game_state";
    private static final String CURRENT_SATE = "current_sate";

    public static PrefUtils getInstance() {
        if (sInstance == null) {
            sInstance = new PrefUtils();
        }
        return sInstance;
    }


    public void setGameState(GameState gameState) {
        String json = gameState.toJson();
        Utils.log(TAG, json);
        prefs.putString(GAME_STATE, json);
        prefs.flush();
    }

    public GameState getGameState() {
        return GameState.newInstance(prefs.getString(GAME_STATE));
    }

    public void setCurrentState(CurrentState currentState) {
        if (currentState == null) {
            prefs.putString(CURRENT_SATE, "");
        } else {
            prefs.putString(CURRENT_SATE, currentState.toJson());
        }
        prefs.flush();
    }

    public CurrentState getCurrentState() {
        return CurrentState.newInstance(prefs.getString(CURRENT_SATE));
    }
}
