package dev.lyze.fishoffsloth.level.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.entities.data.BulletData;

public class SunnyPlayer extends Player {
    public SunnyPlayer(Level level, boolean firstPlayer) {
        super(firstPlayer, BulletData.builder()
                .animation(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/sunny/bullet"), Animation.PlayMode.LOOP))
                .width(70).height(55)
                .lightColor(new Color(1, 1, 1, 0.4f))
                .offsetX(10).offsetY(65)
                .travelDistance(700)
                .damage(1)
                .timeBetweenShots(175)
                .lightDistance(50)
                .lightOffsetX(0).lightOffsetY(30)
                .build(), level);

        setRun(new Animation<>(0.15f, Statics.mainAtlas.findRegions("players/sunny/move"), Animation.PlayMode.LOOP));
        setIdle(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/sunny/idle"), Animation.PlayMode.LOOP));
        setJump(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/sunny/jump"), Animation.PlayMode.LOOP));
        setFall(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/sunny/jump"), Animation.PlayMode.LOOP));
        setShoot(new Animation<>(0.2f, Statics.mainAtlas.findRegions("players/sunny/shoot"), Animation.PlayMode.LOOP));

        setHeartPath("players/sunny/heart");
        setHealth(4);
        setAnimationXOffset(-60f);
        setAnimationYOffset(-10f);
    }
}
