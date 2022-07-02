package dev.lyze.fishoffsloth.level.entities.enemies;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.CollisionFilter;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.collisionFilters.PatrollingEnemyCollisionFilter;
import dev.lyze.fishoffsloth.level.entities.ShooterEntity;
import dev.lyze.fishoffsloth.level.entities.data.BulletData;
import dev.lyze.fishoffsloth.level.entities.tiles.PatrolDirectionChangeTile;
import dev.lyze.fishoffsloth.utils.Direction;
import dev.lyze.fishoffsloth.utils.Sloth;
import lombok.var;

public class PatrollingEnemy extends ShooterEntity {
    private Direction direction;

    private float shootyTimer;

    public PatrollingEnemy(float x, float y, Direction direction, Sloth sloth, Level level) {
        super(x, y, 75, 200, BulletData.builder()
                .animation(new Animation<>(0.2f, Statics.mainAtlas.findRegions("enemies/bullet"), Animation.PlayMode.LOOP))
                .width(40).height(20)
                .lightColor(new Color(1, 1, 1, 0.4f))
                .offsetX(150).offsetY(87)
                .animationOffsetX(100)
                .travelDistance(900)
                .enemy(true)
                .damage(1)
                .timeBetweenShots(MathUtils.random(2000, 3000))
                .lightDistance(50)
                .lightOffsetX(-5).lightOffsetY(10)
                .build(), level, PatrollingEnemyCollisionFilter.instance);

        setDoDeathAnimation(true);
        
        this.direction = direction;

        Animation<TextureAtlas.AtlasRegion> animation = null;
        switch (sloth) {
            case Clogg:
                animation = new Animation<>(0.15f, Statics.mainAtlas.findRegions("enemies/clogg/move"), Animation.PlayMode.LOOP);
                break;
            case Beauty:
                animation = new Animation<>(0.15f, Statics.mainAtlas.findRegions("enemies/beauty/move"), Animation.PlayMode.LOOP);
                break;
        }

        setIdle(animation);
        setRun(animation);

        setAnimationXOffset(-20);
        setAnimationYOffset(-10f);

        wantsToShoot = true;
    }

    @Override
    public void update(EntityWorld world, float delta) {
        var change = getPatrolBlockOnMe(world);
        if (change != null)
            this.direction = change.getDirection();

        switch (direction) {
            case Left:
                wantsToMoveLeft = 1;
                wantsToMoveRight = 0;
                break;
            case Right:
                wantsToMoveLeft = 0;
                wantsToMoveRight = 1;
                break;
        }

        super.update(world, delta);
    }

    private PatrolDirectionChangeTile getPatrolBlockOnMe(EntityWorld world) {
        world.getWorld().project(item, position.x, position.y, width, height, position.x, position.y - 20f, CollisionFilter.defaultFilter, getTempCollisions());

        for (int i = 0; i < getTempCollisions().size(); i++) {
            var collision = getTempCollisions().get(i);
            var userData = collision.other.userData;

            if (userData instanceof PatrolDirectionChangeTile) {
                return (PatrolDirectionChangeTile) userData;
            }
        }

        return null;
    }

    @Override
    public void damage(int amount, Direction from) {
        super.damage(amount, from);

        Statics.playSound(Statics.hit);
    }

    @Override
    protected void die(Direction from) {
        super.die(from);

        for (int i = 0; i < (Gdx.app.getType() == Application.ApplicationType.WebGL ? 3 : 25); i++)
            level.getEntityWorld().addEntity(new EnemyExplosion(position.x + width / 2f, position.y + height / 2f, level));
    }
}

