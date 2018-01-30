package com.nordeck.blockmover.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by parker on 5/9/15.
 */
public class EndPoint extends BaseMapObject {
    public static final String TAG = "EndPoint";

    private Texture endPointTexture;

    public EndPoint(float x, float y, Texture endPointTexture) {
        super(x, y, true);
        this.endPointTexture = endPointTexture;
    }

    @Override
    public void actOnPlayer(Player player, Rectangle intersection) {

    }

    @Override
    public void actOnMapObject(BaseMapObject interactingMapObject, Rectangle intersection) {

    }

    @Override
    public void draw(SpriteBatch spriteBatch, float runTime) {
        // since the cells are 64 x 64 and the end points are smaller center the drawing area
        spriteBatch.draw(endPointTexture,
                getX() + ((getWidth() - endPointTexture.getWidth()) / 2f),
                getY() + ((getHeight() - endPointTexture.getHeight()) / 2f),
                endPointTexture.getWidth(),
                endPointTexture.getHeight());
    }

    @Override
    public void initAssets() {

    }
}
