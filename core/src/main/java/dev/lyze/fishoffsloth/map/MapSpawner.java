package dev.lyze.fishoffsloth.map;

import dev.lyze.fishoffsloth.level.Level;
import lombok.Getter;

public abstract class MapSpawner<TProperties extends MapSpawnerProperties> {
    protected final Level level;
    protected final Map map;

    @Getter private final Class<TProperties> propertiesClass;

    public MapSpawner(Level level, Map map, Class<TProperties> propertiesClass) {
        this.level = level;
        this.map = map;
        this.propertiesClass = propertiesClass;
    }

    public void spawn(float x, float y, MapSpawnerProperties data) {
        spawnInternal(x, y, (TProperties) data);
    }

    protected abstract void spawnInternal(float x, float y, TProperties data);
}
