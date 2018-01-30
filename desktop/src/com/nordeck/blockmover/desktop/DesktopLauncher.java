package com.nordeck.blockmover.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nordeck.blockmover.Ludum32Game;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = Ludum32Game.GAME_HEIGHT / 2;
        config.width = Ludum32Game.GAME_WIDTH / 2;
        config.resizable = false;
        new LwjglApplication(new Ludum32Game(), config);
    }
}
