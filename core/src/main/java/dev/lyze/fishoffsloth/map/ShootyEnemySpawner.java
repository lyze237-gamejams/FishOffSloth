package dev.lyze.fishoffsloth.map;

import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.entities.enemies.ShootyEnemy;
import dev.lyze.fishoffsloth.map.properties.ShootyEnemySpawnerProperties;
import lombok.CustomLog;
import lombok.var;

@CustomLog
public class ShootyEnemySpawner extends MapSpawner<ShootyEnemySpawnerProperties> {
    public ShootyEnemySpawner(Level level, Map map) {
        super(level, map, ShootyEnemySpawnerProperties.class);
    }

    @Override
    protected void spawnInternal(float x, float y, ShootyEnemySpawnerProperties data) {
        log.logInfo("Spawning shooty enemy at " + x + " / " + y);

        var entity = new ShootyEnemy(x, y, data.getDirection(), data.getSloth(), level);
        entity.setHealth(data.getHealth());

        level.getEntityWorld().addEntity(entity);
    }
}
