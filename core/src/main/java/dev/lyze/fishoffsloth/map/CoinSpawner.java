package dev.lyze.fishoffsloth.map;

import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.entities.tiles.CoinTile;
import dev.lyze.fishoffsloth.map.properties.CoinSpawnerProperties;
import lombok.CustomLog;
import lombok.var;

@CustomLog
public class CoinSpawner extends MapSpawner<CoinSpawnerProperties> {
    public CoinSpawner(Level level, Map map) {
        super(level, map, CoinSpawnerProperties.class);
    }

    @Override
    protected void spawnInternal(float x, float y, CoinSpawnerProperties data) {
        log.logInfo("Spawning coin: " + " at " + x + " /" + y);

        var coin = new CoinTile(x, y, level);
        level.getEntityWorld().addEntity(coin);
    }
}
