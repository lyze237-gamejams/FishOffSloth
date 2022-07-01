package dev.lyze.fishoffsloth.level.collisionFilters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.fishoffsloth.level.entities.BulletEntity;
import dev.lyze.fishoffsloth.level.entities.DeadEntity;
import dev.lyze.fishoffsloth.level.entities.MovableEntity;
import dev.lyze.fishoffsloth.level.entities.tiles.Tile;

public class BulletCollisionFilter implements CollisionFilter {
    public static final BulletCollisionFilter instance = new BulletCollisionFilter();

    @Override
    public Response filter(Item item, Item other) {
        if (other.userData instanceof BulletEntity)
            return null;

        if (other.userData instanceof DeadEntity)
            return null;

        if (other.userData instanceof Tile && !((Tile) other.userData).isHitbox())
            return Response.cross;

        if (other.userData instanceof MovableEntity)
            return Response.cross;

        return Response.slide;
    }
}
