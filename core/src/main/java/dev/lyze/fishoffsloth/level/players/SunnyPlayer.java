package dev.lyze.fishoffsloth.level.players;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.fishoffsloth.level.Level;
import lombok.var;

public class SunnyPlayer extends Player {
    public SunnyPlayer(Level level) {
        super(false, level);

        var atlas = new TextureAtlas("atlases/main.atlas");
        setIdle(new Animation<>(0.2f, atlas.findRegions("players/sunny/idle"), Animation.PlayMode.LOOP));

        setAnimationXOffset(-60f);
    }
}
