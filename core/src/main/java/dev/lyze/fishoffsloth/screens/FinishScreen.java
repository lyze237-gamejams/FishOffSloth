package dev.lyze.fishoffsloth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.transition.impl.SlidingOutTransition;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.utils.ManagedScreenAdapter;
import lombok.var;

public class FinishScreen extends ManagedScreenAdapter {
    private Stage stage;
    private Stage bgStage;

    private float ignoreInput = 3f;
    private boolean sceneSwitch;

    private int coins;

    @Override
    protected void create() {
        stage = new Stage(new FitViewport(1920, 1080));
        setupBgStage();
    }


    private void setupBgStage() {
        bgStage = new Stage(new ScreenViewport());

        var root = new Table();
        root.setFillParent(true);

        var mainMenuBg = new Image(Statics.mainAtlas.findRegion("mainMenuBg"));
        mainMenuBg.setScaling(Scaling.fill);

        root.add(mainMenuBg).grow();

        bgStage.addActor(root);
    }


    @Override
    public void show() {
        super.show();

        if (pushParams != null)
            coins = (int) pushParams[0];

        setupStage();
    }

    private void setupStage() {
        var root = new Table();
        root.defaults().pad(100);
        root.setFillParent(true);

        root.add(new Image(Statics.mainAtlas.findRegion("finish"))).row();

        root.add(new Label("Score: " + coins, setupFont(Color.WHITE)));

        stage.addActor(root);
    }

    private Label.LabelStyle setupFont(Color color) {
        var style = new Label.LabelStyle();
        style.font = new BitmapFont(Gdx.files.internal("fonts/LiberationSans-Regular.fnt"));
        style.font.setUseIntegerPositions(true);
        style.fontColor = color;

        return style;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if ((ignoreInput -= delta) < 0 && !sceneSwitch && (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)))
            ((ManagedGame) Gdx.app.getApplicationListener()).getScreenManager().pushScreen(MainMenuScreen.class.getName(), SlidingOutTransition.class.getName());

        bgStage.getViewport().apply();
        bgStage.act();
        bgStage.draw();

        stage.getViewport().apply();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        bgStage.getViewport().update(width, height, true);
    }
}