package com.nordeck.blockmover.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * The constructor calls {@link #initAssets()} and sets the {@link #setPosition(float, float)}
 * <p/>
 * Created by Parker on 2/21/2015.
 */
public abstract class BaseGameObject {

    private float x;
    private float y;
    private int width;
    private int height;

    private Rectangle boundingBox;

    public BaseGameObject(float x, float y) {
        setPosition(x, y);
        initAssets();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        if (boundingBox != null) {
            boundingBox.setPosition(x, y);
        }
    }

    public void setX(float x) {
        setPosition(x, y);
    }

    public void setY(float y) {
        setPosition(x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        if (boundingBox != null) {
            boundingBox.setSize(width, height);
        }
    }

    /**
     * creates the bounding box
     */
    protected void initBoundingBox() {
        this.boundingBox = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    protected void drawDebugShape(ShapeRenderer shapeRenderer, Color color) {
        if (boundingBox != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(color);
            shapeRenderer.rect(boundingBox.getX(), boundingBox.getY(), boundingBox.getWidth(), boundingBox.getHeight());
            shapeRenderer.end();
        }
    }

    public abstract void update(float delta);

    public abstract void draw(SpriteBatch spriteBatch, float runTime);

    public abstract void drawDebug(ShapeRenderer shapeRenderer);

    public abstract void initAssets();

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public Rectangle hasIntersection(BaseMapObject object) {
        if (object.isCollidable() && getBoundingBox() != null) {
            if (object.getBoundingBox() != null) {
                Rectangle intersection = new Rectangle();
                boolean intersected = Intersector.intersectRectangles(object.getBoundingBox(), getBoundingBox(),
                        intersection);
                if (intersected) {
                    return intersection;
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}