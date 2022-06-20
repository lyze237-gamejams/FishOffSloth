package dev.lyze.fishoffsloth.level.entities.tiles;

import dev.lyze.fishoffsloth.level.Level;

public class GroundTile extends Tile {
    public GroundTile(float x, float y, float width, float height, Level level) {
        super(x, y, width, height, level);

        setHitbox(true);
    }
}

