package dev.lyze.fishoffsloth.level.collisionFilters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;

public class DeadCollisionFilter implements CollisionFilter {
    public static final DeadCollisionFilter instance = new DeadCollisionFilter();

    @Override
    public Response filter(Item item, Item other) {
        return null;
    }
}
