package dev.lyze.fishoffsloth.level.entities;

import com.dongbat.jbump.Collision;
import com.dongbat.jbump.Response;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.collisionFilters.BulletCollisionFilter;
import dev.lyze.fishoffsloth.level.entities.data.BulletData;
import dev.lyze.fishoffsloth.utils.Direction;
import lombok.var;

public class BulletEntity extends MovableEntity {
    private final float travelDistance;
    private final BulletData data;
    private final Direction direction;

    public BulletEntity(float x, float y, Direction direction, BulletData data, Level level) {
        super(x, y, data.getWidth(), data.getHeight(), level, BulletCollisionFilter.instance);

        this.direction = direction;
        this.data = data;
        this.travelDistance = x + (data.getTravelDistance() * direction.getValue().x);

        setIdle(data.getAnimation());
        setRun(data.getAnimation());

        setSpeed(10);
        setAnimationXOffset(-data.getAnimationOffsetX());

        var light = level.getLightWorld().createPointLight(0, 0, data.getLightColor(), data.getLightDistance(), data.getLightRays());
        level.getLightWorld().getSyncer().addEntity(this, light, getWidth() / 2f + data.getLightOffsetX() * direction.getValue().x, data.getLightOffsetY());
    }

    @Override
    public void update(EntityWorld world, float delta) {
        move();
        if (isOutOfDistance())
            die();

        super.update(world, delta);
    }

    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (collision.type != Response.cross)
            die();

        if (collision.other.userData instanceof MovableEntity) {
            var entity = ((MovableEntity) collision.other.userData);

            entity.damage(data.getDamage());
        }
    }

    private void move() {
        wantsToMoveLeft = direction == Direction.Left ? 1 : 0;
        wantsToMoveRight = direction == Direction.Right ? 1 : 0;
    }

    private boolean isOutOfDistance() {
        return (direction == Direction.Right && position.x > travelDistance) || (direction == Direction.Left && position.x < travelDistance);
    }
}
