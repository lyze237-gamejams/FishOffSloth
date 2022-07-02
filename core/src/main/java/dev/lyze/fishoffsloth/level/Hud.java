package dev.lyze.fishoffsloth.level;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.players.Player;
import lombok.var;

public class Hud {
    private final Level level;

    private final Stage stage = new Stage(new ScreenViewport());

    public Hud(Level level) {
        this.level = level;
    }

    public void updateHud() {
        stage.clear();

        var players = level.getPlayers().getPlayers();
        setupPlayer(players.get(0), Align.topLeft);
        if (players.size > 1)
            setupPlayer(players.get(1), Align.topRight);
    }

    private void setupPlayer(Player player, int align) {
        if (player == null)
            return;

        var root = new Table();
        root.setFillParent(true);

        var table = new Table();
        var health = player.getHealth();
        for (int i = 0; i < health; i++)
            table.add(new Image(Statics.mainAtlas.findRegion(player.getHeartPath())));

        root.add(table).align(align).expand().pad(16);

        stage.addActor(root);
    }

    public void update(float delta) {
        stage.act(delta);
    }

    public void render() {
        stage.getViewport().apply();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
