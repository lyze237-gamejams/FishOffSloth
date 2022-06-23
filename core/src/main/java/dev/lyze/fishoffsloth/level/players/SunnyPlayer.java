package dev.lyze.fishoffsloth.level.players;

import com.badlogic.gdx.graphics.g2d.Animation;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.Level;

public class SunnyPlayer extends Player {
    public SunnyPlayer(Level level) {
        super(false, level);

        setIdle(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/sunny/idle"), Animation.PlayMode.LOOP));

        setAnimationXOffset(-60f);

        setTimeBetweenShots(100);
        getBulletOffset().set(100, 110);
    }
}
