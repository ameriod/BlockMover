package com.nordeck.blockmover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by parker on 5/8/15.
 */
public class AssetLoader {

    public static Texture character1, character2, character3, character4, character5, character6, character7,
            character8, character9, character10;

    public static Texture crateBrown, crateDarkBrown;

    public static Texture groundGrass;

    public static Texture wallBeige;

    public static Texture endPointBeige;

    public static Skin skin;
    // Game Screen Icons
    public static Texture zoomOut, zoomIn, menu, undo, restart, arrowLeft, arrowRight;
    public static Texture controlLeft, controlRight, controlUp, controlDown;
    // Menu Screen Icons
    public static Texture play, information, settings, levels;
    // Level Pack screen
    public static Texture checkmark, open;

    public static final int PADDING = 16;

    public static final int MENU_BTN_WIDTH = Ludum32Game.GAME_WIDTH / 3;
    public static final int MENU_BTN_HEIGHT = 72;

    public static final String DEFAULT_LARGE_LABEL = "default_large";
    private static final String LABEL_TEXT = "label";
    private static final String DEFAULT = "default";
    private static final String SKIN_WHITE = "white";

    public static void load() {
        character1 = new Texture("Character1.png");
        character2 = new Texture("Character2.png");
        character3 = new Texture("Character3.png");
        character4 = new Texture("Character4.png");
        character5 = new Texture("Character5.png");
        character6 = new Texture("Character6.png");
        character7 = new Texture("Character7.png");
        character8 = new Texture("Character8.png");
        character9 = new Texture("Character9.png");
        character10 = new Texture("Character10.png");

        crateBrown = new Texture("Crate_Brown.png");
        crateDarkBrown = new Texture("CrateDark_Brown.png");

        groundGrass = new Texture("Ground_Grass.png");

        wallBeige = new Texture("Wall_Beige.png");

        endPointBeige = new Texture("EndPoint_Beige.png");

        // ui styling
        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add(SKIN_WHITE, new Texture(pixmap));


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/UbuntuTitling-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterMain = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterMain.size = 48;
        BitmapFont defaultFont = generator.generateFont(parameterMain);
        parameterMain.size = 32;
        BitmapFont fontLabel = generator.generateFont(parameterMain);

        // Store the default libgdx font under the name "default".
        skin.add(DEFAULT, defaultFont);
        skin.add(LABEL_TEXT, fontLabel);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = skin.newDrawable(SKIN_WHITE, Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable(SKIN_WHITE, Color.LIGHT_GRAY);
        textButtonStyle.over = skin.newDrawable(SKIN_WHITE, Color.LIGHT_GRAY);
        textButtonStyle.checked = skin.newDrawable(SKIN_WHITE, Color.DARK_GRAY);
        textButtonStyle.checkedOver = skin.newDrawable(SKIN_WHITE, Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont(DEFAULT);
        skin.add(DEFAULT, textButtonStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont(LABEL_TEXT);
        skin.add(DEFAULT, labelStyle);

        Label.LabelStyle labelStyleLarge = new Label.LabelStyle();
        labelStyleLarge.font = skin.getFont(DEFAULT);
        skin.add(DEFAULT_LARGE_LABEL, labelStyleLarge);


        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        skin.add(DEFAULT, scrollPaneStyle);

        zoomOut = new Texture("ui/zoomOut.png");
        zoomIn = new Texture("ui/zoomIn.png");
        menu = new Texture("ui/barsHorizontal.png");
        undo = new Texture("ui/return.png");
        restart = new Texture("ui/trashcanOpen.png");
        controlLeft = new Texture("ui/controlLeft.png");
        controlRight = new Texture("ui/controlRight.png");
        controlUp = new Texture("ui/controlUp.png");
        controlDown = new Texture("ui/controlDown.png");
        arrowLeft = new Texture("ui/arrowLeft.png");
        arrowRight = new Texture("ui/arrowRight.png");

        play = new Texture("ui/right.png");
        information = new Texture("ui/information.png");
        settings = new Texture("ui/gear.png");
        levels = new Texture("ui/menuList.png");

        checkmark = new Texture("ui/checkmark.png");
        open = new Texture("ui/open.png");
    }

    public static void dispose() {
        character1.dispose();
        character2.dispose();
        character3.dispose();
        character4.dispose();
        character5.dispose();
        character6.dispose();
        character7.dispose();
        character8.dispose();
        character9.dispose();
        character10.dispose();

        crateBrown.dispose();
        crateDarkBrown.dispose();

        groundGrass.dispose();

        wallBeige.dispose();

        endPointBeige.dispose();

        skin.dispose();

        zoomOut.dispose();
        zoomIn.dispose();
        menu.dispose();
        undo.dispose();
        restart.dispose();
        controlLeft.dispose();
        controlRight.dispose();
        controlUp.dispose();
        controlDown.dispose();
        arrowLeft.dispose();
        arrowRight.dispose();

        play.dispose();
        information.dispose();
        settings.dispose();
        levels.dispose();

        checkmark.dispose();
        open.dispose();
    }
}
