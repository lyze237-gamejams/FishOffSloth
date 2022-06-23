package dev.lyze.fishoffsloth.level.entities;

import com.dongbat.jbump.CollisionFilter;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.collisionFilters.PatrollingEnemyCollisionFilter;
import dev.lyze.fishoffsloth.level.entities.tiles.PatrolDirectionChangeTile;
import dev.lyze.fishoffsloth.utils.Direction;
import lombok.var;

public class PatrollingEnemy extends GravityEntity {
    private Direction direction;

    public PatrollingEnemy(float x, float y, Direction direction, Level level) {
        super(x, y, 75, 200, level, PatrollingEnemyCollisionFilter.instance);
        
        this.direction = direction;
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
}
