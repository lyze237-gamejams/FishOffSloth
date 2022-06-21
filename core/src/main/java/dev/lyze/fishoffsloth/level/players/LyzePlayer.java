package dev.lyze.fishoffsloth.level.players;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.fishoffsloth.level.Level;
import lombok.var;

public class LyzePlayer extends Player {
    public LyzePlayer(Level level) {
        super(true, level);

        var atlas = new TextureAtlas("atlases/main.atlas");

        setRun(new Animation<>(0.15f, atlas.findRegions("players/lyze/move"), Animation.PlayMode.LOOP));
        setIdle(new Animation<>(0.2f, atlas.findRegions("players/lyze/idle"), Animation.PlayMode.LOOP));

        setAnimationXOffset(-75f);

        position.x = 500;
        position.y = 500;
    }
}
