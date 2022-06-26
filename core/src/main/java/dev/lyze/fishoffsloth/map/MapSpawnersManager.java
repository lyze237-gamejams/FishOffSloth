package dev.lyze.fishoffsloth.map;

import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.lyze.fishoffsloth.level.Level;
import lombok.CustomLog;
import lombok.var;

@CustomLog
public class MapSpawnersManager {
    private final Level level;
    private final Map map;

    private final Array<MapSpawner<?>> spawners = new Array<>();

    public MapSpawnersManager(Level level, Map map) {
        this.level = level;
        this.map = map;

        spawners.add(new PlayerSpawner(level, map));
        spawners.add(new PatrollingEnemySpawner(level, map));
        spawners.add(new PatrolDirectionChangeSpawner(level, map));
        spawners.add(new ShootyEnemySpawner(level, map));
    }

    public void initialize() {
        var eventsLayer = map.getMap().getLayers().get("Events");
        if (eventsLayer == null)
            throw new IllegalArgumentException("Couldn't find events layer");
        eventsLayer.setVisible(false);

        for (var object : eventsLayer.getObjects()) {
            if (object instanceof TiledMapTileMapObject) {
                var tile = (TiledMapTileMapObject) object;

                tile.getTile().getProperties().getKeys().forEachRemaining(k -> {
                    if (!tile.getProperties().containsKey(k))
                        tile.getProperties().put(k, tile.getTile().getProperties().get(k));
                });

                var properties = tile.getProperties();
                var type = properties.get("type", String.class);

                if (type == null) {
                    log.logInfo("No type property set in events layer on tile " + tile.getX() + "/" + tile.getY());
                    continue;
                }

                for (var spawner : spawners) {
                    if (spawner.getClass().getSimpleName().equalsIgnoreCase(type)) {
                        doEvent(tile, spawner);
                        break;
                    }
                }
            }
        }
    }

    private void doEvent(TiledMapTileMapObject tile, MapSpawner<?> spawner) {
        try {
            MapSpawnerProperties instance = ClassReflection.newInstance(spawner.getPropertiesClass());

            for (var field : ClassReflection.getDeclaredFields(spawner.getPropertiesClass()))
                initializeMapProperties(tile, instance, field);

            spawner.spawn(tile.getX(), tile.getY(), instance);

        } catch (ReflectionException e) {
            log.logError("Couldn't create " + spawner.getPropertiesClass().getSimpleName(), e);
            throw new RuntimeException(e);
        }
    }

    private void initializeMapProperties(TiledMapTileMapObject tile, MapSpawnerProperties instance, Field field) {
        field.setAccessible(true);

        tile.getProperties().getKeys().forEachRemaining(k -> {
            if (k.equalsIgnoreCase(field.getName())) {
                var value = tile.getProperties().get(k);

                if (field.getType().isEnum()) {
                    if (value.toString().equalsIgnoreCase("null"))
                        value = null;
                    else
                        value = Enum.valueOf((Class<Enum>) field.getType(), value.toString());
                }

                try {
                    field.set(instance, value);
                } catch (ReflectionException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
