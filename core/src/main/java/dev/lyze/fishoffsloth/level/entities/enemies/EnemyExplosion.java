package dev.lyze.fishoffsloth.level.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.Collision;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.collisionFilters.EnemyExplosionCollisionFilter;
import dev.lyze.fishoffsloth.level.entities.GravityEntity;
import dev.lyze.fishoffsloth.level.players.Player;

public class EnemyExplosion extends GravityEntity {
    private float invincibilityTimer = 0.1f;

    public EnemyExplosion(float x, float y, Level level) {
        super(x, y, 30, 30, level, EnemyExplosionCollisionFilter.instance);

        setIdle(new Animation<>(0.2f, Statics.mainAtlas.findRegions("carlcoinsmall"), Animation.PlayMode.LOOP));
        setRun(new Animation<>(0.2f, Statics.mainAtlas.findRegions("carlcoinsmall"), Animation.PlayMode.LOOP));
        setJump(new Animation<>(0.2f, Statics.mainAtlas.findRegions("carlcoinsmall"), Animation.PlayMode.LOOP));

        if (MathUtils.randomBoolean())
            wantsToMoveRight = MathUtils.random(0.6f, 1f);
        else
            wantsToMoveLeft = MathUtils.random(0.6f, 1f);

        setJumpForce(2f);
        setSpeed(2f);
    }

    @Override
    public void update(EntityWorld world, float delta) {
        invincibilityTimer -= delta;

        setJumpForce(getJumpForce() - 2f * delta);

        if (getJumpForce() <= 0) {
            setJumpForce(0);
            wantsToJump = false;
            if (isGrounded()) {
                wantsToMoveRight = 0;
                wantsToMoveLeft = 0;
            }
        }
        else {
            wantsToJump = true;
        }

        super.update(world, delta);
    }



    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (collision.other.userData instanceof Player && invincibilityTimer < 0) {
            level.removeEntity(this);
        }
    }
}
