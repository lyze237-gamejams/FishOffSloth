package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.math.Vector2;
import com.gempukku.libgdx.lib.camera2d.focus.PositionProvider;
import dev.lyze.fishoffsloth.level.players.Player;
import lombok.var;

public class EntityPositionProvider implements PositionProvider {
    private final Player entity;

    public EntityPositionProvider(Player entity) {
        this.entity = entity;
    }

    @Override
    public Vector2 getPosition(Vector2 position) {
        if (!entity.isDead())
            return position.set(entity.getPosition());

        for (var p : entity.getLevel().getPlayers().getPlayers())
            if (!p.isDead())
                return position.set(p.getPosition());

        return position;
    }
}
