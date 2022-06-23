package dev.lyze.fishoffsloth.map;

import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.entities.PatrollingEnemy;
import dev.lyze.fishoffsloth.map.properties.PatrollingEnemySpawnerProperties;
import lombok.CustomLog;

@CustomLog
public class PatrollingEnemySpawner extends MapSpawner<PatrollingEnemySpawnerProperties> {
    public PatrollingEnemySpawner(Level level, Map map) {
        super(level, map, PatrollingEnemySpawnerProperties.class);
    }

    @Override
    protected void spawnInternal(float x, float y, PatrollingEnemySpawnerProperties data) {
        log.logInfo("Spawning patrolling enemy at " + x + " / " + y);

        level.getEntityWorld().addEntity(new PatrollingEnemy(x, y, data.getDirection(), level));
    }
}
