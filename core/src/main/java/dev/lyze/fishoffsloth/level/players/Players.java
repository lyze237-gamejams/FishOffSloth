package dev.lyze.fishoffsloth.level.players;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import dev.lyze.fishoffsloth.gamepads.VirtualGamepadGroup;
import dev.lyze.fishoffsloth.level.Level;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

public class Players {
    @Getter private final Array<Player> players = new Array<>();

    @Getter private final Player player1, player2;

    private final Level level;


    public Players(Level level) {
        this.level = level;

        players.add(player1 = new LyzePlayer(level));
        players.add(player2 = new SunnyPlayer(level));

        players.forEach(p -> p.addToWorld(level.getEntityWorld().getWorld()));
    }

    public void update(float delta) {
        players.forEach(p -> p.update(level.getEntityWorld(), delta));
    }

    public void render(SpriteBatch batch) {
        players.forEach(p -> p.render(batch));
    }

    public void debugRender(ShapeDrawer drawer, BitmapFont font) {
        players.forEach(p -> p.debugRender(drawer, font));
    }
}
