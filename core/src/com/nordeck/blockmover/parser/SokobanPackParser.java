package com.nordeck.blockmover.parser;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.nordeck.blockmover.Utils;
import com.nordeck.blockmover.object.ui.Level;

/**
 * Created by parker on 5/16/15.
 */
public class SokobanPackParser {
    public static final String TAG = "SokobanPackParser";

    private String text;

    private static final String COPYRIGHT = "Copyright:";
    private static final String EMAIL = "E-Mail:";
    private static final String WEBSITE = "Web Site:";
    // Non-level lines start with this
    private static final String DELIMITER = ";";

    private String description = "";
    private String name;
    private Array<Level> levels;

    private static final int NAME_LINE = 0;
    private static final int EMPTY_DESCRIPTION_LINE_COUNT = 1;
    private static final int LEVEL_START_POSITIION = 1;

    public SokobanPackParser(FileHandle file) {
        // the sokoban level format is text
        this.text = file.readString();
        this.description = "";
        this.name = "";
        this.levels = new Array<Level>();
        parse();
    }

    private void parse() {
        int emptyDescriptionLineCount = 0;
        int levelSpaceCount = 0;
        Level newLevel = null;
        String[] lines = text.split("\\r?\\n");
        for (int i = 0, size = lines.length; i < size; i++) {
            String line = lines[i];
            if (i == NAME_LINE) {
                // save the name separate for the description
                name = createDescLine(line);
                // skip the next line since it is a space
                i++;
                continue;
            } else if (hasDelimiter(line) && emptyDescriptionLineCount < EMPTY_DESCRIPTION_LINE_COUNT) {
                // Build out the description
                description += createDescLine(line);
                continue;
            }
            emptyDescriptionLineCount++;

            // now where the real level parsing happens
            if (Utils.isTextEmpty(line)) {
                levelSpaceCount++;
                continue;
            }
            if (levelSpaceCount == LEVEL_START_POSITIION) {
                newLevel = new Level();
            }
            if (hasDelimiter(line)) {
                // get the name
                String name = createNameLine(line);
                if (Utils.isTextEmpty(name)) {
                    // No name then show the position of the level within the pack
                    name = String.valueOf(i + 1);
                }
                newLevel.setName(name);
                // finish up the level and add it
                levels.add(newLevel);
                // reset for the next level
                levelSpaceCount = 0;
            } else {
                newLevel.addLineToLevel(line);
                levelSpaceCount++;
            }
        }
    }

    private String createDescLine(String line) {
        return createNameLine(line) + "\n";
    }

    private String createNameLine(String line) {
        return line.replace("; ", "").replace(";", "");
    }

    private boolean hasDelimiter(String line) {
        return line.contains(DELIMITER);
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Array<Level> getLevels() {
        return levels;
    }
}
