package com.nordeck.blockmover.state;

import com.badlogic.gdx.utils.Array;

/**
 * Created by parker on 5/17/15.
 */
public class LevelState {
    private String name;
    private boolean completed;
    private Array<String> playerTrack;
    private Array<Array<String>> boxTracks;
    private int pushes;
    private int moves;

    public LevelState() {
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * If true it updates the move / pushes history, clears out the player / box tracks
     *
     * @param completed
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed) {
            int newPushCount = getArrayMoveStateCount(getBoxTracks());
            int newMoveCount = getMoveStateCount(playerTrack);
            if (newMoveCount < moves || newPushCount < pushes) {
                // update since the player got better values
                pushes = newPushCount;
                moves = newMoveCount;
            }
            playerTrack = null;
            boxTracks = null;
        }
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Array<String> getPlayerTrack() {
        return playerTrack;
    }

    public void setPlayerTrack(Array<String> playerTrack) {
        this.playerTrack = playerTrack;
    }

    public Array<Array<String>> getBoxTracks() {
        return boxTracks;
    }

    public void setBoxTracks(Array<Array<String>> boxTracks) {
        this.boxTracks = boxTracks;
    }

    public static int getArrayMoveStateCount(Array<Array<String>> moves) {
        if (moves == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0, size = moves.size; i < size; i++) {
            count += getMoveStateCount(moves.get(i));
        }
        return count;
    }

    public static int getMoveStateCount(Array<String> moves) {
        if (moves == null) {
            return 0;
        }
        return moves.size;
    }

    public boolean isInProgress() {
        return playerTrack != null && boxTracks != null && playerTrack.size > 0 && boxTracks.size > 0;
    }
}
