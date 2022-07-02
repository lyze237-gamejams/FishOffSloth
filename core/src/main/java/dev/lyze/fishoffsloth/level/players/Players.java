package dev.lyze.fishoffsloth.level.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import dev.lyze.fishoffsloth.level.Level;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Players {
    @Getter private final Array<Player> players = new Array<>(Player.class);

    @Getter private Player player1, player2;

    private final Level level;


    public Players(PlayerType playerType, Level level) {
        this.level = level;

        switch (playerType) {
            case LYZE:
                this.players.add(player1 = new LyzePlayer(level, true));
                break;
            case SUNNY:
                this.players.add(player1 = new SunnyPlayer(level, true));
                break;
            case BOTH:
                this.players.add(player1 = new LyzePlayer(level, true));
                this.players.add(player2 = new SunnyPlayer(level, false));
                break;
        }

        this.players.forEach(p -> p.addToWorld(level.getEntityWorld().getWorld()));

        this.players.forEach(p -> level.getLightWorld().getSyncer().addEntity(p, level.getLightWorld().createPointLight(0, 0, new Color(1, 1, 1, 0.3f), 300, 32), p.getWidth() / 2f, p.getHeight() / 2f));
    }

    public void update(float delta) {
        players.forEach(p -> p.update(level.getEntityWorld(), delta));

        checkFinishZone();
        restartOnDeath();
    }


    private void checkFinishZone() {
        var touchFinish = false;
        for (var player : players)
            touchFinish |= level.getMap().getFinishZone().contains(player.getPosition().x, player.getPosition().y);

        if (touchFinish) {
            level.getGameScreen().finish();
        }
    }

    private void restartOnDeath() {
        var allDead = true;
        for (var player : players)
            if (!player.isDead())
                allDead = false;

        if (allDead)
            level.getGameScreen().restart();
    }

    public void render(SpriteBatch batch) {
        players.forEach(p -> p.render(batch));
    }

    public void debugRender(ShapeDrawer drawer, BitmapFont font) {
        players.forEach(p -> p.debugRender(drawer, font));
    }
}
