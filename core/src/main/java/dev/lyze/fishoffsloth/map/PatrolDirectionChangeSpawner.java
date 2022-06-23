package dev.lyze.fishoffsloth.map;

import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.entities.tiles.PatrolDirectionChangeTile;
import dev.lyze.fishoffsloth.map.properties.PatrollingEnemySpawnerProperties;
import lombok.CustomLog;

@CustomLog
public class PatrolDirectionChangeSpawner extends MapSpawner<PatrollingEnemySpawnerProperties> {
    public PatrolDirectionChangeSpawner(Level level, Map map) {
        super(level, map, PatrollingEnemySpawnerProperties.class);
    }

    @Override
    protected void spawnInternal(float x, float y, PatrollingEnemySpawnerProperties data) {
        log.logInfo("Spawning patrol direction change block at " + x + " / " + y);

        level.getEntityWorld().addStaticEntity(new PatrolDirectionChangeTile(x, y, 75, 75, data.getDirection(), level));
    }
}
