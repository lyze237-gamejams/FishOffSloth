package dev.lyze.fishoffsloth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.utils.ManagedScreenAdapter;
import dev.lyze.fishoffsloth.utils.UpdateRenderLoop;
import lombok.CustomLog;

@CustomLog
public class GameScreen extends ManagedScreenAdapter {
    private UpdateRenderLoop loop;
    private Level level;


    @Override
    protected void create() {

    }

    @Override
    public void show() {
        super.show();

        level = new Level(new TmxMapLoader().load("maps/DevMap.tmx"));
        loop = new UpdateRenderLoop(this::update, this::render);

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.TEAL);
        loop.updateAndRender();
    }

    private void render() {
        level.render();
    }

    private void update(float delta) {
        level.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        if (level == null)
            return;

        level.resize(width, height);
    }
}
