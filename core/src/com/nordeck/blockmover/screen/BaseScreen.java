package com.nordeck.blockmover.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nordeck.blockmover.Ludum32Game;

public abstract class BaseScreen implements Screen {

    protected Stage stage;
    protected InputMultiplexer inputMultiplexer;


    public BaseScreen() {
        stage = new Stage(new FitViewport(Ludum32Game.GAME_WIDTH, Ludum32Game.GAME_HEIGHT));
        // Catch multiple input points (just add new input processors to the InputMultiplexer)
        inputMultiplexer = new InputMultiplexer(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        renderStage(delta);
    }

    protected void renderStage(float delta) {
        stage.act(delta);
        stage.draw();
    }

    protected void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setScreen(Screen screen) {
        ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
    }

}