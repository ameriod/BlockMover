package com.nordeck.blockmover.object.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.nordeck.blockmover.Utils;
import com.nordeck.blockmover.parser.SokobanPackParser;

/**
 * Created by parker on 5/16/15.
 */
public class LevelPack {

    public LevelPack(FileHandle fileHandle) {
        this.fileHandle = fileHandle;
        this.name = fileHandle.nameWithoutExtension().replace("_", " ");
        this.fileName = fileHandle.name();
    }

    /**
     * Debug stuff
     *
     * @param name
     * @param description
     * @param levels
     */
    public LevelPack(String name, String description, Array<Level> levels) {
        this.name = name;
        this.description = description;
        this.levels = levels;
    }

    private FileHandle fileHandle;
    private String name;
    private String fileName;
    private String description;
    private Array<Level> levels;

    public FileHandle getFileHandle() {
        return fileHandle;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public void parseLevelPack() {
        if (Utils.isTextEmpty(description) || levels == null || levels.size == 0) {
            SokobanPackParser packParser = new SokobanPackParser(fileHandle);
            description = packParser.getDescription();
            levels = packParser.getLevels();
        }
    }

    public String getDescription() {
        if (Utils.isTextEmpty(description)) {
            parseLevelPack();
        }
        return description;
    }

    public Array<Level> getLevels() {
        if (levels == null) {
            parseLevelPack();
        }
        return levels;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LevelPack levelPack = (LevelPack) o;

        if (name != null ? !name.equals(levelPack.name) : levelPack.name != null) return false;
        return !(fileName != null ? !fileName.equals(levelPack.fileName) : levelPack.fileName != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        return result;
    }
}
