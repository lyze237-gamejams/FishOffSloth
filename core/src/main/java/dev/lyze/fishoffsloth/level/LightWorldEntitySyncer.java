package dev.lyze.fishoffsloth.level;

import box2dLight.Light;
import com.badlogic.gdx.utils.Array;
import dev.lyze.fishoffsloth.level.entities.Entity;
import lombok.var;

public class LightWorldEntitySyncer {
    private final Array<Entry> entries = new Array<>();

    public LightWorldEntitySyncer() {

    }

    public void addEntity(Entity entity, Light light, float offsetX, float offsetY) {
        entries.add(new Entry(entity, light, offsetX, offsetY));
    }

    public void update(float delta) {
        entries.forEach(e -> e.light.setPosition(e.entity.getPosition().x + e.offsetX, e.entity.getPosition().y + e.offsetY));
    }

    public void removeEntity(Entity entity) {
        var iterator = entries.iterator();

        while (iterator.hasNext()) {
            var entry = iterator.next();
            if (entry.entity == entity) {
                iterator.remove();
                entry.light.remove(true);
                return;
            }
        }
    }

    private static class Entry {
        private final Entity entity;
        private final Light light;
        private final float offsetX;
        private final float offsetY;

        public Entry(Entity entity, Light light, float offsetX, float offsetY) {
            this.entity = entity;
            this.light = light;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }
    }
}
