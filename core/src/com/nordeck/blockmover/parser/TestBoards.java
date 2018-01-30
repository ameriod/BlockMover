package com.nordeck.blockmover.parser;

import com.badlogic.gdx.utils.Array;
import com.nordeck.blockmover.object.ui.Level;
import com.nordeck.blockmover.object.ui.LevelPack;

/**
 * Created by parker on 5/10/15.
 */

public class TestBoards {

    public static LevelPack getTestPack() {
        return new LevelPack("Test Levels", "These are the test levels using in the development of the game",
                getTestMaps());
    }

    public static Array<Level> getTestMaps() {
        Array<Level> levels = new Array<Level>();
        levels.add(new Level("Level 1", LEVEL_1));
        levels.add(new Level("Level 2", LEVEL_2));
        levels.add(new Level("Level 3", LEVEL_3));
        levels.add(new Level("Level 4", LEVEL_4));
        levels.add(new Level("Level 5", LEVEL_5));
        levels.add(new Level("Level 6", LEVEL_6));
        return levels;
    }

    public static final String LEVEL_1 = "#########\n" +
            "#@  $  .#\n" +
            "#########";

    public static final String LEVEL_2 = "########\n" +
            "#    ###\n" +
            "#@$  ###\n" +
            "#### ###\n" +
            "##   ###\n" +
            "##    ##\n" +
            "#  ##. #\n" +
            "#      #\n" +
            "#####  #\n" +
            "########";

    public static final String LEVEL_3 = "########\n" +
            "#      #\n" +
            "#  $  .#\n" +
            "#@ $  .#\n" +
            "#  $  .#\n" +
            "#      #\n" +
            "########";

    public static final String LEVEL_4 = "########\n" +
            "#  #.  #\n" +
            "# $#   #\n" +
            "#  # @##\n" +
            "#  # $##\n" +
            "#    .##\n" +
            "########";

    public static final String LEVEL_5 = "#########\n" +
            "##  #   #\n" +
            "#.$.  $ #\n" +
            "# #  ## #\n" +
            "# @$.$. #\n" +
            "#########";

    public static final String LEVEL_6 = "#########\n" +
            "#  #   .#\n" +
            "#@$ $   #\n" +
            "# $ ##..#\n" +
            "#   #####\n" +
            "#########";
}
