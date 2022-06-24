package dev.lyze.fishoffsloth.level.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.entities.data.BulletData;

public class LyzePlayer extends Player {
    public LyzePlayer(Level level) {
        super(true, BulletData.builder()
                .animation(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/lyze/bullet"), Animation.PlayMode.LOOP))
                .width(180).height(80)
                .lightColor(new Color(1, 1, 1, 0.4f))
                .offsetX(120).offsetY(70)
                .animationOffsetX(110)
                .travelDistance(800)
                .timeBetweenShots(800)
                .lightDistance(130)
                .lightOffsetX(0).lightOffsetY(40)
                .build(), level);

        setRun(new Animation<>(0.15f, Statics.mainAtlas.findRegions("players/lyze/move"), Animation.PlayMode.LOOP));
        setIdle(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/lyze/idle"), Animation.PlayMode.LOOP));
        setJump(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/lyze/jump"), Animation.PlayMode.LOOP));
        setFall(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/lyze/jump"), Animation.PlayMode.LOOP));

        setAnimationXOffset(-75f);
        setAnimationYOffset(-10f);
    }
}
