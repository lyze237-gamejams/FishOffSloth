package dev.lyze.fishoffsloth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.players.PlayerType;
import dev.lyze.fishoffsloth.utils.ManagedScreenAdapter;
import lombok.var;

public class MainMenuScreen extends ManagedScreenAdapter  {
    private BitmapFont font;

    private Label.LabelStyle labelStyle;

    private Stage stage, bgStage;

    @Override
    protected void create() {
        font = new BitmapFont();

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

        Gdx.input.setInputProcessor(stage);
    }

    private void setupStage() {
        stage = new Stage(new FitViewport(1920, 1080));

        var root = new Table();
        root.setFillParent(true);

        root.add(new Image(Statics.mainAtlas.findRegion("selectMode"))).row();

        var charSelectTable = new Table();
        root.add(charSelectTable);

        var p1p2Table = new Table();
        charSelectTable.add(p1p2Table).padRight(128);

        var values = PlayerType.values();
        for (int i = 0; i < values.length - 1; i++) {
            var value = values[i];
            p1p2Table.add(generateCharTable(value)).pad(12).row();
        }
        charSelectTable.add(generateCharTable(values[values.length - 1])).padLeft(128);

        stage.addActor(root);
    }

    private Table generateCharTable(PlayerType value) {
        var table = new Table();
        var image = new ImageButton(new TextureRegionDrawable(Statics.mainAtlas.findRegion(value.getImagePath())));
        image.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((ManagedGame) Gdx.app.getApplicationListener()).getScreenManager().pushScreen(GameScreen.class.getName(), BlendingTransition.class.getName(), value);
            }
        });
        table.add(image).row();
        return table;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.TEAL);

        bgStage.getViewport().apply();
        bgStage.act(delta);
        bgStage.draw();

        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        bgStage.getViewport().update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

}
