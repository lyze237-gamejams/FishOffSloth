package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.math.Vector2;
import com.gempukku.libgdx.lib.camera2d.focus.PositionProvider;

public class EntityPositionProvider implements PositionProvider {
    private final Entity entity;

    public EntityPositionProvider(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Vector2 getPosition(Vector2 position) {
        return position.set(entity.getPosition().x, entity.getPosition().y);
    }
}
