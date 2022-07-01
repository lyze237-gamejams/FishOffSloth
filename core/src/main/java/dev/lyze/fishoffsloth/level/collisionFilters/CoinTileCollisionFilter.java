package dev.lyze.fishoffsloth.level.collisionFilters;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import dev.lyze.fishoffsloth.level.players.Player;

public class CoinTileCollisionFilter implements CollisionFilter {
    public static final CoinTileCollisionFilter instance = new CoinTileCollisionFilter();

    @Override
    public Response filter(Item item, Item other) {
        if (other.userData instanceof Player)
            return Response.cross;

        return null;
    }
}
