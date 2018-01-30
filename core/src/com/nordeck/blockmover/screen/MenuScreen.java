package com.nordeck.blockmover.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.nordeck.blockmover.AssetLoader;
import com.nordeck.blockmover.PrefUtils;
import com.nordeck.blockmover.object.ui.IconTextButton;
import com.nordeck.blockmover.object.ui.LevelPack;
import com.nordeck.blockmover.state.CurrentState;

/**
 * Created by parker on 5/15/15.
 */
public class MenuScreen extends BaseScreen {

    private CurrentState currentState;

    public static final String CONTINUE = "Continue";
    public static final String LEVEL_PACKS = "Level Packs";
    public static final String SETTINGS = "Settings";

    public MenuScreen() {
        Table table = new Table();
        table.setBackground(new SpriteDrawable(new Sprite(AssetLoader.groundGrass)));
        table.center();
        table.setFillParent(true);
        stage.addActor(table);

        // Only show the continue button if there is a current state
        currentState = PrefUtils.getInstance().getCurrentState();

        if (currentState != null) {
            IconTextButton btnContinue = new IconTextButton(CONTINUE, AssetLoader.skin, AssetLoader.play);
            btnContinue.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setScreen(new GameScreen(PrefUtils.getInstance().getGameState(),
                            new LevelPack(Gdx.files.internal(LevelPacksScreen.LEVELS_PATH + currentState.getPackFileName())),
                            currentState.getPosition()));
                }
            });
            table.add(btnContinue).width(AssetLoader.MENU_BTN_WIDTH).height(AssetLoader.MENU_BTN_HEIGHT).center()
                    .padBottom(AssetLoader.PADDING).row();
        }

        IconTextButton btnLevels = new IconTextButton(LEVEL_PACKS, AssetLoader.skin, AssetLoader.levels);
        btnLevels.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new LevelPacksScreen());
            }
        });
        table.add(btnLevels).width(AssetLoader.MENU_BTN_WIDTH).height(AssetLoader.MENU_BTN_HEIGHT).center()
                .padBottom(AssetLoader.PADDING).row();

        IconTextButton btnSettings = new IconTextButton(SETTINGS, AssetLoader.skin, AssetLoader.settings);
        btnSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO impl
            }
        });
        table.add(btnSettings).width(AssetLoader.MENU_BTN_WIDTH).height(AssetLoader.MENU_BTN_HEIGHT).center()
                .padBottom(AssetLoader.PADDING).row();
        // TODO show the settings button
        btnSettings.setVisible(false);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
