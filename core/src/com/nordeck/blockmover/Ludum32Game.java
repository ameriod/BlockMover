package com.nordeck.blockmover;

import com.badlogic.gdx.Game;
import com.nordeck.blockmover.screen.MenuScreen;

public class Ludum32Game extends Game {

    public static final boolean IS_DEBUG = true;

    //1280x720
    // 20 cells
    public static final int GAME_WIDTH = /*960*/ 1280 /*1920*/;
    // 11.25 cells
    public static final int GAME_HEIGHT = /*640*/ 720  /*1080*/;

    public static final int CELL_SIZE = 64;

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new MenuScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
