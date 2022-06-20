package dev.lyze.fishoffsloth.level.entities.tiles;

import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.entities.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Tile extends Entity {
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private boolean hitbox;

    public Tile(float x, float y, float width, float height, Level level) {
        super(x, y, width, height, level);
    }
}
