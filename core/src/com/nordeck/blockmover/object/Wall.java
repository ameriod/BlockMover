package com.nordeck.blockmover.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by parker on 5/9/15.
 */
public class Wall extends BaseMapObject {
    public static final String TAG = "Wall";

    private Texture wallTexture;

    private float oldX, oldY;

    public Wall(float x, float y, Texture wallTexture) {
        super(x, y, true);
        this.wallTexture = wallTexture;
    }

    @Override
    public void actOnPlayer(Player player, Rectangle intersection) {

    }

    @Override
    public void actOnMapObject(BaseMapObject interactingMapObject, Rectangle intersection) {

    }

    @Override
    public void draw(SpriteBatch spriteBatch, float runTime) {
        spriteBatch.draw(wallTexture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void initAssets() {
        // no impl since the texture is passed in
    }
}
