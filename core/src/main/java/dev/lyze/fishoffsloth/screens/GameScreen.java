package dev.lyze.fishoffsloth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.transition.impl.HorizontalSlicingTransition;
import dev.lyze.fishoffsloth.gamepads.VirtualGamepadGroup;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.players.PlayerType;
import dev.lyze.fishoffsloth.utils.ManagedScreenAdapter;
import dev.lyze.fishoffsloth.utils.UpdateRenderLoop;
import lombok.CustomLog;
import lombok.var;

import java.util.ArrayList;

@CustomLog
public class GameScreen extends ManagedScreenAdapter {
    private UpdateRenderLoop loop;
    private Level level;

    private final ArrayList<VirtualGamepadGroup> gamepads = new ArrayList<>();

    private boolean restarting, finishing;
    private float screenTimer;

    @Override
    protected void create() {

    }

    @Override
    public void show() {
        super.show();

        gamepads.clear();

        var playerType = PlayerType.LYZE;
        if (pushParams != null)
            playerType = (PlayerType) pushParams[0];

        level = new Level(this, playerType, new TmxMapLoader().load("maps/DevMap.tmx"));
        level.initialize();
        loop = new UpdateRenderLoop(this::update, this::render);

        level.getPlayers().getPlayers().forEach(p -> gamepads.add(new VirtualGamepadGroup(p, gamepads.size())));

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        restarting = finishing = false;
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

        if (restarting) {
            if ((screenTimer -= delta) < 0) {
                ((ManagedGame) Gdx.app.getApplicationListener()).getScreenManager().pushScreen(MainMenuScreen.class.getName(), HorizontalSlicingTransition.class.getName());
                screenTimer = 10000;
            }
        }

        if (finishing) {
            if ((screenTimer -= delta) < 0) {
                ((ManagedGame) Gdx.app.getApplicationListener()).getScreenManager().pushScreen(FinishScreen.class.getName(), HorizontalSlicingTransition.class.getName(), level.getCoins());
                screenTimer = 10000;
            }
        }
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

    public void restart() {
        if (restarting)
            return;

        restarting = true;
        screenTimer = 2f;
    }

    public void finish() {
        if (finishing)
            return;

        finishing = true;
        screenTimer = 1f;
    }
}
