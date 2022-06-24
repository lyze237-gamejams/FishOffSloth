package dev.lyze.fishoffsloth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.lyze.fishoffsloth.gamepads.VirtualGamepadGroup;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.utils.ManagedScreenAdapter;
import dev.lyze.fishoffsloth.utils.UpdateRenderLoop;
import lombok.CustomLog;

import java.util.ArrayList;

@CustomLog
public class GameScreen extends ManagedScreenAdapter {
    private UpdateRenderLoop loop;
    private Level level;

    private final ArrayList<VirtualGamepadGroup> gamepads = new ArrayList<>();

    @Override
    protected void create() {

    }

    @Override
    public void show() {
        super.show();

        gamepads.clear();

        level = new Level(new TmxMapLoader().load("maps/DevMap.tmx"));
        level.initialize();
        loop = new UpdateRenderLoop(this::update, this::render);

        level.getPlayers().getPlayers().forEach(p -> gamepads.add(new VirtualGamepadGroup(p, gamepads.size())));

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        gamepads.forEach(g -> g.update(delta));
        loop.updateAndRender();
    }

    private void update(float delta) {
        level.update(delta);
        gamepads.forEach(g -> g.reset(delta));
    }

    private void render() {
        level.render();
    }

    @Override
    public void resize(int width, int height) {
        if (level == null)
            return;

        level.resize(width, height);
    }
}
