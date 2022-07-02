package dev.lyze.fishoffsloth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.players.PlayerType;
import dev.lyze.fishoffsloth.utils.ManagedScreenAdapter;
import lombok.var;

public class MainMenuScreen extends ManagedScreenAdapter  {
    private BitmapFont font;

    private Label.LabelStyle labelStyle;

    private Stage stage;

    @Override
    protected void create() {
        font = new BitmapFont();
        labelStyle = setupFont(Color.WHITE);

        setupStage();
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(stage);
    }

    private void setupStage() {
        var screenManager = ((ManagedGame) Gdx.app.getApplicationListener()).getScreenManager();

        stage = new Stage(new FitViewport(1920, 1080));

        var root = new Table();
        root.setFillParent(true);

        root.add(new Label("Fish Off Sloth", labelStyle)).padBottom(24).row();
        root.add(new Label("Select mode", labelStyle)).row();

        var inner = new Table();
        root.add(inner);

        for (PlayerType value : PlayerType.values()) {
            var table = new Table();
            var image = new ImageButton(new TextureRegionDrawable(Statics.mainAtlas.findRegion(value.getImagePath(), value.getIndex())));
            image.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    screenManager.pushScreen(GameScreen.class.getName(), BlendingTransition.class.getName(), value);
                }
            });
            table.add(image).row();
            var text = new Label(value.toString(), labelStyle);
            text.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    screenManager.pushScreen(GameScreen.class.getName(), BlendingTransition.class.getName(), value);
                }
            });
            table.add(text);
            inner.add(table);
        }

        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.TEAL);

        stage.getViewport().apply();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    private Label.LabelStyle setupFont(Color color) {
        var style = new Label.LabelStyle();
        style.font = font;
        style.font.setUseIntegerPositions(true);
        style.fontColor = color;

        return style;
    }
}
