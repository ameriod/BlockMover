package com.nordeck.blockmover.object.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by parker on 5/15/15.
 */
public class IconTextButton extends TextButton {

    private Texture icon;

    public IconTextButton(String text, Skin skin, Texture icon) {
        super(text, skin);
        this.icon = icon;
    }

    public Texture getIcon() {
        return icon;
    }

    public float getIconWidth() {
        return icon.getWidth() / 1.5f;
    }

    public float getIconHeight() {
        return icon.getHeight() / 1.5f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // make sure the icon is always centered in the button
        batch.draw(icon, getX(), ((getHeight() - getIconHeight()) / 2f) + getY(), getIconWidth(), getIconHeight());
    }
}
