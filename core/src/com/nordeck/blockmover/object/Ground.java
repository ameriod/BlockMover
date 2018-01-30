package com.nordeck.blockmover.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by parker on 5/9/15.
 */
public class Ground extends BaseMapObject {
    public static final String TAG = "Ground";

    private Texture groundTexture;

    public Ground(float x, float y, Texture groundTexture) {
        super(x, y, false);
        this.groundTexture = groundTexture;
    }

    @Override
    public void actOnPlayer(Player player, Rectangle intersection) {

    }

    @Override
    public void actOnMapObject(BaseMapObject interactingMapObject, Rectangle intersection) {

    }

    @Override
    public void draw(SpriteBatch spriteBatch, float runTime) {
        spriteBatch.draw(groundTexture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void initAssets() {
        // no impl since it is passed in
    }
}
