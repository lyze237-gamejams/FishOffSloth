package dev.lyze.fishoffsloth.level.players;

import com.badlogic.gdx.graphics.g2d.Animation;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.Level;

public class LyzePlayer extends Player {
    public LyzePlayer(Level level) {
        super(true, level);

        setRun(new Animation<>(0.15f, Statics.mainAtlas.findRegions("players/lyze/move"), Animation.PlayMode.LOOP));
        setIdle(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/lyze/idle"), Animation.PlayMode.LOOP));
        setJump(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/lyze/jump"), Animation.PlayMode.LOOP));
        setFall(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/lyze/jump"), Animation.PlayMode.LOOP));

        setAnimationXOffset(-75f);
        getBulletOffset().set(160, 110);
    }
}
