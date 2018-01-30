package com.nordeck.blockmover.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.nordeck.blockmover.Ludum32Game;

/**
 * Base class used for walls, chests and floors
 * <p/>
 * Created by parker on 2/23/15.
 */
public abstract class BaseMapObject extends BaseGameObject {

    private boolean collidable;

    public BaseMapObject(float x, float y, boolean collidable) {
        super(x, y);
        setSize(Ludum32Game.CELL_SIZE, Ludum32Game.CELL_SIZE);
        this.collidable = collidable;
        if (collidable) {
            initBoundingBox();
        }
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void drawDebug(ShapeRenderer shapeRenderer) {
        drawDebugShape(shapeRenderer, Color.BLUE);
    }

    public boolean isCollidable() {
        return collidable;
    }

    public abstract void actOnPlayer(Player player, Rectangle intersection);

    public abstract void actOnMapObject(BaseMapObject interactingMapObject, Rectangle intersection);
}
