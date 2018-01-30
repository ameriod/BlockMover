package com.nordeck.blockmover.object.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nordeck.blockmover.AssetLoader;

/**
 * Created by parker on 5/19/15.
 */
public class IconLabel extends Label {

    private Texture icon;
    private String unbufferedText;

    public IconLabel(Texture icon, CharSequence text, Skin skin) {
        super(text, skin);
        setIcon(icon);
    }

    public Texture getIcon() {
        return icon;
    }

    public void setIcon(Texture icon) {
        this.icon = icon;
    }

    public float getIconWidth() {
        if (icon == null) {
            return 0;
        }
        return icon.getWidth() / 2;
    }

    public float getIconHeight() {
        if (icon == null) {
            return 0;
        }
        return icon.getHeight() / 2;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // make sure the icon is always centered in the button
        if (icon != null) {
            batch.draw(icon, getX() - getIconWidth() - AssetLoader.PADDING,
                    ((getHeight() - getIconHeight()) / 2f) + getY(), getIconWidth(), getIconHeight());
        }
    }
}
