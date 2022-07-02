package dev.lyze.fishoffsloth.level;

import box2dLight.Light;
import com.badlogic.gdx.utils.Array;
import dev.lyze.fishoffsloth.level.entities.Entity;

public class LightWorldEntitySyncer {
    private final Array<LightWorldEntry> entries = new Array<>(LightWorldEntry.class);

    public LightWorldEntitySyncer() {

    }

    public void addEntity(Entity entity, Light light, float offsetX, float offsetY) {
        entries.add(new LightWorldEntry(entity, light, offsetX, offsetY));
    }

    public void update(float delta) {
        entries.forEach(e -> e.getLight().setPosition(e.getEntity().getPosition().x + e.getOffsetX(), e.getEntity().getPosition().y + e.getOffsetY()));
    }

    public void removeEntity(Entity entity) {
        Array.ArrayIterator<LightWorldEntry> iterator = entries.iterator();

        while (iterator.hasNext()) {
            LightWorldEntry LightWorldEntry = iterator.next();
            if (LightWorldEntry.getEntity() == entity) {
                iterator.remove();
                LightWorldEntry.getLight().remove(true);
                return;
            }
        }
    }
}
