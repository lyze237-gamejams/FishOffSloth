package dev.lyze.fishoffsloth.level.players;

import dev.lyze.fishoffsloth.level.Level;

public class SunnyPlayer extends Player {
    public SunnyPlayer(Level level) {
        super(false, level);

        position.x = 200;
        position.y = 500;
    }
}
