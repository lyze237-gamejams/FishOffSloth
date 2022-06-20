package dev.lyze.fishoffsloth.level.entities;

import com.dongbat.jbump.CollisionFilter;
import dev.lyze.fishoffsloth.level.Level;

public class Player extends GravityEntity {
    public Player(float x, float y, float width, float height, Level level) {
        super(x, y, width, height, level, CollisionFilter.defaultFilter);
    }
}
