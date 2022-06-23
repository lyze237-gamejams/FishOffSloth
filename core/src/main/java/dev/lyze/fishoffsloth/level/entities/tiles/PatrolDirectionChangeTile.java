package dev.lyze.fishoffsloth.level.entities.tiles;

import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.utils.Direction;
import lombok.Getter;

public class PatrolDirectionChangeTile extends Tile {
    @Getter private final Direction direction;

    public PatrolDirectionChangeTile(float x, float y, float width, float height, Direction direction, Level level) {
        super(x, y, width, height, level);

        this.direction = direction;

        setHitbox(false);
    }
}

