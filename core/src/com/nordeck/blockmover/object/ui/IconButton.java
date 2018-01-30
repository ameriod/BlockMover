package com.nordeck.blockmover.object.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by parker on 5/15/15.
 */
public class IconButton extends TextButton {

    private Texture icon;

    public IconButton(Skin skin, Texture icon) {
        super("", skin);
        this.icon = icon;
    }

    @Override
    public float getWidth() {
        return icon.getWidth() / 1.5f;
    }

    @Override
    public float getHeight() {
        return icon.getHeight() / 1.5f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(icon, getX(), getY(), getWidth(), getHeight());
    }
}
