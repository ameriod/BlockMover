package com.nordeck.blockmover.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.nordeck.blockmover.AssetLoader;
import com.nordeck.blockmover.Ludum32Game;
import com.nordeck.blockmover.PrefUtils;
import com.nordeck.blockmover.object.ui.IconButton;
import com.nordeck.blockmover.object.ui.Level;
import com.nordeck.blockmover.object.ui.LevelPack;
import com.nordeck.blockmover.state.GameState;
import com.nordeck.blockmover.state.LevelState;
import com.nordeck.blockmover.state.PackState;

import java.util.Comparator;

/**
 * Created by parker on 5/15/15.
 */
public class LevelPacksScreen extends BaseScreen {
    public static final String TAG = "LevelsScreen";

    public static final String LEVELS_PATH = "levels/";

    private ScrollPane scrollPaneLevelsList;
    private Table levelsTable;
    private Table packTable;
    private GameState gameState;

    public LevelPacksScreen() {
        gameState = PrefUtils.getInstance().getGameState();

        Table table = new Table(AssetLoader.skin);
        table.setBackground(new SpriteDrawable(new Sprite(AssetLoader.groundGrass)));
        table.setFillParent(true);
        table.columnDefaults(2);
        stage.addActor(table);

        // Pack List
        packTable = new Table(AssetLoader.skin).align(Align.topLeft);

        IconButton btnMenu = new IconButton(AssetLoader.skin, AssetLoader.menu);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new MenuScreen());
            }
        });
        Label labelHeader = new Label(MenuScreen.LEVEL_PACKS, AssetLoader.skin, AssetLoader.DEFAULT_LARGE_LABEL);

        Table headerTable = new Table();
        table.setWidth(packTable.getWidth());
        table.align(Align.topLeft);

        // need to set specific size
        headerTable.add(btnMenu)
                .width(btnMenu.getWidth())
                .align(Align.right);
        // label takes up the remaining space
        headerTable.add(labelHeader)
                .expandX()
                .align(Align.center);
        // need to set a size on the row
        packTable.add(headerTable)
                .width(Ludum32Game.GAME_WIDTH / 2 - (AssetLoader.PADDING * 2))
                .height(labelHeader.getHeight()).row();

        ScrollPane scrollPanePack = new ScrollPane(packTable, AssetLoader.skin);
        scrollPanePack.setFlickScroll(true);
        table.add(scrollPanePack)
                .pad(AssetLoader.PADDING)
                .width(Ludum32Game.GAME_WIDTH / 2 - (AssetLoader.PADDING * 2))
                .height(Ludum32Game.GAME_HEIGHT - AssetLoader.PADDING);
        addPacks(getLevelPacks());

        // Levels list
        levelsTable = new Table(AssetLoader.skin).align(Align.topLeft);
        scrollPaneLevelsList = new ScrollPane(levelsTable, AssetLoader.skin);
        scrollPaneLevelsList.setFlickScroll(true);
        table.add(scrollPaneLevelsList)
                .pad(AssetLoader.PADDING)
                .width(Ludum32Game.GAME_WIDTH / 2 - (AssetLoader.PADDING * 2))
                .height(Ludum32Game.GAME_HEIGHT - AssetLoader.PADDING);

    }

    private void addLevels(final LevelPack levelPack) {
        levelPack.parseLevelPack();
        levelsTable.clear();

        Label titleLabel = new Label(levelPack.getName(), AssetLoader.skin);
        titleLabel.setAlignment(Align.left);
        titleLabel.setWrap(true);
        levelsTable.add(titleLabel)
                .width(scrollPaneLevelsList.getWidth())
                .align(Align.left)
                .padBottom(AssetLoader.PADDING / 2)
                .row();

        Label descLabel = new Label(levelPack.getDescription(), AssetLoader.skin);
        descLabel.setAlignment(Align.left);
        descLabel.setWrap(true);
        levelsTable.add(descLabel)
                .width(scrollPaneLevelsList.getWidth())
                .align(Align.left)
                .row();

        PackState packState = gameState.getPackState(levelPack.getFileName());

        for (int i = 0, size = levelPack.getLevels().size; i < size; i++) {
            final Level level = levelPack.getLevels().get(i);
            final int position = i;
            Table table = new Table();
            table.setWidth(levelsTable.getWidth());
            table.align(Align.topLeft);

            boolean isInProgress = false;
            boolean isComplete = false;
            Texture playTexture;

            LevelState levelState = packState.getLevelState(level.getName());
            if (levelState != null) {
                isInProgress = levelState.isInProgress();
                isComplete = levelState.isCompleted();
            }
            if (isInProgress) {
                playTexture = AssetLoader.open;
            } else {
                playTexture = AssetLoader.play;
            }

            IconButton btnPlay = new IconButton(AssetLoader.skin, playTexture);
            btnPlay.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setScreen(new GameScreen(gameState, levelPack, position));
                }
            });

            Label labelName = new Label(level.getName(), AssetLoader.skin, AssetLoader.DEFAULT_LARGE_LABEL);
            labelName.setWrap(true);
            labelName.setAlignment(Align.left);

            if (isComplete) {
                Image check = new Image();
                check.setDrawable(new SpriteDrawable(new Sprite(AssetLoader.checkmark)));
                // need to set specific size (and make a little smaller)
                table.add(check)
                        .width(AssetLoader.checkmark.getWidth() / 2)
                        .height(AssetLoader.checkmark.getHeight() / 2);
            }
            // label takes up the remaining space
            table.add(labelName)
                    .expandX()
                    .align(Align.left);
            // need to set specific size
            table.add(btnPlay)
                    .width(btnPlay.getWidth())
                    .align(Align.right);
            // need to set a width to the row
            levelsTable.add(table)
                    .pad(0, 0, AssetLoader.PADDING, 0)
                    .width(levelsTable.getWidth())
                    .height(labelName.getHeight())
                    .row();

        }
    }

    public static Array<LevelPack> getLevelPacks() {
        Array<LevelPack> levelPacks = new Array<LevelPack>();
        // Get all of the levels
        FileHandle[] files = Gdx.files.internal(LEVELS_PATH).list();
        for (FileHandle file : files) {
            levelPacks.add(new LevelPack(file));
        }
        Sort.instance().sort(levelPacks, new Comparator<LevelPack>() {
            @Override
            public int compare(LevelPack o1, LevelPack o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return levelPacks;
    }

    private void addPacks(Array<LevelPack> packs) {
        for (int i = 0, size = packs.size; i < size; i++) {
            final LevelPack pack = packs.get(i);
            TextButton btnPack = new TextButton(" " + pack.getName() + "  ", AssetLoader.skin);
            btnPack.getLabel().setAlignment(Align.left);
            packTable.add(btnPack)
                    .fill()
                    .align(Align.left)
                    .pad(AssetLoader.PADDING / 2, 0, AssetLoader.PADDING / 2, 0)
                    .row();
            btnPack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    addLevels(pack);
                    scrollPaneLevelsList.setScrollPercentY(0);
                }
            });
        }
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
