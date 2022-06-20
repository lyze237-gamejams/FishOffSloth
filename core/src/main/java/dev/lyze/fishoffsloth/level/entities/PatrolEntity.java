package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.dongbat.jbump.*;
import dev.lyze.fishoffsloth.level.Level;
import lombok.*;

@CustomLog
public class PatrolEntity extends MovableEntity {
    private float stateTime;
    private boolean moveRight = true;

    public PatrolEntity(float x, float y, float width, float height, Level level) {
        super(x, y, width, height, level, CollisionFilter.defaultFilter);

        var atlas = new TextureAtlas("atlases/main.atlas");
        setIdle(new Animation<>(0.15f, atlas.findRegions("players/sunny/idle"), Animation.PlayMode.LOOP_PINGPONG));
        setDeath(new Animation<>(0.15f, atlas.findRegions("players/sunny/idle"), Animation.PlayMode.LOOP_PINGPONG));
        setRun(new Animation<>(0.15f, atlas.findRegions("players/sunny/idle"), Animation.PlayMode.LOOP_PINGPONG));
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        stateTime += delta;

        if (stateTime > 2) {
            wantsToMoveRight = wantsToMoveLeft = 0;

            if (velocity.x == 0) {
                stateTime = 0;
                moveRight = !moveRight;
            }

        } else {
            wantsToMoveRight = moveRight ? 0.3f : 0;
            wantsToMoveLeft = moveRight ? 0 : 0.3f;
        }
    }
}