package com.nordeck.blockmover.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.nordeck.blockmover.*;
import com.nordeck.blockmover.object.ui.IconButton;
import com.nordeck.blockmover.object.ui.IconLabel;
import com.nordeck.blockmover.object.ui.Level;
import com.nordeck.blockmover.object.ui.LevelPack;
import com.nordeck.blockmover.state.CurrentState;
import com.nordeck.blockmover.state.GameState;
import com.nordeck.blockmover.state.LevelState;
import com.nordeck.blockmover.state.PackState;

/**
 * Created by parker on 5/8/15.
 */
public class GameScreen extends BaseScreen {
    public static final String TAG = "GameScreen";

    private GameWorld gameWorld;
    private GameRenderer gameRenderer;
    private GameInputProcessor gameInputProcessor;
    private int position;

    protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;
    protected OrthographicCamera gameCamera;

    private float runTime = 0;

    private LevelPack levelPack;
    private GameState gameState;
    private PackState packState;
    private LevelState currentLevelState;

    private IconLabel labelHeader;
    private ImageButton btnPreviousBoard, btnNextBoard;

    public GameScreen(GameState gameState, LevelPack levelPack, final int position) {
        super();
        this.levelPack = levelPack;
        this.gameState = gameState;
        this.position = position;
        this.packState = gameState.getPackState(levelPack.getFileName());

        this.spriteBatch = new SpriteBatch();
        if (Ludum32Game.IS_DEBUG) {
            shapeRenderer = new ShapeRenderer();
        }
        this.gameCamera = new OrthographicCamera(Ludum32Game.GAME_WIDTH, Ludum32Game.GAME_HEIGHT);
        this.gameWorld = initWorld(position);
        this.gameRenderer = new GameRenderer(gameWorld, gameCamera);
        // use the on screen controls
        this.gameInputProcessor = new GameInputProcessor(gameWorld, gameCamera, false);
        inputMultiplexer.addProcessor(gameInputProcessor);

        IconButton btnMenu = new IconButton(AssetLoader.skin, AssetLoader.menu);
        float btnYHeightTop = Ludum32Game.GAME_HEIGHT - btnMenu.getHeight() - AssetLoader.PADDING;
        btnMenu.setWidth(btnMenu.getWidth() + AssetLoader.PADDING * 2);
        btnMenu.setPosition(AssetLoader.PADDING, btnYHeightTop);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveState();
                setScreen(new MenuScreen());
            }
        });
        stage.addActor(btnMenu);

        IconButton btnUndo = new IconButton(AssetLoader.skin, AssetLoader.undo);
        btnUndo.setWidth(btnUndo.getWidth() + AssetLoader.PADDING * 2);
        btnUndo.setPosition(btnMenu.getX() + btnMenu.getWidth() + AssetLoader.PADDING, btnYHeightTop);
        btnUndo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameWorld.undoMove();
            }
        });
        stage.addActor(btnUndo);

        IconButton btnRestart = new IconButton(AssetLoader.skin, AssetLoader.restart);
        btnRestart.setWidth(btnRestart.getWidth() + AssetLoader.PADDING * 2);
        btnRestart.setPosition(btnUndo.getX() + btnMenu.getWidth() + AssetLoader.PADDING, btnYHeightTop);
        btnRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restart();
                currentLevelState = gameWorld.getLevelState();
                displayCurrentLevelState(currentLevelState);

            }
        });
        stage.addActor(btnRestart);

        IconButton btnZoomIn = new IconButton(AssetLoader.skin, AssetLoader.zoomIn);
        btnZoomIn.setWidth(btnZoomIn.getWidth() + AssetLoader.PADDING * 2);
        btnZoomIn.setPosition(Ludum32Game.GAME_WIDTH - btnZoomIn.getWidth() - AssetLoader.PADDING,
                btnYHeightTop);
        btnZoomIn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameInputProcessor.zoomIn();
            }
        });
        stage.addActor(btnZoomIn);

        IconButton btnZoomOut = new IconButton(AssetLoader.skin, AssetLoader.zoomOut);
        btnZoomOut.setWidth(btnZoomOut.getWidth() + AssetLoader.PADDING * 2);
        btnZoomOut.setPosition(Ludum32Game.GAME_WIDTH - btnZoomOut.getWidth() - AssetLoader.PADDING,
                btnZoomIn.getY() - btnZoomOut.getHeight() - AssetLoader.PADDING);
        btnZoomOut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameInputProcessor.zoomOut();
            }
        });
        stage.addActor(btnZoomOut);

        // On screen controls
        float leftRightYPosition = Ludum32Game.GAME_HEIGHT / 2 - (AssetLoader.controlLeft.getHeight() / 2);
        float controlPadding = AssetLoader.PADDING * 2;

        ImageButton btnControlLeft = new ImageButton(new SpriteDrawable(new Sprite(AssetLoader.controlLeft)));
        btnControlLeft.setPosition(controlPadding, leftRightYPosition);
        btnControlLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameWorld.getPlayer().moveLeft();
            }
        });
        stage.addActor(btnControlLeft);

        ImageButton btnControlRight = new ImageButton(new SpriteDrawable(new Sprite(AssetLoader.controlRight)));
        btnControlRight.setPosition(btnControlLeft.getX() + btnControlLeft.getWidth() + controlPadding * 2,
                leftRightYPosition);
        btnControlRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameWorld.getPlayer().moveRight();
            }
        });
        stage.addActor(btnControlRight);

        float upDownXPosition = (btnControlRight.getX() - btnControlLeft.getX()) / 2 + btnControlLeft.getX();
        float upYPosition = leftRightYPosition + btnControlLeft.getHeight() / 2 + controlPadding;
        float downYPosition = leftRightYPosition - btnControlLeft.getHeight() / 2 - controlPadding;

        ImageButton btnControlUp = new ImageButton(new SpriteDrawable(new Sprite(AssetLoader.controlUp)));
        btnControlUp.setPosition(upDownXPosition, upYPosition);
        btnControlUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameWorld.getPlayer().moveUp();
            }
        });
        stage.addActor(btnControlUp);

        ImageButton btnControlDown = new ImageButton(new SpriteDrawable(new Sprite(AssetLoader.controlDown)));
        btnControlDown.setPosition(upDownXPosition, downYPosition);
        btnControlDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameWorld.getPlayer().moveDown();
            }
        });
        stage.addActor(btnControlDown);

        // On Screen level pager
        Table headerTable = new Table();

        labelHeader = new IconLabel(null, "", AssetLoader.skin);
        float headerXStart = btnRestart.getX() + btnRestart.getWidth() + 2 * AssetLoader.PADDING;
        float headerXStop = btnZoomIn.getX() - 2 * AssetLoader.PADDING;
        headerTable.setPosition(headerXStart, btnYHeightTop);
        headerTable.setSize(headerXStop - headerXStart, btnMenu.getHeight());

        btnPreviousBoard = new ImageButton(new SpriteDrawable(new Sprite(AssetLoader.arrowLeft)));
        btnPreviousBoard.setSize(btnPreviousBoard.getWidth() / 2, btnPreviousBoard.getHeight() / 2);
        btnPreviousBoard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setCurrentPosition(getCurrentPosition() - 1);
                incrementLevel(getCurrentPosition(), false);
            }
        });

        btnNextBoard = new ImageButton(new SpriteDrawable(new Sprite(AssetLoader.arrowRight)));
        btnNextBoard.setSize(btnNextBoard.getWidth() / 2, btnNextBoard.getHeight() / 2);
        btnNextBoard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setCurrentPosition(getCurrentPosition() + 1);
                incrementLevel(getCurrentPosition(), false);
            }
        });

        setupHeader(position);
        displayCurrentLevelState(currentLevelState);

        headerTable.add(btnPreviousBoard).width(btnPreviousBoard.getWidth());
        headerTable.add(labelHeader).expandX().center();
        headerTable.add(btnNextBoard).width(btnNextBoard.getWidth());
        stage.addActor(headerTable);

    }

    @Override
    public void show() {
    }

    @Override
    protected void renderStage(float delta) {
        // no impl we want the stage to draw over the game (this method gets called in the constructor)
    }

    @Override
    protected void clearScreen() {
        // TODO change the color based on the ground colors
        Gdx.gl.glClearColor(39.6f / 100f, 62.4f / 100f, 24.3f / 100f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        runTime += delta;
        gameWorld.update(delta);
        gameRenderer.render(spriteBatch, shapeRenderer, runTime);

        if (gameWorld.isBoardOver()) {
            position++;
            // Remove the player input
            incrementLevel(position, true);
        }

        // Ui over the game
        stage.act(delta);
        stage.draw();
    }

    private void setupHeader(int position) {
        int levelsSize = levelPack.getLevels().size;
        if (position > levelsSize) {
            return;
        }
        if (position == 0) {
            btnPreviousBoard.setVisible(false);
        } else if (position >= levelsSize - 1) {
            btnNextBoard.setVisible(false);
        } else {
            btnPreviousBoard.setVisible(true);
            btnNextBoard.setVisible(true);
        }
        Level level = getCurrentLevel(position);
        String headerName = levelPack.getName() + " - " + (position + 1) + "/" + levelPack.getLevels().size + ": " +
                level.getName();
        labelHeader.setText(headerName);
    }

    private void displayCurrentLevelState(LevelState currentLevelState) {
        if (labelHeader == null) {
            return;
        }
        if (currentLevelState != null) {
            if (currentLevelState.isCompleted()) {
                labelHeader.setIcon(AssetLoader.checkmark);
            } else if (currentLevelState.isInProgress()) {
                labelHeader.setIcon(AssetLoader.open);
            } else {
                labelHeader.setIcon(null);
            }
        }
    }

    private LevelState getCurrentLevelState(int position) {
        return packState.getLevelState(getCurrentLevel(position).getName());
    }

    private Level getCurrentLevel(int position) {
        return levelPack.getLevels().get(position);
    }

    private void setCurrentPosition(int position) {
        this.position = position;
    }

    private int getCurrentPosition() {
        return position;
    }

    private void incrementLevel(int newPosition, boolean completed) {
        setupHeader(newPosition);
        updateLevelState(completed);
        if (newPosition < levelPack.getLevels().size) {
            gameWorld = initWorld(newPosition);
            gameRenderer.setGameWorld(gameWorld);
            gameInputProcessor.setGameWorld(gameWorld);
        } else {
            // TODO The pack is over
            setScreen(new MenuScreen());
        }
    }

    private void restart() {
        // clear out the saved movement tracks
        if (currentLevelState != null) {
            currentLevelState.setBoxTracks(null);
            currentLevelState.setPlayerTrack(null);
        }
        gameWorld = new GameWorld(getCurrentLevel(position), currentLevelState);
        gameRenderer.setGameWorld(gameWorld);
        gameInputProcessor.setGameWorld(gameWorld);
    }

    private GameWorld initWorld(int position) {
        // the current state could be null
        currentLevelState = getCurrentLevelState(position);
        GameWorld gameWorld = new GameWorld(getCurrentLevel(position), currentLevelState);
        gameCamera.zoom = gameWorld.getStartZoomState();
        gameCamera.update();
        displayCurrentLevelState(currentLevelState);
        return gameWorld;
    }

    @Override
    public void pause() {
        saveState();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        saveState();
    }

    @Override
    public void dispose() {
        super.dispose();
        saveState();
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }

    /**
     * Updates the pack state with the current level state, and marks as complete if needed (clears out everything)
     *
     * @param complete
     */
    private void updateLevelState(boolean complete) {
        currentLevelState = gameWorld.getLevelState();
        if (complete) {
            // players can replay levels, completed true resets the tracks
            currentLevelState.setCompleted(true);
        }
        // Update the game state
        packState.updateLevelState(currentLevelState);
        packState = gameState.updatePackState(packState);
    }

    private void saveState() {
        // update the current level state (box moves, player moves so we can recreate the board)
        updateLevelState(false);
        // save the level currently one (level pack + position)
        PrefUtils.getInstance().setGameState(gameState);
        CurrentState currentState;
        if (position < levelPack.getLevels().size) {
            currentState = new CurrentState(levelPack.getFileName(), position);
        } else {
            currentState = null;
        }
        PrefUtils.getInstance().setCurrentState(currentState);
    }

}
