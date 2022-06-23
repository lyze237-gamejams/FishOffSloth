package dev.lyze.fishoffsloth.level.entities;

import com.dongbat.jbump.Collision;
import com.dongbat.jbump.Response;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.collisionFilters.BulletCollisionFilter;
import dev.lyze.fishoffsloth.utils.Direction;

public class BulletEntity extends MovableEntity {
    private final float travelDistance;
    private final Direction direction;

    private boolean alreadyRemoved;

    public BulletEntity(float x, float y, float travelDistance, Direction direction, Level level) {
        super(x, y, 25, 25, level, BulletCollisionFilter.instance);

        this.direction = direction;
        this.travelDistance = x + (travelDistance * direction.getValue().x);

        setSpeed(10);
    }

    @Override
    public void update(EntityWorld world, float delta) {
        move();
        if (isOutOfDistance())
            destroy();

        super.update(world, delta);
    }

    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (collision.type != Response.cross)
            destroy();
    }

    private void move() {
        wantsToMoveLeft = direction == Direction.Left ? 1 : 0;
        wantsToMoveRight = direction == Direction.Right ? 1 : 0;
    }

    private void destroy() {
        if (alreadyRemoved)
            return;

        alreadyRemoved = true;
        level.getEntityWorld().removeEntity(this);
    }

    private boolean isOutOfDistance() {
        return (direction == Direction.Right && position.x > travelDistance) || (direction == Direction.Left && position.x < travelDistance);
    }
}
