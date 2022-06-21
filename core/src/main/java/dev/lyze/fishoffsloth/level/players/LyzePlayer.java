package dev.lyze.fishoffsloth.level.players;

import dev.lyze.fishoffsloth.level.Level;

public class LyzePlayer extends Player {
    public LyzePlayer(Level level) {
        super(true, level);

        position.x = 500;
        position.y = 500;
    }
}
