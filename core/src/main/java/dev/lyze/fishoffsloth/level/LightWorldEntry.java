package dev.lyze.fishoffsloth.level;

import box2dLight.Light;
import dev.lyze.fishoffsloth.level.entities.Entity;
import lombok.Data;

@Data
public class LightWorldEntry {
    private final Entity entity;
    private final Light light;
    private final float offsetX;
    private final float offsetY;

    public LightWorldEntry(Entity entity, Light light, float offsetX, float offsetY) {
        this.entity = entity;
        this.light = light;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}
