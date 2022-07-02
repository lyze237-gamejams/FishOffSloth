package dev.lyze.fishoffsloth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.transition.impl.SlidingOutTransition;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.utils.ManagedScreenAdapter;
import lombok.var;

public class SplashScreen extends ManagedScreenAdapter {
    private Stage stage;
    private Stage bgStage;

    private Timer.Task timerTask;

    @Override
    protected void create() {
        stage = new Stage(new FitViewport(1920, 1080));
        setupStage();
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

        timerTask = new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                ((ManagedGame) Gdx.app.getApplicationListener()).getScreenManager().pushScreen(MainMenuScreen.class.getName(), SlidingOutTransition.class.getName());
            }
        }, 3f);
    }

    private void setupStage() {
        var root = new Table();
        root.defaults().pad(100);
        root.setFillParent(true);

        var lyzeLogo = new Image(Statics.mainAtlas.findRegion("players/lyze/logo"));
        var sunnyLogo = new Image(Statics.mainAtlas.findRegion("players/sunny/logo"));

        lyzeLogo.addAction(Actions.scaleTo(1.1f, 1.1f, 3f));
        sunnyLogo.addAction(Actions.scaleTo(1.1f, 1.1f, 3f));

        root.add(lyzeLogo).row();
        root.add(sunnyLogo);

        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if ((Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) && timerTask.isScheduled()) {
            timerTask.cancel();
            ((ManagedGame) Gdx.app.getApplicationListener()).getScreenManager().pushScreen(MainMenuScreen.class.getName(), SlidingOutTransition.class.getName());
        }

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